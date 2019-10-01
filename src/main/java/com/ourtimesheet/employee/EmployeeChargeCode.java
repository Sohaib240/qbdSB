package com.ourtimesheet.employee;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.timesheet.chargeCode.ChargeCode;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.Assert;

/**
 * Created by Abdus Salam on 1/2/2017.
 */
public class EmployeeChargeCode {
    @DBRef
    private ChargeCode chargeCode;
    private OurDateTime startDate;
    private OurDateTime endDate;

    public EmployeeChargeCode(ChargeCode chargeCode, OurDateTime startDate, OurDateTime endDate) {
        if (chargeCode != null) {
            this.chargeCode = chargeCode;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

    public OurDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OurDateTime startDate) {
        Assert.isTrue(startDate != null, "Start date cannot be null");
        this.startDate = startDate;
    }

    public OurDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(OurDateTime endDate) {
        this.endDate = endDate;
    }

    public ChargeCode getChargeCode() {
        return chargeCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeChargeCode that = (EmployeeChargeCode) o;

        return chargeCode != null && chargeCode.equals(that.chargeCode);
    }

    @Override
    public int hashCode() {
        return chargeCode != null ? chargeCode.hashCode() : 0;
    }

    public String getId() {
        return chargeCode.getId().toString();
    }

    public String getName() {
        return chargeCode.getName();
    }

    public String getChargeCodeName() {
        return chargeCode.getChargeCodeName();
    }

    public String getHierarchicalName() {
        return chargeCode.getHierarchicalName();
    }

    public boolean isActive() {
        return chargeCode != null && chargeCode.isActive();
    }

    public boolean isEffective(OurDateTime startDate, OurDateTime endDate) {
        if (this.startDate == null && this.endDate == null) {
            return true;
        } else {
            return isStartDateEffective(startDate, endDate) && isEndDateEffective(startDate, endDate);
        }
    }

    private boolean isEndDateEffective(OurDateTime startDate, OurDateTime endDate) {
        return this.endDate == null || (this.endDate.isAfterOrSame(startDate) || (this.endDate.isAfterOrSame(endDate) && this.endDate.isBeforeOrSame(endDate)));
    }

    private boolean isStartDateEffective(OurDateTime startDate, OurDateTime endDate) {
        return this.startDate.isBeforeOrSame(startDate) || this.startDate.isBeforeOrSame(endDate);
    }

    public boolean hasChildren() {
        return chargeCode.children() != null && !chargeCode.children().isEmpty();
    }
}