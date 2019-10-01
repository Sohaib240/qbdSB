package com.ourtimesheet.qbd.factory;

import com.ourtimesheet.paidTimeOff.Leave;
import com.ourtimesheet.paytype.EmployeePayType;
import com.ourtimesheet.paytype.PayType;
import com.ourtimesheet.qbd.domain.Customer;
import com.ourtimesheet.qbd.domain.CustomerJob;
import com.ourtimesheet.qbd.domain.QuickBooksClass;
import com.ourtimesheet.qbd.domain.ServiceItem;
import com.ourtimesheet.qbd.helper.XmlParserUtils;
import com.ourtimesheet.qbd.query.QBDRequest;
import com.ourtimesheet.qbd.query.hourExport.HoursSyncRequest;
import com.ourtimesheet.qbd.query.hourExport.LeaveHourSyncRequest;
import com.ourtimesheet.qbd.query.hourExport.OverTimeHoursSyncRequest;
import com.ourtimesheet.qbd.query.hourExport.RegularHoursSyncRequest;
import com.ourtimesheet.timesheet.chargeCode.ChargeCode;
import com.ourtimesheet.timesheet.hoursWorked.HoursWorked;
import nu.xom.converters.DOMConverter;
import org.apache.xerces.dom.DOMImplementationImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Abdus Salam on 3/2/2017.
 */
public class ExportQueryFactory {

    private XmlParserUtils xmlParserUtils;

    public String create(InputStream inputStream, HoursWorked hoursWorked, String notes, HoursSyncRequest hoursSyncRequest) throws Exception {
        xmlParserUtils = new XmlParserUtils();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        Document doc = documentBuilderFactory.newDocumentBuilder().parse(inputStream);

        doc = populateDocumentWithData(hoursWorked, notes, doc, hoursSyncRequest);

        StringWriter stw = new StringWriter();
        Transformer serializer = TransformerFactory.newInstance().newTransformer();
        serializer.transform(new DOMSource(doc), new StreamResult(stw));
        return stw.toString().replaceAll("<(\\w+)>\\s*</\\1>|<\\w+/>", "");
    }

    private Document populateDocumentWithData(HoursWorked hoursWorked, String notes, Document doc, HoursSyncRequest hoursSyncRequest) {
        Node TimeTrackingAdd = getParamsNode(doc);
        updateEmployee(hoursWorked, TimeTrackingAdd);
        updateChargeCode(hoursWorked, TimeTrackingAdd, hoursSyncRequest);
        updateDuration(hoursWorked, TimeTrackingAdd, hoursSyncRequest);
        updateTXNDate(hoursWorked, TimeTrackingAdd);
        updateNotes(notes, TimeTrackingAdd);
        updateBillable(hoursWorked, TimeTrackingAdd);
        doc = removeEmptyNodesFromDocument(doc);
        return doc;
    }


    private Document removeEmptyNodesFromDocument(Document doc) {
        nu.xom.Document convertedDocument = DOMConverter.convert(doc);
        xmlParserUtils.removeEmptyNodes(convertedDocument);
        return DOMConverter.convert(convertedDocument, new DOMImplementationImpl());
    }

    private void updateBillable(HoursWorked hoursWorked, Node timeTrackingAdd) {
        Node billableStatus = xmlParserUtils.getNode("BillableStatus", timeTrackingAdd.getChildNodes());
        billableStatus.setTextContent(hoursWorked.isBillable() ? "Billable" : "NotBillable");
    }

    private Node getParamsNode(Document doc) {
        NodeList root = doc.getChildNodes();
        Node QBXML = xmlParserUtils.getNode("QBXML", root).getNextSibling();
        Node QBXMLMsgsRq = xmlParserUtils.getNode("QBXMLMsgsRq", QBXML.getChildNodes());
        Node TimeTrackingAddRq = xmlParserUtils.getNode("TimeTrackingAddRq", QBXMLMsgsRq.getChildNodes());
        return xmlParserUtils.getNode("TimeTrackingAdd", TimeTrackingAddRq.getChildNodes());
    }

    private void updateChargeCode(HoursWorked hoursWorked, Node timeTrackingAdd, HoursSyncRequest hoursSyncRequest) {
        PayType payType = null;
        for (ChargeCode chargeCode : hoursWorked.authorizedCharge().getAuthorizedCharge()) {
            if (chargeCode != null) {
                if (chargeCode.getChargeCodeName().equalsIgnoreCase("Customer")) {
                    Node CustomerRef = xmlParserUtils.getNode("CustomerRef", timeTrackingAdd.getChildNodes());
                    Node ListID = xmlParserUtils.getNode("ListID", CustomerRef.getChildNodes());
                    ListID.setTextContent(((Customer) chargeCode).getQuickBooksId());
                    Node FullName = xmlParserUtils.getNode("FullName", CustomerRef.getChildNodes());
                    FullName.setTextContent(chargeCode.getName());
                    payType = extractPayType(chargeCode, hoursSyncRequest, hoursWorked);
                } else if (chargeCode.getChargeCodeName().equalsIgnoreCase("Job")) {
                    Node CustomerRef = xmlParserUtils.getNode("CustomerRef", timeTrackingAdd.getChildNodes());
                    Node ListID = xmlParserUtils.getNode("ListID", CustomerRef.getChildNodes());
                    ListID.setTextContent(((CustomerJob) chargeCode).getQuickBooksId());
                    Node FullName = xmlParserUtils.getNode("FullName", CustomerRef.getChildNodes());
                    FullName.setTextContent(chargeCode.getName());
                    payType = extractPayType(chargeCode, hoursSyncRequest, hoursWorked);
                } else if (chargeCode.getChargeCodeName().equalsIgnoreCase("Service Item")) {
                    Node ClassRef = xmlParserUtils.getNode("ItemServiceRef", timeTrackingAdd.getChildNodes());
                    Node ListID = xmlParserUtils.getNode("ListID", ClassRef.getChildNodes());
                    ListID.setTextContent(((ServiceItem) chargeCode).getQuickBooksId());
                    Node FullName = xmlParserUtils.getNode("FullName", ClassRef.getChildNodes());
                    FullName.setTextContent(chargeCode.getName());
                } else if (chargeCode.getChargeCodeName().equalsIgnoreCase("Class")) {
                    Node ClassRef = xmlParserUtils.getNode("ClassRef", timeTrackingAdd.getChildNodes());
                    Node ListID = xmlParserUtils.getNode("ListID", ClassRef.getChildNodes());
                    ListID.setTextContent(((QuickBooksClass) chargeCode).getQuickBooksId());
                    Node FullName = xmlParserUtils.getNode("FullName", ClassRef.getChildNodes());
                    FullName.setTextContent(chargeCode.getName());
                } else if (chargeCode.getChargeCodeName().equalsIgnoreCase("Leave")) {
                    payType = ((Leave) chargeCode).getPayType();
                }
            }
        }
        updatePayTypeRef(payType, timeTrackingAdd);
    }

    private void updatePayTypeRef(PayType payType, Node timeTrackingAdd) {
        if (payType != null) {
            Node payrollRef = xmlParserUtils.getNode("PayrollItemWageRef", timeTrackingAdd.getChildNodes());
            Node ListID = xmlParserUtils.getNode("ListID", payrollRef.getChildNodes());
            ListID.setTextContent(payType.getQuickBooksId());
            Node full = xmlParserUtils.getNode("FullName", payrollRef.getChildNodes());
            full.setTextContent(payType.getName());
        }
    }

    private PayType extractPayType(ChargeCode chargeCode, HoursSyncRequest hoursSyncRequest, HoursWorked hoursWorked) {
        PayType payType = null;

        if (hoursWorked.getPayType() == null) {
            List<EmployeePayType> employeePayTypes = hoursSyncRequest.getEffectivePayTypes();
            for (EmployeePayType employeePayType : employeePayTypes) {
                if (employeePayType.getChargeCodes() != null) {
                    for (ChargeCode employeeChargeCode : employeePayType.getChargeCodes()) {
                        if (employeeChargeCode.equals(chargeCode) && isPayTypeEffective(hoursSyncRequest, employeePayType)) {
                            payType = employeePayType.getPayType();
                        }
                    }
                }
            }
            if (payType == null) {
                for (EmployeePayType employeePayType : employeePayTypes) {
                    if ((employeePayType.getChargeCodes() == null || employeePayType.getChargeCodes().size() == 0) && isPayTypeEffective(hoursSyncRequest, employeePayType)) {
                        payType = employeePayType.getPayType();
                    }
                }
            }
        } else {
            payType = hoursWorked.getPayType();
        }
        return payType;
    }

    private boolean isPayTypeEffective(HoursSyncRequest hoursSyncRequest, EmployeePayType employeePayType) {
        return hoursSyncRequest.getHoursWorked().getWorkedDate().isAfterOrSame(employeePayType.getStartDate()) && (employeePayType.getEndDate() == null ? true : (hoursSyncRequest.getHoursWorked().getWorkedDate().isBeforeOrSame(employeePayType.getEndDate())));
    }


    private void updateDuration(HoursWorked hoursWorked, Node timeTrackingAdd, QBDRequest qbdRequest) {
        if (qbdRequest.getClass().equals(RegularHoursSyncRequest.class) || qbdRequest.getClass().equals(LeaveHourSyncRequest.class)) {
            Node Duration = xmlParserUtils.getNode("Duration", timeTrackingAdd.getChildNodes());
            Duration.setTextContent(transformIntoQBDHours(hoursWorked.getActualHours()));
        } else if (qbdRequest.getClass().equals(OverTimeHoursSyncRequest.class)) {
            Node Duration = xmlParserUtils.getNode("Duration", timeTrackingAdd.getChildNodes());
            Duration.setTextContent(transformIntoQBDHours(hoursWorked.getOvertime()));
        }
    }


    private void updateEmployee(HoursWorked hoursWorked, Node TimeTrackingAdd) {
        Node EntityRef = xmlParserUtils.getNode("EntityRef", TimeTrackingAdd.getChildNodes());
        Node ListID = xmlParserUtils.getNode("ListID", EntityRef.getChildNodes());
        ListID.setTextContent(hoursWorked.getEmployee().getEmployeeNumber());
        Node FullName = xmlParserUtils.getNode("FullName", EntityRef.getChildNodes());
        FullName.setTextContent(hoursWorked.getEmployee().getEmployeeName());
    }

    private void updateTXNDate(HoursWorked hoursWorked, Node timeTrackingAdd) {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        Node TxnDate = xmlParserUtils.getNode("TxnDate", timeTrackingAdd.getChildNodes());
        TxnDate.setTextContent(dt.format(hoursWorked.getWorkedDate().getDateTime().toDate()));
    }

    private void updateNotes(String notes, Node timeTrackingAdd) {
        Node Notes = xmlParserUtils.getNode("Notes", timeTrackingAdd.getChildNodes());
        Notes.setTextContent(notes);
    }

    String transformIntoQBDHours(double hoursWorked) {
        int hours = (int) hoursWorked;
        double minutes = ((hoursWorked - hours) * 60);
        int mins = (int) minutes;
        double seconds = (minutes - mins) * 100;
        int secs = (int) (seconds * 60 / 100);
        return "+PT" + hours + "H" + mins + "M" + secs + "S";
    }
}