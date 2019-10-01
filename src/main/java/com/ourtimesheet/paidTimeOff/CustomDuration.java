package com.ourtimesheet.paidTimeOff;


/**
 * Created by Abdus Salam on 12/21/2017.
 */
public class CustomDuration extends RuleEffectiveDuration {

    private int quantity;
    private DurationMode durationMode;

    public CustomDuration() {
    }

    public CustomDuration(int quantity, DurationMode durationMode) {
        this.quantity = quantity;
        this.durationMode = durationMode;
    }

    public int getQuantity() {
        return quantity;
    }

    public DurationMode getDurationMode() {
        return durationMode;
    }

    @Override
    public String getType() {
        return "custom";
    }

    @Override
    public EffectiveDuration getDuration() {
        return new EffectiveDuration(quantity, durationMode);
    }
}