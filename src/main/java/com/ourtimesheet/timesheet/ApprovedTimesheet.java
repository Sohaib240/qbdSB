package com.ourtimesheet.timesheet;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.export.ExportConfigurationType;
import com.ourtimesheet.timesheet.hoursWorked.Note;
import com.ourtimesheet.timesheet.signature.Signature;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Abdus Salam on 8/3/2016.
 */
public class ApprovedTimesheet extends Timesheet {

    @PersistenceConstructor
    public ApprovedTimesheet(UUID id, OurDateTime startDate, OurDateTime endDate, Employee employee, List<Signature> signatureList, Note note, int revisionNumber, Map<String, Boolean> exportedConfigurations) {
        super(id, startDate, endDate, employee, signatureList, note, revisionNumber, exportedConfigurations);
    }

    @Override
    public TimesheetStatus getStatus() {
        return TimesheetStatus.APPROVED;
    }

    @Override
    public Timesheet submit(Signature signature) {
        throw new IllegalStateException("Approved timesheet cannot be submitted");
    }

    @Override
    public Timesheet approve(Signature signature) {
        super.sign(signature);
        return new ApprovedTimesheet(super.getId(), super.getStartDate(), super.getEndDate(), super.getEmployee(), super.getSignatureList(), super.getNote(), super.getRevisionNumber(), super.getExportedConfigurations());
    }

    @Override
    public Timesheet reject(String rejectionReason) {
        throw new IllegalStateException("Approved timesheet cannot be rejected");
    }

    @Override
    public Timesheet unSubmit() {
        throw new IllegalStateException("Approved timesheet cannot be un Submitted");
    }

    @Override
    public boolean isEditable() {
        return false;
    }

    @Override
    public Timesheet unApprove() {
        super.removeSupervisorSignatures();
        return new SubmittedTimesheet(super.getId(), super.getStartDate(), super.getEndDate(), super.getEmployee(), super.getSignatureList(), super.getNote(), super.getRevisionNumber(), super.getExportedConfigurations());
    }

    @Override
    public boolean isExportable() {
        return true;
    }

    @Override
    public Timesheet process(OurDateTime ourDateTime) {
        Map<String, Boolean> exportedConfigurations = super.getExportedConfigurations();
        Map<String, Boolean> updatedConfigurations = new HashMap<>();
        exportedConfigurations.forEach((s, aBoolean) -> updatedConfigurations.put(s, true));
        return new InProcessApprovedTimesheet(super.getId(), super.getStartDate(), super.getEndDate(), super.getEmployee(), super.getSignatureList(), super.getNote(), super.getRevisionNumber(), updatedConfigurations);
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
        Map<String, Boolean> exportedConfigurations = super.getExportedConfigurations();
        exportedConfigurations.put(exportConfigurationType.toString(), true);
        return new ExportedTimesheet(super.getId(), super.getStartDate(), super.getEndDate(), super.getEmployee(), super.getSignatureList(), super.getNote(), super.getRevisionNumber(), exportedConfigurations);
    }

    @Override
    public Timesheet revise(OurDateTime processedTime) {
        throw new IllegalStateException("Approved timesheet cannot be corrected");
    }

    @Override
    public OurDateTime getPrecessingDate() {
        return null;
    }
}
