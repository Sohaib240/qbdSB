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
public class ProcessedTimesheet extends Timesheet {

    private final OurDateTime processingDate;

    @PersistenceConstructor
    public ProcessedTimesheet(UUID id, OurDateTime startDate, OurDateTime endDate, Employee employee, List<Signature> signatureList, Note note, OurDateTime processingDate, int revisionNumber, Map<String, Boolean> exportedConfigurations) {
        super(id, startDate, endDate, employee, signatureList, note, revisionNumber, exportedConfigurations);
        this.processingDate = processingDate;
    }

    public OurDateTime getProcessingDate() {
        return processingDate;
    }

    @Override
    public TimesheetStatus getStatus() {
        return TimesheetStatus.PROCESSED;
    }

    @Override
    public Timesheet submit(Signature signature) {
        super.sign(signature);
        return new ProcessedTimesheet(super.getId(), super.getStartDate(), super.getEndDate(), super.getEmployee(), super.getSignatureList(), super.getNote(), getProcessingDate(), super.getRevisionNumber(), super.getExportedConfigurations());
    }

    @Override
    public Timesheet approve(Signature signature) {
        throw new IllegalStateException("Processed timesheet cannot be approved again");
    }

    @Override
    public Timesheet reject(String rejectionReason) {
        throw new IllegalStateException("Processed timesheet cannot be rejected");
    }

    @Override
    public Timesheet unSubmit() {
        throw new IllegalStateException("Processed timesheet cannot be un Submitted");
    }

    @Override
    public boolean isEditable() {
        return false;
    }

    @Override
    public Timesheet unApprove() {
        throw new IllegalStateException("Processed timesheet cannot be unApproved");
    }

    @Override
    public boolean isExportable() {
        return false;
    }

    @Override
    public Timesheet process(OurDateTime timestamp) {
        return this;
    }

    @Override
    public Timesheet processSuccess(OurDateTime timeStamp, ExportConfigurationType exportConfigurationType, boolean processTimesheet) {
        throw new IllegalStateException("Processed timesheet cannot be processed again");
    }

    @Override
    public Timesheet processFailed() {
        throw new IllegalStateException("Processed timesheet cannot be processed again");
    }

    @Override
    public Timesheet export(ExportConfigurationType exportConfigurationType) {
        throw new IllegalStateException("Processed timesheet cannot be exported");
    }

    @Override
    public OurDateTime getPrecessingDate() {
        return processingDate;
    }

    @Override
    public Timesheet revise(OurDateTime processedTime) {
        return new RevisedTimesheet(super.getId(), super.getStartDate(), super.getEndDate(), super.getEmployee(), super.getSignatureList(), super.getNote(), super.getRevisionNumber(), processingDate, super.getExportedConfigurations());
    }
}
