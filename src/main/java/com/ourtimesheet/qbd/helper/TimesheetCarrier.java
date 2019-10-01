package com.ourtimesheet.qbd.helper;

import com.ourtimesheet.common.Entity;
import com.ourtimesheet.timesheet.hoursWorked.HoursWorked;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by zohaib on 06/09/2016.
 */
public class TimesheetCarrier {
    private final UUID id;
    private final String notes;
    private final List<HoursWorked> hoursWorked;

    public TimesheetCarrier(UUID id, String notes, List<HoursWorked> hoursWorked) {
        this.id = id;
        this.notes = notes;
        this.hoursWorked = hoursWorked;
    }

    public UUID getId() {
        return id;
    }

    public String getNotes() {
        return notes;
    }

    public List<HoursWorked> getHoursWorked() {
        return hoursWorked;
    }

    public List<UUID> getHoursWorkedIds() {
        return hoursWorked
                .stream()
                .map(Entity::getId)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimesheetCarrier that = (TimesheetCarrier) o;

        if (!id.equals(that.id)) return false;
        if (!notes.equals(that.notes)) return false;
        return hoursWorked.equals(that.hoursWorked);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + notes.hashCode();
        result = 31 * result + hoursWorked.hashCode();
        return result;
    }
}
