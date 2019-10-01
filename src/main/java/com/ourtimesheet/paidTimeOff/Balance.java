package com.ourtimesheet.paidTimeOff;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Abdus Salam on 12/24/2017.
 */
public class Balance {

    private double accruedBalance = 0.00;
    private double beginningBalance = 0.00;
    private double usedBalance = 0.00;
    private double currentBalance = 0.00;
    private double lostBalance = 0.00;
    private double earnedBalance = 0.00;

    public Balance() {
        accruedBalance = 0.00;
        beginningBalance = 0.00;
        usedBalance = 0.00;
        currentBalance = 0.00;
        lostBalance = 0.00;
        earnedBalance = 0.00;
    }

    public Balance(double accruedBalance, double beginningBalance, double usedBalance, double currentBalance, double lostBalance, double earnedBalance) {
        this.accruedBalance = accruedBalance;
        this.beginningBalance = beginningBalance;
        this.usedBalance = usedBalance;
        this.currentBalance = currentBalance;
        this.lostBalance = lostBalance;
        this.earnedBalance = earnedBalance;
    }

    public double getAccruedBalance() {
        return round(accruedBalance, 2);
    }

    public double getBeginningBalance() {
        return round(beginningBalance, 2);
    }

    public double getUsedBalance() {
        return round(usedBalance, 2);
    }

    public double getCurrentBalance() {
        return round(currentBalance, 2);
    }

    public void updateAccrualBalance(double accruedHours) {
        this.accruedBalance += accruedHours;
        this.currentBalance += accruedHours;
    }

    public void addUsedBalance(double usedHours) {
        this.usedBalance += usedHours;
        this.currentBalance -= usedHours;
    }

    public void removeUsedBalance(double usedHours) {
        if (this.usedBalance - usedHours >= 0) {
            this.usedBalance -= usedHours;
        } else {
            this.usedBalance = 0.00;
        }
        this.currentBalance += usedHours;
    }

    public double getLostBalance() {
        return lostBalance;
    }

    public double getEarnedBalance() {
        return round(earnedBalance, 2);
    }

    public void addEarnedBalance(double earnedBalance, double currentHoursToAdd) {
        this.earnedBalance += earnedBalance;
        this.currentBalance += currentHoursToAdd;
    }

    public void removeEarnedBalance(double earnedBalance) {
        this.earnedBalance -= earnedBalance;
    }

    public void addLostBalance(double lostBalance) {
        this.lostBalance += lostBalance;
    }

    public void updateLostAndCurrentBalance(double lostHoursToDeduct, double currentHoursToDeduct) {
        this.lostBalance -= lostHoursToDeduct;
        this.currentBalance -= currentHoursToDeduct;
    }

    private double round(double value, int precision) {
        return new BigDecimal(value).setScale(precision, RoundingMode.HALF_UP).doubleValue();
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

}