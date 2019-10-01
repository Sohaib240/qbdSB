package com.hourtimesheet.services;

import com.hourtimesheet.delegate.QBDServiceDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.util.ArrayList;

/**
 * Created by Abdus Salam on 1/23/2017.
 */
@Component
@WebService(endpointInterface = "com.hourtimesheet.services.QuickBooksDesktopService", serviceName = "qbd")
public class QuickBooksDesktopServiceImpl implements QuickBooksDesktopService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuickBooksDesktopServiceImpl.class);

    private QBDServiceDelegate QBDServiceDelegate;

    public void setQBDServiceDelegate(QBDServiceDelegate QBDServiceDelegate) {
        this.QBDServiceDelegate = QBDServiceDelegate;
    }

    @Override
    public ArrayOfString authenticate(String strUserName, String strPassword) {
        ArrayOfString arr = new ArrayOfString();
        arr.string = new ArrayList<>();
        arr.string.add(QBDServiceDelegate.authenticate(strUserName, strPassword));
        arr.string.add("");
        return arr;
    }

    @Override
    public String clientVersion(String strVersion) {
        return "";
    }

    @Override
    public String serverVersion(String ticket) {
        return "1";
    }

    @Override
    public String closeConnection(String ticket) {
        QBDServiceDelegate.endSession(ticket);
        return ticket.equals("nvu") ? "Authentication Failed" : "Successfully closed";
    }

    @Override
    public String connectionError(String ticket, String hresult, String message) {
        LOGGER.info("Entered connection error with response {}", hresult);
        return "done";
    }

    @Override
    public String getLastError(String ticket) {
        return ticket.equals("nvu") ? "Authentication Failed" : "Some Error Occurred";
    }

    @Override
    public String getInteractiveURL(String wcTicket, String sessionID) {
        return "1";
    }

    @Override
    public String interactiveDone(String wcTicket) {
        return "1";
    }

    @Override
    public String interactiveRejected(String wcTicket, String reason) {
        return "1";
    }

    @Override
    public int receiveResponseXML(String ticket, String response, String hresult, String message) {
        try {
            QBDServiceDelegate.saveEntities(response, ticket);
            if (QBDServiceDelegate.isAnyQueryToBeProcessed(ticket)) {
                return 50;
            } else {
                QBDServiceDelegate.sendFinishEmail(ticket);
                return 100;
            }
        } catch (Exception e) {
            LOGGER.warn("Error in receiving xml response with error {}", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String sendRequestXML(String ticket, String strHCPResponse, String strCompanyFileName, String qbXMLCountry, int qbXMLMajorVers, int qbXMLMinorVers) {
        return QBDServiceDelegate.getQueryStringForNextRequest(ticket);
    }
}