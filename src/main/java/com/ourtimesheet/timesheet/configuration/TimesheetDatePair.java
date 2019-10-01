package com.ourtimesheet.timesheet.configuration;

import com.ourtimesheet.datetime.OurDateTime;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.PersistenceConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zohaib on 03/05/2016.
 */
public class TimesheetDatePair {

    private final OurDateTime startDate;

    private final OurDateTime endDate;

    @PersistenceConstructor
    public TimesheetDatePair(OurDateTime startDate, OurDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public TimesheetDatePair(OurDateTime startDate, OurDateTime endDate, String timeOfDay) {
        int hourOfDay = extractHourOfDay(timeOfDay);
        int minuteOfHour = extractMinuteOfHour(timeOfDay);

        this.startDate = startDate.withHourOfDay(hourOfDay).withMinuteOfHour(minuteOfHour);
        this.endDate = endDate.plusDays(1).withHourOfDay(hourOfDay).withMinuteOfHour(minuteOfHour).minusSeconds(1);
    }

    public OurDateTime getStartDate() {
        return startDate;
    }

    public OurDateTime getEndDate() {
        return endDate;
    }

    private int extractHourOfDay(String timeOfDay) {
        int hourOfDay = Integer.parseInt(getTimeOfDayInTwentyFourHourFormat(timeOfDay));
        return hourOfDay;
    }

    private int extractMinuteOfHour(String timeOfDay) {
        return Integer.parseInt(StringUtils.substringAfter(timeOfDay, ":").substring(0, 2));
    }

    private String getTimeOfDayInTwentyFourHourFormat(String timeOfDay) {
        Date date = null;
        try {
            date = new SimpleDateFormat("hh:mm a").parse(timeOfDay);
        } catch (ParseException e) {
            throw new RuntimeException("Error while creating 24 hour time format");
        }
        return StringUtils.substringBefore(new SimpleDateFormat("HH:mm").format(date), ":");
    }
}
