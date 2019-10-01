package com.ourtimesheet.paidTimeOff.overDrawConfiguration;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Abdus Salam on 1/16/2018.
 */
public class OverDrawConfiguration {

    private double quantity;

    public OverDrawConfiguration(double quantity) {
        this.quantity = round(quantity,2);
    }

    public double getMaxOverDrawLimit() {
        return quantity;
    }

    public double round(double value, int precision) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(precision, RoundingMode.FLOOR);
        return bd.doubleValue();
    }
}