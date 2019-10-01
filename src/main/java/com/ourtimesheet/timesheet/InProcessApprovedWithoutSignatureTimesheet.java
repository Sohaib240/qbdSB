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
 * Created by Abdus Salam on 3/1/2017.
 */
public class InProcessApprovedWithoutSignatureTimesheet extends Timesheet {

    @PersistenceConstructor
    public InProcessApprovedWithoutSignatureTimesheet(UUID id, OurDateTime startDate, OurDateTime endDate, Employee employee, List<Signature> signatureList, Note note, int revisionNumber, Map<String, Boolean> exportedConfigurations) {
        super(id, startDate, endDate, employee, signatureList, note, revisionNumber, exportedConfigurations);
    }

    @Override
    public TimesheetStatus getStatus() {
        return TimesheetStatus.PROCESSING_TIMESHEET_WITHOUT_SIGNATURE;
    }

    @Override
    public Timesheet submit(Signature signature) {
        super.sign(signature);
        return new InProcessApprovedTimesheet(super.getId(), super.getStartDate(), super.getEndDate(), super.getEmployee(), super.getSignatureList(), super.getNote(), super.getRevisionNumber(), super.getExportedConfigurations());

    }

    @Override
    public Timesheet approve(Signature signature) {
        throw new IllegalStateException("Timesheet In Process without signature cannot be approved");
    }

    @Override
    public Timesheet reject(String rejectionReason) {
        throw new IllegalStateException("Timesheet In Process without signature cannot be rejected");
    }

    @Override
    public Timesheet unSubmit() {
        throw new IllegalStateException("Timesheet In Process without signature cannot be un Submitted");
    }

    @Override
    public boolean isEditable() {
        return false;
    }

    @Override
    public Timesheet unApprove() {
        throw new IllegalStateException("Timesheet In Process without signature cannot be un Approved");
    }

    @Override
    public boolean isExportable() {
        return false;
    }

    @Override
    public Timesheet process(OurDateTime timestamp) {
        throw new IllegalStateException("Timesheet In Process without signature cannot be processed again");
    }

    @Override
    public Timesheet processSuccess(OurDateTime timeStamp, ExportConfigurationType exportConfigurationType, boolean processTimesheet) {
        Map<String, Boolean> exportedConfigurations = super.getExportedConfigurations();
        exportedConfigurations.put(exportConfigurationType.toString(), true);
        if (processTimesheet)
            return new ProcessedTimesheet(super.getId(), super.getStartDate(), super.getEndDate(), super.getEmployee(), super.getSignatureList(), super.getNote(), timeStamp, super.getRevisionNumber(), super.getExportedConfigurations());
        else
            return new ExportedWithoutSignatureTimesheet(super.getId(), super.getStartDate(), super.getEndDate(), super.getEmployee(), super.getSignatureList(), super.getNote(), super.getRevisionNumber(), exportedConfigurations);

    }

    @Override
    public Timesheet processFailed() {
        Map<String, Boolean> exportedConfigurations = super.getExportedConfigurations();
        exportedConfigurations.put(ExportConfigurationType.QBD.toString(), false);
        if (super.containsAnyExportConfiguration())
            return new ExportedWithoutSignatureTimesheet(super.getId(), super.getStartDate(), super.getEndDate(), super.getEmployee(), super.getSignatureList(), super.getNote(), super.getRevisionNumber(), exportedConfigurations);
        else
            return new ApprovedWithoutSignatureTimesheet(super.getId(), super.getStartDate(), super.getEndDate(), super.getEmployee(), super.getSignatureList(), super.getNote(), super.getRevisionNumber(), exportedConfigurations);
    }

    @Override
    public Timesheet export(ExportConfigurationType exportConfigurationType) {
        throw new IllegalStateException("Exported timesheet cannot be processed");
    }

    @Override
    public Timesheet revise(OurDateTime processedTime) {
        throw new IllegalStateException("In Process Approved without signature timesheet cannot be corrected");
    }

    @Override
    public OurDateTime getPrecessingDate() {
        return null;
    }
}
