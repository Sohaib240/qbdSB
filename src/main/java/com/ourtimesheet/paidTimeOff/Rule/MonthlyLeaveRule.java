package com.ourtimesheet.paidTimeOff.Rule;

import com.ourtimesheet.paidTimeOff.RuleEffectiveDuration;
import com.ourtimesheet.paidTimeOff.accumulatedBalance.MaximumAccumulatedBalance;
import com.ourtimesheet.paidTimeOff.carryOver.CarryOver;
import com.ourtimesheet.paidTimeOff.overDrawConfiguration.OverDrawConfiguration;
import com.ourtimesheet.timesheet.configuration.DayOfMonth;

import java.util.UUID;

/**
 * Created by Talha on 1/9/2018.
 */
public class MonthlyLeaveRule extends LeaveRule {

    private final DayOfMonth dayOfMonth;

    public MonthlyLeaveRule(UUID id, RuleEffectiveDuration ruleEffectiveDuration, DayOfMonth dayOfMonth,
                            double accrualQuantity, CarryOver carryOver,
                            MaximumAccumulatedBalance maximumAccumulatedBalance, OverDrawConfiguration overDrawConfiguration) {
        super(id, ruleEffectiveDuration, accrualQuantity, carryOver, maximumAccumulatedBalance, overDrawConfiguration);
        this.dayOfMonth = dayOfMonth;
    }


}