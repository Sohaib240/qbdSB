package com.ourtimesheet.paidTimeOff.log;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.paidTimeOff.Rule.LeaveRule;

/**
 * Created by Abdus Salam on 1/12/2018.
 */
public class CarryOverLeaveLog extends LeaveLog {

    private OurDateTime carryOverDate;
    private double carryOverAmount;
    private LeaveRule leaveRule;

    public CarryOverLeaveLog(OurDateTime carryOverDate, double carryOverAmount, LeaveRule leaveRule) {
        this.carryOverDate = carryOverDate;
        this.carryOverAmount = carryOverAmount;
        this.leaveRule = leaveRule;
    }
}
