package com.ourtimesheet.paidTimeOff.Rule;

import com.ourtimesheet.paidTimeOff.RuleEffectiveDuration;
import com.ourtimesheet.paidTimeOff.accumulatedBalance.MaximumAccumulatedBalance;
import com.ourtimesheet.paidTimeOff.carryOver.CarryOver;
import com.ourtimesheet.paidTimeOff.overDrawConfiguration.OverDrawConfiguration;

import java.util.UUID;

public class YearlyHireDateLeaveRule extends LeaveRule {


    public YearlyHireDateLeaveRule(UUID id, RuleEffectiveDuration ruleEffectiveDuration, double accrualQuantity,
                                   CarryOver carryOver, MaximumAccumulatedBalance maximumAccumulatedBalance,
                                   OverDrawConfiguration overDrawConfiguration) {
        super(id, ruleEffectiveDuration, accrualQuantity, carryOver, maximumAccumulatedBalance, overDrawConfiguration);
    }
}
