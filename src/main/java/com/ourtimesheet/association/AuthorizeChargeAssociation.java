package com.ourtimesheet.association;

import com.ourtimesheet.common.Entity;
import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.paytype.PayType;
import com.ourtimesheet.timesheet.chargeCode.ChargeCode;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Abdus Salam on 11/2/2017.
 */
@Document(collection = "authorizeChargeAssociation")
public class AuthorizeChargeAssociation extends Entity {
    @DBRef
    private final List<ChargeCode> chargeCodes;
    @DBRef
    private final PayType payType;
    private boolean active;
    private boolean billable;

    @PersistenceConstructor
    private AuthorizeChargeAssociation(UUID id, List<ChargeCode> chargeCodes, PayType payType, boolean active) {
        super(id);
        this.chargeCodes = chargeCodes;
        this.payType = payType;
        this.active = active;
    }

    public List<ChargeCode> getChargeCodes() {
        if (this.chargeCodes == null) {
            return new ArrayList<>();
        }
        this.chargeCodes.sort(Comparator.comparingInt(ChargeCode::getOrder));
        return this.chargeCodes.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    public boolean isBillable() {
        return billable;
    }

    public PayType getPayType() {
        return payType;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isEffective(OurDateTime dateTime) {
        return payType != null ? getChargeCodes().stream().filter(chargeCode -> !chargeCode.isEffective(dateTime)).count() == 0 && payType.isEffective(dateTime) : getChargeCodes().stream().filter(chargeCode -> !chargeCode.isEffective(dateTime)).count() == 0;
    }

    public OurDateTime getEndDate(TimeZone timeZone, OurDateTime associationEndDate) {
        OurDateTime endDate = null;
        OurDateTime currentDateTime = new OurDateTime(new DateTime(), timeZone);
        if (payType != null) {
            Optional<ChargeCode> optionalChargeCode = getChargeCodes().stream().filter(chargeCode -> !chargeCode.isEffective(currentDateTime)).sorted((o1, o2) -> o1.getEndDate().compareTo(o2.getEndDate())).findFirst();
            if (optionalChargeCode.isPresent()) {
                endDate = payType.isEffective(currentDateTime) ? optionalChargeCode.get().getEndDate() : payType.getEndDate().isAfterOrSame(optionalChargeCode.get().getEndDate()) ? optionalChargeCode.get().getEndDate() : payType.getEndDate();
            } else if (!payType.isEffective(currentDateTime)) {
                endDate = payType.getEndDate();
            }
        } else {
            Optional<ChargeCode> optionalChargeCode = getChargeCodes().stream().filter(chargeCode -> !chargeCode.isEffective(currentDateTime)).sorted((o1, o2) -> o1.getEndDate().compareTo(o2.getEndDate())).findFirst();
            endDate = optionalChargeCode.map(ChargeCode::getEndDate).orElse(null);
        }
        return endDate == null ? associationEndDate : associationEndDate == null ? endDate : associationEndDate.isBeforeOrSame(endDate) ? associationEndDate : endDate;
    }

    public String getDescription() {
        StringBuilder employeeChargeCode = new StringBuilder();
        for (ChargeCode chargeCode : getChargeCodes()) {
            if (!StringUtils.isBlank(chargeCode.getHierarchicalName()) && chargeCode.getChargeCodeName().equals("Job")) {
                employeeChargeCode = new StringBuilder(getFormatedHierarchicalName(chargeCode.getHierarchicalName() + " > "));
            } else {
                employeeChargeCode.append(chargeCode.getName()).append(" > ");
            }
        }
        if (payType != null) {
            return employeeChargeCode.append(payType.getName()).toString();
        } else
            return employeeChargeCode.substring(0, employeeChargeCode.length() - 3);
    }

    public String getFormatedHierarchicalName(String hiererchicyName) {
        return hiererchicyName.replace(":", " > ");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AuthorizeChargeAssociation that = (AuthorizeChargeAssociation) o;

        if (chargeCodes != null ? !chargeCodes.equals(that.chargeCodes) : that.chargeCodes != null) {
            return false;
        }
        return payType != null ? payType.equals(that.payType) : that.payType == null;
    }

    @Override
    public int hashCode() {
        int result = chargeCodes != null ? chargeCodes.hashCode() : 0;
        result = 31 * result + (payType != null ? payType.hashCode() : 0);
        return result;
    }
}