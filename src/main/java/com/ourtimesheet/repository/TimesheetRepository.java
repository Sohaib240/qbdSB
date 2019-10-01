package com.ourtimesheet.repository;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.timesheet.PaginatedTimesheetResponse;
import com.ourtimesheet.timesheet.Timesheet;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.UUID;

public interface TimesheetRepository extends Repository<Timesheet> {

    PaginatedTimesheetResponse findTimesheetsByQuery(Query query);


}
