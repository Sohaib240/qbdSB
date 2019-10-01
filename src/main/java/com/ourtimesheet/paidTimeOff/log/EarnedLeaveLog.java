package com.ourtimesheet.paidTimeOff.log;

/**
 * Created by Talha on 2/9/2018.
 */

public class EarnedLeaveLog extends LeaveLog {

    private String timesheetId;
    private double lostHoursAdded;

    public EarnedLeaveLog(String timesheetId, double lostHoursAdded) {
        this.timesheetId = timesheetId;
        this.lostHoursAdded = lostHoursAdded;
    }
}