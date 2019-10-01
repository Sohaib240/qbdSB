package com.ourtimesheet.paidTimeOff.carryOver.carryOverType;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Abdus Salam on 1/12/2018.
 */
public class LimitedCarryOver extends CarryOverType {

    private double carryOverQuantity;

    public LimitedCarryOver(double carryOverQuantity) {
        this.carryOverQuantity = round(carryOverQuantity,2);
    }

    @Override
    public String getType() {
        return "limited";
    }

    @Override
    public double getQty() {
        return carryOverQuantity;
    }

    public double round(double value, int precision) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(precision, RoundingMode.FLOOR);
        return bd.doubleValue();
    }
}
