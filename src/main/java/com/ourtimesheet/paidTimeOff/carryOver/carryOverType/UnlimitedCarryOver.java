package com.ourtimesheet.paidTimeOff.carryOver.carryOverType;

/**
 * Created by Abdus Salam on 1/12/2018.
 */
public class UnlimitedCarryOver extends CarryOverType {

    public UnlimitedCarryOver() {
    }

    @Override
    public String getType() {
        return "unLimited";
    }

    @Override
    public double getQty() {
        return 10000;
    }
}