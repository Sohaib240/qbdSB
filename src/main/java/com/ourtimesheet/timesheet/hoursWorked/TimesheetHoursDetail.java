package com.ourtimesheet.timesheet.hoursWorked;

import com.ourtimesheet.paytype.PayTypeConfiguration;

import java.util.List;

/**
 * Created by Abdus Salam on 10/19/2017.
 */
public interface TimesheetHoursDetail {

    List<HoursDetail> getHoursDetailByAuthorizeCharge(PayTypeConfiguration payTypeConfiguration);
}