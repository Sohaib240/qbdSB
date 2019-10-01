package com.ourtimesheet.job;

import com.ourtimesheet.timesheet.hoursWorked.HoursWorked;

public class ResultMapping {
    private final HoursWorked hoursWorked;

    public ResultMapping(HoursWorked hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public HoursWorked getHoursWorked() {
        return hoursWorked;
    }
}
