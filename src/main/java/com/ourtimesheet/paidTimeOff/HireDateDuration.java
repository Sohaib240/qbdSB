package com.ourtimesheet.paidTimeOff;

/**
 * Created by Abdus Salam on 12/21/2017.
 */
public class HireDateDuration extends RuleEffectiveDuration {

    public HireDateDuration() {}

    @Override
    public String getType() {
        return "hireDate";
    }

    @Override
    public EffectiveDuration getDuration() {
        return new EffectiveDuration(0, DurationMode.HIRE_DATE);
    }
}