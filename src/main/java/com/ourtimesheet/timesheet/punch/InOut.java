package com.ourtimesheet.timesheet.punch;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;

/**
 * Created by click chain on 4/26/2016.
 */
public class InOut {

    private final Punch inPunch;

    private final Punch outPunch;

    public InOut(Punch inPunch, Punch outPunch) {
        validatePunches(inPunch, outPunch);
        this.inPunch = inPunch;
        this.outPunch = outPunch;
    }

    public Punch getOutPunch() {
        return outPunch;
    }

    public Punch getInPunch() {
        return inPunch;
    }

    public String getPunchInTime() {
        return getTimeFromPunchTimeStamp(inPunch);
    }

    public String getPunchOutTime() {
        return getTimeFromPunchTimeStamp(outPunch);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InOut inOut = (InOut) o;

        return new EqualsBuilder()
                .append(inPunch, inOut.inPunch)
                .append(outPunch, inOut.outPunch)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(inPunch)
                .append(outPunch)
                .toHashCode();
    }

    private String getTimeFromPunchTimeStamp(Punch punch) {
        return punch == null ? "" : new SimpleDateFormat("hh:mm a").format(punch.getPunchDate());
    }

    private void validatePunches(Punch inPunch, Punch outPunch) {
        Assert.isTrue(inPunch != null || outPunch != null);
        if (inPunch != null && outPunch != null) {
            Assert.isTrue(inPunch.getPunchDate().isBeforeOrSame(outPunch.getPunchDate()));
            Assert.isTrue(inPunch.getPunchDate().isSameDay(outPunch.getPunchDate()));
        }
    }
}
