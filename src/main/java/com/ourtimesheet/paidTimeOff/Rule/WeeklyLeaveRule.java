package com.ourtimesheet.paidTimeOff.Rule;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.paidTimeOff.RuleEffectiveDuration;
import com.ourtimesheet.paidTimeOff.accumulatedBalance.MaximumAccumulatedBalance;
import com.ourtimesheet.paidTimeOff.carryOver.CarryOver;
import com.ourtimesheet.paidTimeOff.overDrawConfiguration.OverDrawConfiguration;

import java.time.DayOfWeek;
import java.util.UUID;

/**
 * Created by Abdus Salam on 1/9/2018.
 */
public class WeeklyLeaveRule extends LeaveRule {

    private final DayOfWeek dayOfWeek;
    private final OurDateTime firstAccrualDueDate;

    public WeeklyLeaveRule(UUID id, RuleEffectiveDuration ruleEffectiveDuration, DayOfWeek dayOfWeek,
                           double accrualQuantity, CarryOver carryOver,
                           MaximumAccumulatedBalance maximumAccumulatedBalance, OverDrawConfiguration overDrawConfiguration, OurDateTime firstAccrualDueDate) {
        super(id, ruleEffectiveDuration, accrualQuantity, carryOver, maximumAccumulatedBalance, overDrawConfiguration);
        this.dayOfWeek = dayOfWeek;
        this.firstAccrualDueDate = firstAccrualDueDate;
    }
}