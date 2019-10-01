package com.ourtimesheet.paidTimeOff;

/**
 * Created by Abdus Salam on 12/21/2017.
 */
public class EffectiveDuration {

    private int quantity;
    private DurationMode durationMode;

    public EffectiveDuration(int quantity, DurationMode durationMode) {
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EffectiveDuration that = (EffectiveDuration) o;

        if (quantity != that.quantity) {
            return false;
        }
        return durationMode == that.durationMode;
    }

    @Override
    public int hashCode() {
        int result = quantity;
        result = 31 * result + (durationMode != null ? durationMode.hashCode() : 0);
        return result;
    }
}
