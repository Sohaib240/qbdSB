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

import static com.ourtimesheet.timesheet.TimesheetStatus.REVISED;

public class RevisedTimesheet extends Timesheet {
    private final OurDateTime processingDate;

    @PersistenceConstructor
    public RevisedTimesheet(UUID id, OurDateTime startDate, OurDateTime endDate, Employee employee, List<Signature> signatureList, Note note, int revisionNumber, OurDateTime processingDate, Map<String, Boolean> exportedConfigurations) {
        super(id, startDate, endDate, employee, signatureList, note, revisionNumber,exportedConfigurations);
        this.processingDate = processingDate;
    }

    @Override
    public TimesheetStatus getStatus() {
        return REVISED;
    }

    @Override
    public Timesheet submit(Signature signature) {
        throw new IllegalStateException("Corrected timesheet cannot be submitted");
    }

    @Override
    public Timesheet approve(Signature signature) {
        throw new IllegalStateException("Corrected timesheet cannot be approved");
    }

    @Override
    public Timesheet reject(String rejectionReason) {
        throw new IllegalStateException("Corrected timesheet cannot be rejected");
    }

    @Override
    public Timesheet unSubmit() {
        throw new IllegalStateException("Corrected timesheet cannot be un submitted");
    }

    @Override
    public boolean isEditable() {
        return false;
    }

    @Override
    public Timesheet unApprove() {
        throw new IllegalStateException("Corrected timesheet cannot be un Approve");
    }

    @Override
    public boolean isExportable() {
        return false;
    }

    @Override
    public Timesheet process(OurDateTime timestamp) {
        throw new IllegalStateException("Corrected timesheet cannot be process");
    }

    @Override
    public Timesheet processSuccess(OurDateTime timeStamp, ExportConfigurationType exportConfigurationType, boolean processTimesheet) {
        throw new IllegalStateException("Corrected timesheet cannot be processed");
    }

    @Override
    public Timesheet processFailed() {
        throw new IllegalStateException("Corrected timesheet cannot be processed");
    }

    @Override
    public Timesheet export(ExportConfigurationType exportConfigurationType) {
        throw new IllegalStateException("Corrected timesheet cannot be exported");
    }

    @Override
    public Timesheet revise(OurDateTime processedTime) {
        throw new IllegalStateException("Corrected timesheet cannot be corrected");
    }

    @Override
    public OurDateTime getPrecessingDate() {
        return null;
    }

}
