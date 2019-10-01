package com.ourtimesheet.timesheet.search;

import com.ourtimesheet.datetime.OurDateTime;

import java.util.UUID;

/**
 * Created by Abdus Salam on 9/27/2016.
 */
public abstract class TimesheetItemSearchCriteria {
    private UUID employeeId;
    private OurDateTime startDate;
    private OurDateTime endDate;
    private int revisionNumber;

    public TimesheetItemSearchCriteria(UUID employeeId, OurDateTime startDate, OurDateTime endDate, int revisionNumber) {
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.revisionNumber = revisionNumber;
    }

    public UUID getEmployeeUUID() {
        return employeeId;
    }

    public String getEmployeeId() {
        return employeeId.toString();
    }

    public OurDateTime getStartDate() {
        return startDate;
    }

    public OurDateTime getEndDate() {
        return endDate;
    }

    public int getRevisionNumber() {
        return revisionNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimesheetItemSearchCriteria that = (TimesheetItemSearchCriteria) o;

        if (revisionNumber != that.revisionNumber) return false;
        if (employeeId != null ? !employeeId.equals(that.employeeId) : that.employeeId != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        return endDate != null ? endDate.equals(that.endDate) : that.endDate == null;

    }

    @Override
    public int hashCode() {
        int result = employeeId != null ? employeeId.hashCode() : 0;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + revisionNumber;
        return result;
    }
}
