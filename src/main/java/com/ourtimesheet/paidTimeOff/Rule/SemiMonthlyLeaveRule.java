package com.ourtimesheet.paidTimeOff.Rule;

import com.ourtimesheet.paidTimeOff.RuleEffectiveDuration;
import com.ourtimesheet.paidTimeOff.accumulatedBalance.MaximumAccumulatedBalance;
import com.ourtimesheet.paidTimeOff.carryOver.CarryOver;
import com.ourtimesheet.paidTimeOff.overDrawConfiguration.OverDrawConfiguration;
import com.ourtimesheet.timesheet.configuration.DayOfMonth;

import java.util.UUID;

/**
 * Created by Abdus Salam on 1/9/2018.
 */
public class SemiMonthlyLeaveRule extends LeaveRule {

    private final DayOfMonth dayOfMonthForFirstAccrual;
    private final DayOfMonth dayOfMonthForSecondAccrual;

    public SemiMonthlyLeaveRule(UUID id, RuleEffectiveDuration ruleEffectiveDuration, double accrualQuantity, DayOfMonth dayOfMonthForFirstAccrual,
                                DayOfMonth dayOfMonthForSecondAccrual, CarryOver carryOver, MaximumAccumulatedBalance maximumAccumulatedBalance, OverDrawConfiguration overDrawConfiguration) {
        super(id, ruleEffectiveDuration, accrualQuantity, carryOver, maximumAccumulatedBalance, overDrawConfiguration);
        this.dayOfMonthForFirstAccrual = dayOfMonthForFirstAccrual;
        this.dayOfMonthForSecondAccrual = dayOfMonthForSecondAccrual;
    }
}