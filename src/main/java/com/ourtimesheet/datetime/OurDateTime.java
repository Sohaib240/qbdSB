package com.ourtimesheet.datetime;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.springframework.data.annotation.PersistenceConstructor;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import static java.time.temporal.TemporalAdjusters.*;

/**
 * Created by hassan on 7/7/16.
 */
public class OurDateTime implements Comparable<OurDateTime> {

    private final DateTime dateTime;

    private final TimeZone effectiveTimezone;

    @PersistenceConstructor
    public OurDateTime(DateTime dateTime, TimeZone effectiveTimezone) {
        this.effectiveTimezone = effectiveTimezone;
        DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(effectiveTimezone);
        this.dateTime = dateTime.withZone(dateTimeZone);
    }

    public OurDateTime(TimeZone effectiveTimezone) {
        this.effectiveTimezone = effectiveTimezone;
        DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(effectiveTimezone);
        this.dateTime = DateTime.now(dateTimeZone);
    }

    public OurDateTime(int year, int month, int day, int hourOfDay, int minuteOfHour, TimeZone effectiveTimezone) {
        this.effectiveTimezone = effectiveTimezone;
        DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(effectiveTimezone);
        DateTime dateTime = new DateTime(year, month, day, hourOfDay, minuteOfHour, 0, 0, dateTimeZone);
        this.dateTime = dateTime;
    }

    public OurDateTime(int year, int month, int day, int hourOfDay, int minuteOfHour, int secondOfMinute, int millisOfSecond, TimeZone effectiveTimezone) {
        this.effectiveTimezone = effectiveTimezone;
        DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(effectiveTimezone);
        DateTime dateTime = new DateTime(year, month, day, hourOfDay, minuteOfHour, secondOfMinute, millisOfSecond, dateTimeZone);
        this.dateTime = dateTime;
    }

    public OurDateTime(int year, int month, int day, TimeZone effectiveTimezone) {
        this.effectiveTimezone = effectiveTimezone;
        DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(effectiveTimezone);
        DateTime dateTime = new DateTime(year, month, day, 0, 0, 0, 0, dateTimeZone);
        this.dateTime = dateTime;
    }

    public static OurDateTime getCurrentDate() {
        return new OurDateTime(DateTime.now(), TimeZone.getDefault());
    }

    public static OurDateTime getCurrentDate(TimeZone timeZone) {
        return new OurDateTime(DateTime.now(), timeZone);
    }

    public DateTime toDateTime() {
        return dateTime;
    }

    public OurDateTime withHourOfDay(int hourOfDay) {
        return new OurDateTime(dateTime.withHourOfDay(hourOfDay), effectiveTimezone);
    }

    public OurDateTime withMinuteOfHour(int minuteOfHour) {
        return new OurDateTime(dateTime.withMinuteOfHour(minuteOfHour), effectiveTimezone);
    }

    public OurDateTime withSecondOfMinute(int minuteOfHour) {
        return new OurDateTime(dateTime.withSecondOfMinute(minuteOfHour), effectiveTimezone);
    }

    public OurDateTime withDayOfMonth(int dayOfMonth) {
        return new OurDateTime(dateTime.withDayOfMonth(dayOfMonth), effectiveTimezone);
    }

    public OurDateTime lastDayOfMonth() {
        return new OurDateTime(dateTime.dayOfMonth().withMaximumValue(), effectiveTimezone);
    }

    public OurDateTime previousOrSameDayOfWeek(int dayOfWeek) {
        DateTime dateTime = DateTime.parse(getLocalDateTime().with(previousOrSame(DayOfWeek.of(dayOfWeek))).toString());
        return new OurDateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), effectiveTimezone);
    }

    public OurDateTime previousDayOfWeek(int dayOfWeek) {
        DateTime dateTime = DateTime.parse(getLocalDateTime().with(previous(DayOfWeek.of(dayOfWeek))).toString());
        return new OurDateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), effectiveTimezone);
    }

    public OurDateTime nextOrSameDayOfWeek(int dayOfWeek) {
        DateTime dateTime = DateTime.parse(getLocalDateTime().with(nextOrSame(DayOfWeek.of(dayOfWeek))).toString());
        return new OurDateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), effectiveTimezone);
    }

    public OurDateTime nextDayOfWeek(int dayOfWeek) {
        DateTime dateTime = DateTime.parse(getLocalDateTime().with(next(DayOfWeek.of(dayOfWeek))).toString());
        return new OurDateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), effectiveTimezone);
    }

    public OurDateTime dateOnly() {
        return new OurDateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), effectiveTimezone);
    }

    private LocalDateTime getLocalDateTime() {
        return LocalDateTime.ofInstant(toInstant(), dateTime.getZone().toTimeZone().toZoneId());
    }

    public int daysBetween(OurDateTime ourDateTime) {
        return Days.daysBetween(this.dateTime, ourDateTime.dateTime).getDays();
    }

    public long getNumberOfBusinessDays(OurDateTime ourDateTime) {
        List<OurDateTime> dates = new ArrayList<>();
        OurDateTime value = this;
        while (value.isBeforeOrSame(ourDateTime)) {
            dates.add(value);
            value = value.plusDays(1);
        }

        return dates.stream().filter(date -> date.getDateTime().getDayOfWeek() <= 5).count();
    }

    public boolean isDateRangeOverLapping(OurDateTime endPoint, OurDateTime rangeToChaeckStartDate, OurDateTime rangeToChaeckEndDate) {
        return this.isBeforeOrSame(rangeToChaeckEndDate) && rangeToChaeckStartDate.isBeforeOrSame(endPoint);
    }

    private Instant toInstant() {
        return dateTime.toDate().toInstant();
    }

    public long getTime() {
        return dateTime.toDate().getTime();
    }

    public boolean isBeforeOrSame(OurDateTime ourDateTime) {
        return dateTime.isBefore(ourDateTime.dateTime) || dateTime.isEqual(ourDateTime.dateTime);
    }

    public boolean isBefore(OurDateTime ourDateTime) {
        return dateTime.isBefore(ourDateTime.dateTime);
    }

    public boolean isAfterOrSame(OurDateTime ourDateTime) {
        return dateTime.isAfter(ourDateTime.dateTime) || dateTime.isEqual(ourDateTime.dateTime);
    }

    public boolean isAfter(OurDateTime ourDateTime) {
        return dateTime.isAfter(ourDateTime.dateTime);
    }

    public boolean isSameDay(OurDateTime ourDateTime) {
        return dateTime.dayOfMonth().roundFloorCopy().isEqual(ourDateTime.dateTime.dayOfMonth().roundFloorCopy());
    }

    public boolean isAfterOrSameTimeOnly(OurDateTime ourDateTime) {
        return dateTime.toLocalTime().isAfter(ourDateTime.dateTime.toLocalTime()) || dateTime.toLocalTime().equals(ourDateTime.dateTime.toLocalTime());
    }

    public boolean isBeforeOrSameTimeOnly(OurDateTime ourDateTime) {
        return dateTime.toLocalTime().isBefore(ourDateTime.dateTime.toLocalTime()) || dateTime.toLocalTime().equals(ourDateTime.dateTime.toLocalTime());
    }

    public String format(String dateFormat) {
        return dateTime.toString(dateFormat);
    }

    public int getDayOfMonth() {
        return dateTime.getDayOfMonth();
    }

    public String dayOfWeek() {
        return dateTime.dayOfWeek().getAsString();
    }

    public String dateOnlyStandardFormat() {
        return format("MM/dd/yyyy");
    }

    public String timeOnlyStandardFormat() {
        return format("h:mm a");
    }

    public String dateTimeStandardFormat() {
        return format("MM/dd/yyyy hh:mm a");
    }

    @Override
    public String toString() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Date", dateTime.toString("yyyy-MM-dd"));
        jsonObject.addProperty("Day", dateTime.toString("EEEE"));
        jsonObject.addProperty("Time", dateTime.toString("HH:mm:ss.SSS"));
        jsonObject.addProperty("Timezone", dateTime.toString("z"));
        return jsonObject.toString();
    }

    @Override
    public int compareTo(OurDateTime o) {
        return dateTime.compareTo(o.dateTime);
    }

    public OurDateTime plusMinutes(int minutes) {
        return new OurDateTime(dateTime.plusMinutes(minutes), effectiveTimezone);
    }

    public OurDateTime plusSeconds(int seconds) {
        return new OurDateTime(dateTime.plusSeconds(seconds), effectiveTimezone);
    }

    public OurDateTime plusDays(int days) {
        return new OurDateTime(dateTime.plusDays(days), effectiveTimezone);
    }

    public OurDateTime plusWeeks(int weeks) {
        return new OurDateTime(dateTime.plusWeeks(weeks), effectiveTimezone);
    }

    public OurDateTime plusMonths(int months) {
        return new OurDateTime(dateTime.plusMonths(months), effectiveTimezone);
    }

    public OurDateTime plusYears(int years) {
        return new OurDateTime(dateTime.plusYears(years), effectiveTimezone);
    }

    public OurDateTime minusSeconds(int seconds) {
        return new OurDateTime(dateTime.minusSeconds(seconds), effectiveTimezone);
    }

    public OurDateTime minusDays(int days) {
        return new OurDateTime(dateTime.minusDays(days), effectiveTimezone);
    }

    public OurDateTime minusMonths(int months) {
        return new OurDateTime(dateTime.minusMonths(months), effectiveTimezone);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OurDateTime that = (OurDateTime) o;

        return new EqualsBuilder()
                .append(dateTime, that.dateTime)
                .append(effectiveTimezone, that.effectiveTimezone)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(dateTime)
                .append(effectiveTimezone)
                .toHashCode();
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public TimeZone getEffectiveTimezone() {
        return effectiveTimezone;
    }

    public int getMonth() {
        return dateTime.getMonthOfYear();
    }

    public int getYear() {
        return dateTime.getYear();
    }
}
