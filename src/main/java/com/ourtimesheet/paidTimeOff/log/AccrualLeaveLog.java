package com.ourtimesheet.paidTimeOff.log;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.paidTimeOff.Rule.LeaveRule;

/**
 * Created by Abdus Salam on 12/24/2017.
 */
public class AccrualLeaveLog extends LeaveLog {

    private OurDateTime accrualDate;
    private double accrualAmount;
    private OurDateTime startPeriod;
    private OurDateTime endPeriod;
    private LeaveRule leaveRule;

    public AccrualLeaveLog(OurDateTime accrualDate, double accrualAmount, OurDateTime startPeriod, OurDateTime endPeriod, LeaveRule leaveRule) {
        this.accrualDate = accrualDate;
        this.accrualAmount = accrualAmount;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.leaveRule = leaveRule;
    }

}