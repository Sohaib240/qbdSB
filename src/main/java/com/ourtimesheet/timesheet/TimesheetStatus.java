package com.ourtimesheet.timesheet;

/**
 * Created by Jazib on 8/2/2016.
 */
public enum TimesheetStatus {
    SUBMITTED("Submitted"),
    UNSUBMITTED("Unsubmitted"),
    REJECTED("Rejected"),
    APPROVED("Approved"),
    APPROVED_WITHOUT_SIGNATURE("Approved w/o Employee Signature"),
    PROCESSING_TIMESHEET("Processing"),
    PROCESSING_TIMESHEET_WITHOUT_SIGNATURE("Processing w/o Employee Signature"),
    EXPORTED("Exported"),
    EXPORTED_WITHOUT_SIGNATURE("Exported w/o Employee Signature"),
    PROCESSED("Processed"),
    REVISED("Revised"),
    NO_TIMESHEET("No Timesheet");


    private String description;

    TimesheetStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
