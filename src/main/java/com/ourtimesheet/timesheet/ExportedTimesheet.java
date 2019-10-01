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
 * Created by Talha on 8/24/2017.
 */
public class ExportedTimesheet extends Timesheet {

    @PersistenceConstructor
    public ExportedTimesheet(UUID id, OurDateTime startDate, OurDateTime endDate, Employee employee, List<Signature> signatureList, Note note, int revisionNumber, Map<String, Boolean> exportedConfigurations) {
        super(id, startDate, endDate, employee, signatureList, note, revisionNumber, exportedConfigurations);
    }

    @Override
    public TimesheetStatus getStatus() {
        return TimesheetStatus.EXPORTED;
    }

    @Override
    public Timesheet submit(Signature signature) {
        throw new IllegalStateException("Exported timesheet cannot be submitted");
    }

    @Override
    public Timesheet approve(Signature signature) {
        throw new IllegalStateException("Exported timesheet cannot be approved again");
    }

    @Override
    public Timesheet reject(String rejectionReason) {
        throw new IllegalStateException("Exported timesheet cannot be rejected");
    }

    @Override
    public Timesheet unSubmit() {
        throw new IllegalStateException("Exported timesheet cannot be un Submitted");
    }

    @Override
    public boolean isEditable() {
        return false;
    }

    @Override
    public Timesheet unApprove() {
        throw new IllegalStateException("Exported timesheet cannot be un Approve");
    }

    @Override
    public boolean isExportable() {
        return true;
    }

    @Override
    public Timesheet process(OurDateTime timeStamp) {
        Map<String, Boolean> exportedConfigurations = super.getExportedConfigurations();
        Map<String, Boolean> updatedConfigurations = new HashMap<>();
        exportedConfigurations.forEach((s, aBoolean) -> updatedConfigurations.put(s, true));
        return new InProcessApprovedTimesheet(super.getId(), super.getStartDate(), super.getEndDate(), super.getEmployee(), super.getSignatureList(), super.getNote(), super.getRevisionNumber(), updatedConfigurations);
    }

    @Override
    public Timesheet processSuccess(OurDateTime timeStamp, ExportConfigurationType exportConfigurationType, boolean processTimesheet) {
        if (processTimesheet)
            return new ProcessedTimesheet(super.getId(), super.getStartDate(), super.getEndDate(), super.getEmployee(), super.getSignatureList(), super.getNote(), timeStamp, super.getRevisionNumber(), super.getExportedConfigurations());
        else
            return this;
    }

    @Override
    public Timesheet processFailed() {
        return null;
    }

    @Override
    public Timesheet export(ExportConfigurationType exportConfigurationType) {
        return this;
    }

    @Override
    public Timesheet revise(OurDateTime processedTime) {
        throw new IllegalStateException("Exported timesheet cannot be corrected");
    }

    @Override
    public OurDateTime getPrecessingDate() {
        return null;
    }
}
