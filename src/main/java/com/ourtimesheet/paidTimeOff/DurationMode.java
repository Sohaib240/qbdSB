package com.ourtimesheet.paidTimeOff;

/**
 * Created by Abdus Salam on 12/20/2017.
 */
public enum DurationMode {

    MONTHS("month(s)"),
    YEARS("year(s)"),
    DAYS("day(s)"),
    HIRE_DATE("Hire date");

    private String description;

    DurationMode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
