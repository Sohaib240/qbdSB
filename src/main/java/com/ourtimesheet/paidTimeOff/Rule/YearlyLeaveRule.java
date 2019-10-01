package com.ourtimesheet.paidTimeOff.Rule;

import com.ourtimesheet.paidTimeOff.RuleEffectiveDuration;
import com.ourtimesheet.paidTimeOff.accumulatedBalance.MaximumAccumulatedBalance;
import com.ourtimesheet.paidTimeOff.carryOver.CarryOver;
import com.ourtimesheet.paidTimeOff.overDrawConfiguration.OverDrawConfiguration;
import com.ourtimesheet.timesheet.configuration.DayOfMonth;

import java.time.Month;
import java.util.UUID;

/**
 * Created by Abdus Salam on 12/20/2017.
 */
public class YearlyLeaveRule extends LeaveRule {

    private final DayOfMonth dayOfMonth;
    private final Month month;

    public YearlyLeaveRule(UUID id, RuleEffectiveDuration ruleEffectiveDuration, DayOfMonth dayOfMonth,
                           Month month, double accrualQuantity, CarryOver carryOver,
                           MaximumAccumulatedBalance maximumAccumulatedBalance, OverDrawConfiguration overDrawConfiguration) {
        super(id, ruleEffectiveDuration, accrualQuantity, carryOver, maximumAccumulatedBalance, overDrawConfiguration);
        this.dayOfMonth = dayOfMonth;
        this.month = month;
    }
}