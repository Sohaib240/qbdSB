package com.ourtimesheet.timesheet;

import java.util.List;

public class PaginatedTimesheetResponse {
    private List<Timesheet> timesheets;
    private long totalTimesheetsCount;

    public PaginatedTimesheetResponse(List<Timesheet> timesheets, long totalTimesheetsCount) {
        this.timesheets = timesheets;
        this.totalTimesheetsCount = totalTimesheetsCount;
    }

    public List<Timesheet> getTimesheets() {
        return timesheets;
    }

    public int getTotalCount() {
        return (int) totalTimesheetsCount;
    }
}
