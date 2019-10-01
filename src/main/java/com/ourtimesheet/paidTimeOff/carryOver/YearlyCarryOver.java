package com.ourtimesheet.paidTimeOff.carryOver;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.paidTimeOff.carryOver.carryOverType.CarryOverType;
import com.ourtimesheet.timesheet.configuration.DayOfMonth;

import java.time.Month;
import java.util.TimeZone;
import java.util.UUID;

/**
 * Created by Abdus Salam on 1/12/2018.
 */
public class YearlyCarryOver extends CarryOver {

    private final Month month;
    private final DayOfMonth dayOfMonth;
    private final OurDateTime creationDate;

    public YearlyCarryOver(UUID id, Month month, DayOfMonth dayOfMonth, CarryOverType carryOverType, OurDateTime creationDate) {
        super(id, carryOverType);
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.creationDate = creationDate;
    }

    @Override
    public OurDateTime getCarryOverDate(TimeZone timeZone) {
        OurDateTime currentDateTime = OurDateTime.getCurrentDate(timeZone);
        return new OurDateTime(currentDateTime.getDateTime().getYear(), month.getValue(), dayOfMonth.getValue(), timeZone);
    }

    @Override
    public boolean isEffective(TimeZone timeZone) {
        OurDateTime ourDateTime = OurDateTime.getCurrentDate(timeZone);
        OurDateTime effectiveTime = new OurDateTime(ourDateTime.getDateTime().getYear(),month.getValue(),dayOfMonth.getValue(),timeZone);
        return ourDateTime.isAfterOrSame(effectiveTime) && (creationDate == null || effectiveTime.isAfterOrSame(creationDate));
    }

    public Month getMonth() {
        return month;
    }

    public DayOfMonth getDayOfMonth() {
        return dayOfMonth;
    }

    public OurDateTime getCreationDate() {
        return creationDate;
    }
}