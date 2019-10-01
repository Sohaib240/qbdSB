package com.ourtimesheet.paidTimeOff.Rule;

import com.ourtimesheet.common.Entity;
import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.paidTimeOff.Balance;
import com.ourtimesheet.paidTimeOff.RuleEffectiveDuration;
import com.ourtimesheet.paidTimeOff.accumulatedBalance.MaximumAccumulatedBalance;
import com.ourtimesheet.paidTimeOff.carryOver.CarryOver;
import com.ourtimesheet.paidTimeOff.overDrawConfiguration.OverDrawConfiguration;
import org.springframework.data.annotation.Transient;

import java.util.TimeZone;
import java.util.UUID;

/**
 * Created by Abdus Salam on 12/20/2017.
 */
public abstract class LeaveRule extends Entity {

    private final RuleEffectiveDuration ruleEffectiveDuration;
    private final double accrualQuantity;
    private final CarryOver carryOver;
    private final MaximumAccumulatedBalance maximumAccumulatedBalance;
    private final OverDrawConfiguration overDrawConfiguration;
    @Transient
    protected OurDateTime employeeHireDate;

    public LeaveRule(UUID id, RuleEffectiveDuration ruleEffectiveDuration, double accrualQuantity,
                     CarryOver carryOver, MaximumAccumulatedBalance maximumAccumulatedBalance, OverDrawConfiguration overDrawConfiguration) {
        super(id);
        this.ruleEffectiveDuration = ruleEffectiveDuration;
        this.accrualQuantity = accrualQuantity;
        this.carryOver = carryOver;
        this.maximumAccumulatedBalance = maximumAccumulatedBalance;
        this.overDrawConfiguration = overDrawConfiguration;
    }
}