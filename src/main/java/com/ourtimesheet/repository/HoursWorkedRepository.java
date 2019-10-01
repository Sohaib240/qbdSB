package com.ourtimesheet.repository;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.timesheet.hoursWorked.HoursWorked;
import com.ourtimesheet.timesheet.search.HoursWorkedCriteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.UUID;

/**
 * Created by Abdus Salam on 6/7/2016.
 */
public interface HoursWorkedRepository extends Repository<HoursWorked> {

    List<HoursWorked> findHoursWorkedByCriteria(HoursWorkedCriteria hoursWorkedCriteria);
}