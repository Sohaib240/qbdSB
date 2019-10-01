package com.ourtimesheet.paidTimeOff;

/**
 * Created by Abdus Salam on 12/21/2017.
 */
public abstract class RuleEffectiveDuration {

    public abstract String getType();

    public abstract EffectiveDuration getDuration();
}