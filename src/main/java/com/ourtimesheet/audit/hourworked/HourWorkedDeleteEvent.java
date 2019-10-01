package com.ourtimesheet.audit.hourworked;

import com.ourtimesheet.audit.TimesheetAuditEvent;
import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.timesheet.hoursWorked.HoursWorked;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.UUID;

/**
 * Created by Abdus Salam on 6/19/2016.
 */
public class HourWorkedDeleteEvent extends TimesheetAuditEvent {
    private static final String DELETE_HOUR_WORKED_MESSAGE = "Removed hours ${timeValue} on ${dateValue} against ${chargeCodes}";
    private final HoursWorked hoursWorked;

    @PersistenceConstructor
    public HourWorkedDeleteEvent(UUID id, String user, OurDateTime timestamp, String ipAddress, Employee employee, OurDateTime dateOfTimesheetComponent, HoursWorked hoursWorked) {
        super(id, user, timestamp, ipAddress, employee, dateOfTimesheetComponent, hoursWorked.getRevisionNumber());
        this.hoursWorked = hoursWorked;
    }

    public HourWorkedDeleteEvent(String user, OurDateTime timestamp, String ipAddress, HoursWorked hoursWorked) {
        super(user, timestamp, ipAddress, hoursWorked.getEmployee(), hoursWorked.getWorkedDate(), hoursWorked.getRevisionNumber());
        this.hoursWorked = hoursWorked;
    }

    @Override
    public String getEventDescription() {
        return HourWorkedEventDescription.getEventDescription(hoursWorked, DELETE_HOUR_WORKED_MESSAGE);
    }

    @Override
    public OurDateTime getDateOfTimesheetComponentByType() {
        return super.getDateOfTimesheetComponent();
    }
}
