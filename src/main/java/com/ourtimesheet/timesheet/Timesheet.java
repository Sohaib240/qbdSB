package com.ourtimesheet.timesheet;

import com.ourtimesheet.audit.AuditEvent;
import com.ourtimesheet.common.Entity;
import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.export.ExportConfigurationType;
import com.ourtimesheet.paytype.PayTypeConfiguration;
import com.ourtimesheet.timesheet.configuration.TimesheetDatePair;
import com.ourtimesheet.timesheet.hoursWorked.*;
import com.ourtimesheet.timesheet.increment.HoursIncrement;
import com.ourtimesheet.timesheet.punch.InOutMatrix;
import com.ourtimesheet.timesheet.punch.Punch;
import com.ourtimesheet.timesheet.signature.Signature;
import com.ourtimesheet.timesheet.signature.SupervisorSignature;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by click chain on 4/26/2016.
 */
@Document(collection = "timesheet")
public abstract class Timesheet extends Entity {

    private final int revisionNumber;
    private final OurDateTime startDate;
    private final OurDateTime endDate;
    @DBRef(lazy = true)
    private Employee employee;
    private String employeeName;
    private List<Signature> signatureList;
    private Map<String, Boolean> exportedConfigurations;

    @Transient
    private List<Punch> punchList = new ArrayList<>();

    @Transient
    private List<HoursWorked> hoursWorkedList = new ArrayList<>();

    @Transient
    private List<AuditEvent> auditEvents = new ArrayList<>();
    private Note note;

    @PersistenceConstructor
    public Timesheet(UUID id, OurDateTime startDate, OurDateTime endDate, List<Signature> signatureList, Note note, int revisionNumber, Map<String, Boolean> exportedConfigurations) {
        super(id);
        this.startDate = startDate;
        this.endDate = endDate;
        this.signatureList = signatureList;
        this.note = note;
        this.revisionNumber = revisionNumber;
        this.exportedConfigurations = exportedConfigurations;
    }

    public Timesheet(UUID id, OurDateTime startDate, OurDateTime endDate, Employee employee, List<Signature> signatureList, Note note, int revisionNumber, Map<String, Boolean> exportedConfigurations) {
        super(id);
        this.startDate = startDate;
        this.endDate = endDate;
        this.employee = employee;
        this.signatureList = signatureList;
        this.note = note;
        this.revisionNumber = revisionNumber;
        this.employeeName = employee != null ? employee.getEmployeeName() : StringUtils.EMPTY;
        this.exportedConfigurations = exportedConfigurations;
    }

    public Timesheet(OurDateTime startDate, OurDateTime endDate, Employee employee, List<Signature> signatureList, int revisionNumber, Map<String, Boolean> exportedConfigurations) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
        this.employee = employee;
        this.signatureList = signatureList;
        this.note = null;
        this.revisionNumber = revisionNumber;
        this.employeeName = employee != null ? employee.getEmployeeName() : StringUtils.EMPTY;
        this.exportedConfigurations = exportedConfigurations;
    }

    public boolean equals(Timesheet timesheet) {
        return timesheet.getStartDate().equals(this.getStartDate()) && timesheet.getEndDate().equals(this.getEndDate());
    }

    //TODO use other mechanism
    public void setPunchList(List<Punch> punchList) {
        this.punchList = punchList;
    }

    public void setHoursWorkedList(List<HoursWorked> hoursWorkedList) {
        this.hoursWorkedList = hoursWorkedList;
    }

    public List<AuditEvent> getAuditEvents() {
        return auditEvents;
    }

    public void setAuditEvents(List<AuditEvent> auditEvents) {
        this.auditEvents = auditEvents;
    }

    public List<Signature> getSignatureList() {
        return signatureList;
    }

    public OurDateTime getStartDate() {
        return startDate;
    }

    public OurDateTime getEndDate() {
        return endDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    protected void sign(Signature signature) {
        signatureList.add(signature);
    }

    protected void unSign() {
        signatureList = new ArrayList<>();
    }

    public void addNote(Note notes) {
        this.note = notes;
    }

    public String getNoteMessage() {
        return note != null ? note.getMessage() : "";
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public Note getNote() {
        return note;
    }

    public Map<String, Boolean> getExportedConfigurations() {
        return exportedConfigurations != null ? exportedConfigurations : new HashMap<>();
    }

    public TimesheetDatePair getTimesheetDatePair() {
        return new TimesheetDatePair(startDate, endDate);
    }

    public void removeSupervisorSignatures() {
        signatureList = signatureList.stream().filter(signature -> !(signature.getClass().isAssignableFrom(SupervisorSignature.class))).collect(Collectors.toList());
    }

    public InOutMatrix inOutMatrix(HoursIncrement hoursIncrement) {
        return new InOutMatrix(punchList, startDate, endDate, hoursIncrement);
    }

    public HoursWorkedMatrix hoursWorkedMatrix() {
        return new HoursWorkedMatrix(hoursWorkedList, employee.getOvertimeRule());
    }

    public int getRevisionNumber() {
        return revisionNumber;
    }

    public List<HoursDetail> getHoursWorkedWithAssignedPayType(PayTypeConfiguration payTypeConfiguration) {
        return new TimesheetHoursDetailImpl(this).getHoursDetailByAuthorizeCharge(payTypeConfiguration);
    }

    public abstract TimesheetStatus getStatus();

    public abstract Timesheet submit(Signature signature);

    public abstract Timesheet approve(Signature signature);

    public abstract Timesheet reject(String rejectionReason);

    public abstract Timesheet unSubmit();

    public abstract boolean isEditable();

    public abstract Timesheet unApprove();

    public abstract boolean isExportable();

    public abstract Timesheet process(OurDateTime timestamp);

    public abstract Timesheet processSuccess(OurDateTime timeStamp, ExportConfigurationType exportConfigurationType, boolean processTimesheet);

    public abstract Timesheet processFailed();

    public abstract Timesheet export(ExportConfigurationType exportConfigurationType);

    public abstract Timesheet revise(OurDateTime processedTime);

    public abstract OurDateTime getPrecessingDate();

    public boolean isSigned() {
        return signatureList != null && signatureList.size() > 0;
    }


    protected boolean containsAnyExportConfiguration() {
        return (getExportedConfigurations() != null && !getExportedConfigurations().isEmpty())
                && ExportConfigurationType.getFileConfigurationTypes()
                .stream()
                .anyMatch(exportConfigurationType -> getExportedConfigurations().keySet().contains(exportConfigurationType));
    }
}
