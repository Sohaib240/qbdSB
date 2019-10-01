package com.ourtimesheet.timesheet;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.export.ExportConfigurationType;
import com.ourtimesheet.timesheet.hoursWorked.Note;
import com.ourtimesheet.timesheet.signature.Signature;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Abdus Salam on 8/3/2016.
 */
public class RejectedTimesheet extends Timesheet {

    private final String rejectionReason;

    @PersistenceConstructor
    public RejectedTimesheet(UUID id, OurDateTime startDate, OurDateTime endDate, Employee employee, List<Signature> signatureList, Note note, String rejectionReason, int revisionNumber, Map<String, Boolean> exportedConfigurations) {
        super(id, startDate, endDate, employee, signatureList, note, revisionNumber,exportedConfigurations);
        this.rejectionReason = rejectionReason;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    @Override
    public TimesheetStatus getStatus() {
        return TimesheetStatus.REJECTED;
    }

    @Override
    public Timesheet submit(Signature signature) {
        super.sign(signature);
        return new SubmittedTimesheet(super.getId(), super.getStartDate(), super.getEndDate(), super.getEmployee(), super.getSignatureList(), super.getNote(), super.getRevisionNumber(),super.getExportedConfigurations());
    }

    @Override
    public Timesheet approve(Signature signature) {
        super.sign(signature);
        return new ApprovedWithoutSignatureTimesheet(super.getId(), super.getStartDate(), super.getEndDate(), super.getEmployee(), super.getSignatureList(), super.getNote(), super.getRevisionNumber(),super.getExportedConfigurations());

    }

    @Override
    public Timesheet reject(String rejectionReason) {
        throw new IllegalStateException("Rejected timesheet cannot be rejected again");
    }

    @Override
    public Timesheet unSubmit() {
        throw new IllegalStateException("Rejected timesheet cannot be unsubmitted again");
    }

    @Override
    public boolean isEditable() {
        return true;
    }

    @Override
    public Timesheet unApprove() {
        throw new IllegalStateException("Rejected timesheet cannot be unapproved");
    }

    @Override
    public boolean isExportable() {
        return false;
    }

    @Override
    public Timesheet process(OurDateTime timestamp) {
        throw new IllegalStateException("Rejected timesheet cannot be processed");
    }

    @Override
    public Timesheet processSuccess(OurDateTime timeStamp, ExportConfigurationType exportConfigurationType, boolean processTimesheet) {
        throw new IllegalStateException("Export Process successful.");
    }

    @Override
    public Timesheet processFailed() {
        throw new IllegalStateException("Export Process failed due to unexpected reason.");
    }

    @Override
    public Timesheet export(ExportConfigurationType exportConfigurationType) {
        throw new IllegalStateException("Rejected timesheet cannot be exported");
    }

    @Override
    public Timesheet revise(OurDateTime processedTime) {
        throw new IllegalStateException("Rejected timesheet cannot be corrected");
    }

    @Override
    public OurDateTime getPrecessingDate() {
        return null;
    }
}
