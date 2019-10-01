package com.ourtimesheet.paidTimeOff.Rule;

import com.ourtimesheet.paidTimeOff.RuleEffectiveDuration;
import com.ourtimesheet.paidTimeOff.accumulatedBalance.MaximumAccumulatedBalance;
import com.ourtimesheet.paidTimeOff.carryOver.CarryOver;
import com.ourtimesheet.paidTimeOff.overDrawConfiguration.OverDrawConfiguration;
import com.ourtimesheet.paytype.PayType;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.util.Assert;

import java.util.List;
import java.util.UUID;

/**
 * Created by Abdus Salam on 3/26/2018.
 */
public class CompTimeLeaveRule extends EarnedLeaveRule {

    private final double minimumThresholdHours;
    private final double maximumThresholdHours;

    @PersistenceConstructor
    public CompTimeLeaveRule(UUID id, RuleEffectiveDuration ruleEffectiveDuration, double accrualQuantity,
                             CarryOver carryOver, MaximumAccumulatedBalance maximumAccumulatedBalance,
                             OverDrawConfiguration overDrawConfiguration, double thresholdHours, List<PayType> payTypes,
                             double minimumThresholdHours, double maximumThresholdHours) {
        super(id, ruleEffectiveDuration, accrualQuantity, carryOver, maximumAccumulatedBalance, overDrawConfiguration, thresholdHours, payTypes);
        Assert.isTrue(minimumThresholdHours >= 0D, "Potential threshold cannot be empty");
        this.minimumThresholdHours = minimumThresholdHours;
        Assert.isTrue(maximumThresholdHours != 0D, "Maximum threshold cannot be empty");
        this.maximumThresholdHours = maximumThresholdHours;
    }
}
