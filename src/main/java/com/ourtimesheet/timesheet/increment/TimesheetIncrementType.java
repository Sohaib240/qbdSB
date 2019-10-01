package com.ourtimesheet.timesheet.increment;

/**
 * Created by Abdus salam on 5/5/2016.
 */
public enum TimesheetIncrementType {

    SIX_MINUTES_INCREMENT,
    NO_INCREMENT,
    FIFTEEN_MINUTES_INCREMENT;

    public HoursIncrement getHoursIncrement() {
        switch (this) {
            case FIFTEEN_MINUTES_INCREMENT:
                return new FifteenMinuteIncrement();
            case NO_INCREMENT:
                return new NoIncrement();
            case SIX_MINUTES_INCREMENT:
                return new SixMinutesIncrement();
            default:
                return new NoIncrement();
        }
    }
}