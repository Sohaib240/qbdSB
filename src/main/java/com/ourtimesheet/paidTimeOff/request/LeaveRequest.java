package com.ourtimesheet.paidTimeOff.request;

import com.ourtimesheet.common.Entity;
import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.timesheet.signature.Signature;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Transient;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Jazib on 5/15/2018.
 */
public abstract class LeaveRequest extends Entity {
    protected final String leaveName;
    protected final OurDateTime startDate;
    protected final OurDateTime endDate;
    protected final OurDateTime submissionDate;
    protected final double hoursPerDay;
    protected final boolean includeWeekend;
    protected final String comments;
    protected List<Signature> signatures;

    @Transient
    private Employee employee;

    protected LeaveRequest(UUID id, String leaveName, OurDateTime startDate, OurDateTime endDate,
                           double hoursPerDay, boolean includeWeekend, String comments,
                           OurDateTime submissionDate, List<Signature> signatures) {
        super(id);
        Assert.isTrue(StringUtils.isNotBlank(leaveName), "Leave Name cannot be empty");
        this.leaveName = leaveName;
        Assert.isTrue(startDate != null, "Start date cannot be empty");
        this.startDate = startDate;
        Assert.isTrue(endDate != null, "End Date cannot be empty");
        this.endDate = endDate;
        Assert.isTrue(hoursPerDay > 0, "Hours per day cannot be empty");
        this.hoursPerDay = hoursPerDay;
        this.includeWeekend = includeWeekend;
        this.comments = comments;
        this.submissionDate = submissionDate;
        this.signatures = signatures;
    }

    public abstract LeaveRequest approve(Employee employee, OurDateTime dateTime);

    public abstract LeaveRequest reject(String comments);


    public abstract LeaveRequest cancel();

    public void loadEmployee(Employee employee) {
        this.employee = employee;
    }



    public String getLeaveName() {
        return leaveName;
    }

    public OurDateTime getStartDate() {
        return startDate;
    }

    public OurDateTime getEndDate() {
        return endDate;
    }

    public double getHoursPerDay() {
        return hoursPerDay;
    }

    public boolean isIncludeWeekend() {
        return includeWeekend;
    }

    public String getRequestComments() {
        return comments;
    }

    public OurDateTime getSubmissionDate() {
        return submissionDate;
    }

    public double getRequestedHours() {
        if (this.includeWeekend) {
            return (this.startDate.daysBetween(this.endDate) + 1) * this.hoursPerDay;
        } else {
            return this.startDate.getNumberOfBusinessDays(this.endDate) * this.hoursPerDay;
        }
    }

    public Employee getEmployee() {
        return employee;
    }

    public List<Signature> getSignatures() {
        return signatures;
    }

    public void unsign() {
        this.signatures = new ArrayList<>();
    }

    protected void sign(Signature signature) {
        if (this.signatures == null) {
            this.signatures = new ArrayList<>();
        }
        this.signatures.add(signature);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        LeaveRequest that = (LeaveRequest) o;

        return (StringUtils.equalsIgnoreCase(getLeaveName(), that.getLeaveName())
                && getStartDate().isDateRangeOverLapping(getEndDate(), that.getStartDate(), that.getEndDate()))
                && !getId().equals(that.getId());

    }

    @Override
    public int hashCode() {
        return 0;
    }
}