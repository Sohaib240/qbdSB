package com.ourtimesheet.paidTimeOff.accumulatedBalance;

/**
 * Created by umars on 1/12/2018.
 */
public class LimitedAccumulatedBalance extends MaximumAccumulatedBalance {

    private double hours;

    public LimitedAccumulatedBalance(double hours) {
        this.hours = hours;
    }

    public double getHours() {
        return hours;
    }

    @Override
    public String getType() {
        return "limited";
    }

    @Override
    public boolean limitedAccumulation() {
        return true;
    }

    @Override
    public double getHoursQty() {
        return hours;
    }
}
