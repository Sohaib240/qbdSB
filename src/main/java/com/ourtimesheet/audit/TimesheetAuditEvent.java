package com.ourtimesheet.audit;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.employee.Employee;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.UUID;

/**
 * Created by hassan on 6/14/16.
 */
public abstract class TimesheetAuditEvent extends AuditEvent {

    private final Employee employee;

    private final OurDateTime dateOfTimesheetComponent;

    private final int revisionNumber;

    @PersistenceConstructor
    public TimesheetAuditEvent(UUID id, String user, OurDateTime timestamp, String ipAddress, Employee employee, OurDateTime dateOfTimesheetComponent, int revisionNumber) {
        super(id, user, timestamp, ipAddress, AuditEventCategory.TIMESHEET);
        this.employee = employee;
        this.dateOfTimesheetComponent = dateOfTimesheetComponent;
        this.revisionNumber = revisionNumber;
    }

    public TimesheetAuditEvent(String user, OurDateTime timestamp, String ipAddress, Employee employee, OurDateTime dateOfTimesheetComponent, int revisionNumber) {
        super(user, timestamp, ipAddress, AuditEventCategory.TIMESHEET);
        this.employee = employee;
        this.dateOfTimesheetComponent = dateOfTimesheetComponent;
        this.revisionNumber = revisionNumber;
    }

    public OurDateTime getDateOfTimesheetComponent() {
        return dateOfTimesheetComponent;
    }

    public abstract OurDateTime getDateOfTimesheetComponentByType();
}
