package com.ourtimesheet.paidTimeOff.accumulatedBalance;

/**
 * Created by umars on 1/12/2018.
 */
public abstract class MaximumAccumulatedBalance {

    public abstract String getType();

    public abstract boolean limitedAccumulation();

    public abstract double getHoursQty();
}
