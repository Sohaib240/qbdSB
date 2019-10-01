package com.ourtimesheet.audit;

import java.util.UUID;

public class AuditLogSearchCriteria {

    private String timesheetId;

    public AuditLogSearchCriteria() {
    }

    public AuditLogSearchCriteria(String timesheetId) {
        this.timesheetId = timesheetId;
    }

    public UUID getTimesheetId() {
        return UUID.fromString(timesheetId);
    }

    public void setTimesheetid(String id) {
        this.timesheetId = id;
    }
}

