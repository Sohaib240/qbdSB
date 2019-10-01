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
public class UnsubmittedTimesheet extends Timesheet {

    @PersistenceConstructor
    public UnsubmittedTimesheet(UUID id, OurDateTime startDate, OurDateTime endDate, Employee employee, List<Signature> signatureList, Note note, int revisionNumber, Map<String, Boolean> exportedConfigurations) {
        super(id, startDate, endDate, employee, signatureList, note, revisionNumber, exportedConfigurations);
    }

    public UnsubmittedTimesheet(OurDateTime startDate, OurDateTime endDate, Employee employee, List<Signature> signatureList, int revisionNumber, Map<String, Boolean> exportedConfigurations) {
        super(startDate, endDate, employee, signatureList, revisionNumber, exportedConfigurations);
    }

    @Override
    public TimesheetStatus getStatus() {
        return TimesheetStatus.UNSUBMITTED;
    }

    @Override
    public Timesheet submit(Signature signature) {
        super.sign(signature);
        return new SubmittedTimesheet(super.getId(), super.getStartDate(), super.getEndDate(), super.getEmployee(), super.getSignatureList(), super.getNote(), super.getRevisionNumber(),super.getExportedConfigurations());
    }

    @Override
    public Timesheet approve(Signature signature) {
        super.sign(signature);
        return new ApprovedWithoutSignatureTimesheet(super.getId(), super.getStartDate(), super.getEndDate(), super.getEmployee(), getSignatureList(), super.getNote(), super.getRevisionNumber(), super.getExportedConfigurations());
    }

    @Override
    public Timesheet reject(String rejectionReason) {
        throw new IllegalStateException("Unsubmitted timesheet cannot be rejected");
    }

    @Override
    public Timesheet unSubmit() {
        throw new IllegalStateException("Unsubmitted timesheet cannot be un submitted again");
    }

    @Override
    public boolean isEditable() {
        return true;
    }

    @Override
    public Timesheet unApprove() {
        throw new IllegalStateException("Unsubmitted timesheet cannot be unapproved");
    }

    @Override
    public boolean isExportable() {
        return false;
    }

    @Override
    public Timesheet process(OurDateTime timestamp) {
        throw new IllegalStateException("Unsubmitted timesheet cannot be processed");
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
        throw new IllegalStateException("Unsubmitted timesheet cannot be exported");
    }

    @Override
    public Timesheet revise(OurDateTime processedTime) {
        throw new IllegalStateException("Unsubmitted timesheet cannot be corrected");
    }

    @Override
    public OurDateTime getPrecessingDate() {
        return null;
    }
}
