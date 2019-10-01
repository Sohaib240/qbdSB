package com.ourtimesheet.service;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.timesheet.Timesheet;
import com.ourtimesheet.timesheet.hoursWorked.HoursWorked;

import java.util.List;
import java.util.UUID;

public interface TimesheetService {

    Timesheet findById(UUID uuid);

    void processTimesheetWithoutAudit(List<Timesheet> timesheetId);

}
