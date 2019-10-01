package com.ourtimesheet.paidTimeOff.Rule;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.paidTimeOff.RuleEffectiveDuration;
import com.ourtimesheet.paidTimeOff.accumulatedBalance.MaximumAccumulatedBalance;
import com.ourtimesheet.paidTimeOff.carryOver.CarryOver;
import com.ourtimesheet.paidTimeOff.overDrawConfiguration.OverDrawConfiguration;

import java.time.DayOfWeek;
import java.util.UUID;

/**
 * Created by umerr on 1/9/2018.
 */
public class BiWeeklyLeaveRule extends LeaveRule {

    private final DayOfWeek dayOfWeek;
    private final OurDateTime firstAccrualDueDate;

    public BiWeeklyLeaveRule(UUID id, RuleEffectiveDuration ruleEffectiveDuration, DayOfWeek dayOfWeek,
                             OurDateTime firstAccrualDueDate, double accrualQuantity, CarryOver carryOver,
                             MaximumAccumulatedBalance maximumAccumulatedBalance, OverDrawConfiguration overDrawConfiguration) {
        super(id, ruleEffectiveDuration, accrualQuantity, carryOver, maximumAccumulatedBalance, overDrawConfiguration);
        this.dayOfWeek = dayOfWeek;
        this.firstAccrualDueDate = firstAccrualDueDate;
    }

}