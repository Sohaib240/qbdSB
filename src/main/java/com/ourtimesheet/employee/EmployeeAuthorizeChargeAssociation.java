package com.ourtimesheet.employee;

import com.ourtimesheet.association.AuthorizeChargeAssociation;
import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.paytype.PayType;
import com.ourtimesheet.timesheet.chargeCode.ChargeCode;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdus Salam on 11/8/2017.
 */
public class EmployeeAuthorizeChargeAssociation {
    @DBRef
    private final AuthorizeChargeAssociation authorizeChargeAssociation;
    private OurDateTime startDate;
    private OurDateTime endDate;
    private Boolean billable;

    public EmployeeAuthorizeChargeAssociation(OurDateTime startDate, OurDateTime endDate, AuthorizeChargeAssociation authorizeChargeAssociation, Boolean billable) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.authorizeChargeAssociation = authorizeChargeAssociation;
        this.billable = billable;
    }

    public OurDateTime getStartDate() {
        return startDate;
    }

    public OurDateTime getEndDate() {
        return endDate;
    }

    public void updateStartDate(OurDateTime startDate) {
        this.startDate = startDate;
    }

    public void updateEndDate(OurDateTime endDate) {
        this.endDate = endDate;
    }

    public List<ChargeCode> getChargeCodes() {
        return this.authorizeChargeAssociation != null ? this.authorizeChargeAssociation.getChargeCodes() : new ArrayList<>();
    }

    public PayType getPayType() {
        return authorizeChargeAssociation != null ? authorizeChargeAssociation.getPayType() : null;
    }

    public boolean isActive() {
        return !hasInactiveChargeCode(getChargeCodes()) && isPayTypeActive(getPayType());
    }

    public boolean isEffective(OurDateTime startDate, OurDateTime endDate) {
        if (authorizeChargeAssociation.isEffective(startDate)) {
            if (this.startDate == null && this.endDate == null) {
                return true;
            } else {
                return isStartDateEffective(startDate, endDate) && isEndDateEffective(startDate, endDate);
            }
        }
        return false;
    }

    public void updateBillable(Boolean billable) {
        if (billable != null)
            this.billable = billable;
    }

    public Boolean isBillable() {
        return this.billable;
    }

    public String getDescription() {
        return authorizeChargeAssociation.getDescription();
    }

    public boolean isLeave() {
        return getPayType() != null && getPayType().isLeave();
    }

    private boolean isEndDateEffective(OurDateTime startDate, OurDateTime endDate) {
        return this.endDate == null || (this.endDate.isAfterOrSame(startDate) || (this.endDate.isAfterOrSame(endDate) && this.endDate.isBeforeOrSame(endDate)));
    }

    private boolean isStartDateEffective(OurDateTime startDate, OurDateTime endDate) {
        return this.startDate.isBeforeOrSame(startDate) || this.startDate.isBeforeOrSame(endDate);
    }

    private boolean hasInactiveChargeCode(List<ChargeCode> chargeCodes) {
        return chargeCodes.stream().anyMatch(chargeCode -> !chargeCode.isActive());
    }

    private boolean isPayTypeActive(PayType payType) {
        return payType == null || payType.isActive();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmployeeAuthorizeChargeAssociation that = (EmployeeAuthorizeChargeAssociation) o;
        return authorizeChargeAssociation != null ? authorizeChargeAssociation.equals(that.authorizeChargeAssociation) : that.authorizeChargeAssociation == null;
    }

    @Override
    public int hashCode() {
        return authorizeChargeAssociation != null ? authorizeChargeAssociation.hashCode() : 0;
    }
}