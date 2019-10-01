package com.ourtimesheet.audit.hourworked;

import com.ourtimesheet.audit.TimesheetAuditEvent;
import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.timesheet.hoursWorked.HoursWorked;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Abdus Salam on 6/19/2016.
 */
public class HourWorkedUpdateEvent extends TimesheetAuditEvent {

    private static final String UPDATE_HOUR_WORKED_MESSAGE = "Updated hours from ${originalTimeValue} to ${newTimeValue} on date: ${dateValue} against ${chargeCodes}";
    private final HoursWorked originalHoursWorked;
    private final HoursWorked newHourWorked;

    @PersistenceConstructor
    public HourWorkedUpdateEvent(UUID id, String user, OurDateTime timestamp, String ipAddress, Employee employee, OurDateTime dateOfTimesheetComponent, HoursWorked originalHoursWorked, HoursWorked newHourWorked) {
        super(id, user, timestamp, ipAddress, employee, dateOfTimesheetComponent, originalHoursWorked.getRevisionNumber());
        this.originalHoursWorked = originalHoursWorked;
        this.newHourWorked = newHourWorked;
    }

    public HourWorkedUpdateEvent(String user, OurDateTime timestamp, String ipAddress, HoursWorked originalHoursWorked, HoursWorked newHourWorked) {
        super(user, timestamp, ipAddress, originalHoursWorked.getEmployee(), originalHoursWorked.getWorkedDate(), originalHoursWorked.getRevisionNumber());
        this.originalHoursWorked = originalHoursWorked;
        this.newHourWorked = newHourWorked;
    }


    @Override
    public String getEventDescription() {

        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("dateValue", originalHoursWorked.hourWorkedDateOnlyFormatted());
        valuesMap.put("originalTimeValue", originalHoursWorked.hourWorkedTimeOnly());
        valuesMap.put("newTimeValue", newHourWorked.hourWorkedTimeOnly());
        valuesMap.put("chargeCodes", originalHoursWorked.authorizedCharges());
        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        return sub.replace(UPDATE_HOUR_WORKED_MESSAGE);
    }

    @Override
    public OurDateTime getDateOfTimesheetComponentByType() {
        return super.getDateOfTimesheetComponent();
    }
}
