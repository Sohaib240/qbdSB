package com.ourtimesheet.paidTimeOff.accumulatedBalance;

/**
 * Created by umars on 1/12/2018.
 */
public class UnlimitedAccumulatedBalance extends MaximumAccumulatedBalance {

    public UnlimitedAccumulatedBalance() {
    }

    @Override
    public String getType() {
        return "unLimited";
    }

    @Override
    public boolean limitedAccumulation() {
        return false;
    }

    @Override
    public double getHoursQty() {
        return 0;
    }
}
