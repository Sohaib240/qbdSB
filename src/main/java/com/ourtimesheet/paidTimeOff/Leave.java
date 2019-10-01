package com.ourtimesheet.paidTimeOff;

import com.ourtimesheet.common.Entity;
import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.paytype.PayType;
import com.ourtimesheet.timesheet.chargeCode.ChargeCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Jazib on 09/11/2016.
 */

@Document(collection = "leave")
public class Leave extends Entity implements ChargeCode {
    private final String name;
    private final String description;
    private final OurDateTime startDate;
    private OurDateTime endDate;
    @DBRef
    private PayType payType;

    @DBRef
    private List<LeavePolicy> leavePolicies;

    @PersistenceConstructor
    private Leave(UUID id, String name, String description, OurDateTime startDate, OurDateTime endDate, PayType payType, List<LeavePolicy> leavePolicies) {
        super(id);
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.payType = payType;
        this.leavePolicies = leavePolicies;
    }


    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public boolean isEffective(OurDateTime ourDateTime) {
        if (payType == null || payType.getEndDate() == null)
            return true;
        else if (!payType.isActive())
            return false;
        else
            return payType.getEndDate().isAfterOrSame(ourDateTime);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getOrder() {
        return 6;
    }

    @Override
    public String getChargeCodeName() {
        return "Leave";
    }

    @Override
    public Set<ChargeCode> children() {
        return Collections.emptySet();
    }

    @Override
    public boolean isRequired() {
        return true;
    }

    public String getDescription() {
        return description;
    }

    public OurDateTime getStartDate() {
        return startDate;
    }

    public OurDateTime getEndDate() {
        return endDate;
    }

    @Override
    public String getCollectionName() {
        return "leave";
    }

    public PayType getPayType() {
        return payType;
    }

    public void addLeavePolicy(LeavePolicy leavePolicy) {
        if (this.leavePolicies == null) {
            this.leavePolicies = new ArrayList<>();
        }
        this.leavePolicies.add(leavePolicy);
    }

    public List<LeavePolicy> getLeavePolicies() {
        return leavePolicies == null ? new ArrayList<>() : leavePolicies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Leave leave = (Leave) o;

        return name.equals(leave.getName());

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String getHierarchicalName() {
        return name;
    }

    public void updateEndDate(OurDateTime endDate) {
        this.endDate = endDate;
    }

    public Set<OurDateTime> getEffectiveLeaveDays(OurDateTime startDate, OurDateTime endDate, OurDateTime timesheetEndDate) {
        Set<OurDateTime> employeeLeaveDates = new HashSet<>();
        if (endDate != null) {
            employeeLeaveDates.addAll(getDatesBetweenDates(startDate, endDate));
        } else {
            employeeLeaveDates.addAll(getDatesBetweenDates(startDate, timesheetEndDate.plusDays(30)));
        }
        Set<OurDateTime> leaveDates = new HashSet<>();
        if (this.endDate != null) {
            leaveDates.addAll(getDatesBetweenDates(this.startDate, this.endDate));
        } else {
            leaveDates.addAll(getDatesBetweenDates(this.startDate, timesheetEndDate.plusDays(30)));
        }
        return employeeLeaveDates.stream().filter(leaveDates::contains).collect(Collectors.toSet());
    }

    @Override
    public boolean isBillable() {
        return false;
    }

    private Set<OurDateTime> getDatesBetweenDates(OurDateTime startDate, OurDateTime endDate) {
        Set<OurDateTime> datesBetween = new HashSet<>();
        int count = 0;
        datesBetween.add(startDate);
        while (!startDate.plusDays(count).dateOnly().format("YYYY-MM-dd").equals(endDate.dateOnly().format("YYYY-MM-dd"))) {
            count++;
            datesBetween.add(startDate.plusDays(count));
        }
        return datesBetween;
    }

    public void removeLeavePolicy(LeavePolicy leavePolicy) {
        this.leavePolicies.remove(leavePolicy);
    }

    public static class Builder {
        private UUID id;
        private String name;
        private String description;
        private OurDateTime startDate;
        private OurDateTime endDate;
        private PayType paytype;
        private List<LeavePolicy> leavePolicies;

        public Builder(String name, OurDateTime startDate) {
            Assert.isTrue(StringUtils.isNoneBlank(name), "Name of paid time off can not be empty");
            this.name = name;
            Assert.notNull(startDate, "Start date for paid time off can not be empty");
            this.startDate = startDate;
        }

        public Builder withEndDate(OurDateTime endDate) {
            if (endDate != null) {
                Assert.isTrue(this.startDate.isBeforeOrSame(endDate), "Start date for leave can not be after end date");
                this.endDate = endDate;
            }
            return this;
        }

        public Builder withId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withPayType(PayType payType) {
            if (payType != null) {
                this.paytype = payType;
            }
            return this;
        }

        public Builder withLeavePolicies(List<LeavePolicy> leavePolicies) {
            this.leavePolicies = leavePolicies;
            return this;
        }

        public Leave buildPaidTimeOff() {
            return new Leave(id, name, description, startDate, endDate, paytype, leavePolicies);
        }
    }
}
