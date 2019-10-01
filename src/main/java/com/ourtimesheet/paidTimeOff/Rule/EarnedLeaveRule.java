package com.ourtimesheet.paidTimeOff.Rule;

import com.ourtimesheet.paidTimeOff.RuleEffectiveDuration;
import com.ourtimesheet.paidTimeOff.accumulatedBalance.MaximumAccumulatedBalance;
import com.ourtimesheet.paidTimeOff.carryOver.CarryOver;
import com.ourtimesheet.paidTimeOff.overDrawConfiguration.OverDrawConfiguration;
import com.ourtimesheet.paytype.PayType;

import java.util.List;
import java.util.UUID;


public class EarnedLeaveRule extends LeaveRule {

    private final double thresholdHours;
    private final List<PayType> payTypes;

    public EarnedLeaveRule(UUID id, RuleEffectiveDuration ruleEffectiveDuration, double accrualQuantity, CarryOver carryOver,
                           MaximumAccumulatedBalance maximumAccumulatedBalance, OverDrawConfiguration overDrawConfiguration, double thresholdHours, List<PayType> payTypes) {
        super(id, ruleEffectiveDuration, accrualQuantity, carryOver, maximumAccumulatedBalance, overDrawConfiguration);
        this.thresholdHours = thresholdHours;
        this.payTypes = payTypes;
    }
}
