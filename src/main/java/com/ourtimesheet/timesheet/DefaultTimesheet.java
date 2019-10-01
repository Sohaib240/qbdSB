package com.ourtimesheet.timesheet;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.export.ExportConfigurationType;
import com.ourtimesheet.timesheet.signature.Signature;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public class DefaultTimesheet extends Timesheet {

    public DefaultTimesheet(UUID id, OurDateTime startDate, OurDateTime endDate, Employee employee, List<Signature> signatureList, int revisionNumber, Map<String, Boolean> exportedConfigurations) {
        super(id, startDate, endDate, employee, signatureList, null, revisionNumber, exportedConfigurations);

    }

    @Override
    public TimesheetStatus getStatus() {
        return TimesheetStatus.NO_TIMESHEET;
    }

    @Override
    public Timesheet submit(Signature signature) {
        throw new IllegalStateException("No Timesheet , cannot be submitted");
    }

    @Override
    public Timesheet approve(Signature signature) {
        throw new IllegalStateException("No Timesheet , cannot be approved");
    }

    @Override
    public Timesheet reject(String rejectionReason) {
        throw new IllegalStateException("No Timesheet , cannot be rejected");
    }

    @Override
    public Timesheet unSubmit() {
        throw new IllegalStateException("No Timesheet , to unsubmit, it has to be submitted first");
    }

    @Override
    public boolean isEditable() {
        throw new IllegalStateException("No TimeSheet , cannot edit");
    }

    @Override
    public Timesheet unApprove() {
        throw new IllegalStateException("No Timesheet available , cannot be approved");
    }

    @Override
    public boolean isExportable() {
        throw new IllegalStateException("No timesheet available to export");
    }

    @Override
    public Timesheet process(OurDateTime timestamp) {
        throw new IllegalStateException("NO Timesheet available to process");
    }

    @Override
    public Timesheet processSuccess(OurDateTime timeStamp, ExportConfigurationType exportConfigurationType, boolean processTimesheet) {
        throw new IllegalStateException("No Timesheet available");
    }

    @Override
    public Timesheet processFailed() {
        throw new IllegalStateException("No Timesheet available");
    }

    @Override
    public Timesheet export(ExportConfigurationType exportConfigurationType) {
        throw new IllegalStateException("No Timesheet available");
    }

    @Override
    public Timesheet revise(OurDateTime processedTime) {
        throw new IllegalStateException("No Timesheet available");
    }

    @Override
    public OurDateTime getPrecessingDate() {
        throw new IllegalStateException("No Timesheet available");
    }
}
