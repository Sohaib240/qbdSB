package com.ourtimesheet.paytype;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.employee.PayTypeDetail;
import com.ourtimesheet.timesheet.chargeCode.ChargeCode;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhammad Talha on 2/21/2017.
 */
public class EmployeePayType {

    private PayType payType;
    private OurDateTime startDate;
    private OurDateTime endDate;
    @DBRef
    private List<ChargeCode> chargeCodes;

    public EmployeePayType() {
    }

    public EmployeePayType(PayType payType, OurDateTime startDate, OurDateTime endDate, List<ChargeCode> chargeCodes) {
        this.payType = payType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.chargeCodes = chargeCodes;
    }

    public EmployeePayType(PayTypeDetail payTypeDetail) {
        this.payType = payTypeDetail.getPayType();
        this.startDate = payTypeDetail.getStartDate();
        this.endDate = payTypeDetail.getEndDate();
        this.chargeCodes = payTypeDetail.getChargeCodes();
    }

    public PayType getPayType() {
        return payType;
    }

    public void updateStartDate(OurDateTime startDate) {
        this.startDate = startDate;
    }

    public OurDateTime getStartDate() {
        return startDate;
    }

    public void updateEndDate(OurDateTime endDate) {
        this.endDate = endDate;
    }

    public OurDateTime getEndDate() {
        return endDate;
    }

    public String getPayTypeId() {
        return payType.getId().toString();
    }

    public boolean isEffective(OurDateTime startDate, OurDateTime endDate) {
        return isStartDateEffective(startDate, endDate) && isEndDateEffective(startDate, endDate);
    }

    private boolean isEndDateEffective(OurDateTime startDate, OurDateTime endDate) {
        return this.endDate == null || (this.endDate.isAfterOrSame(startDate) || (this.endDate.isAfterOrSame(endDate) && this.endDate.isBeforeOrSame(endDate)));
    }

    private boolean isStartDateEffective(OurDateTime startDate, OurDateTime endDate) {
        return this.startDate.isBeforeOrSame(startDate) || this.startDate.isBeforeOrSame(endDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeePayType)) return false;
        EmployeePayType that = (EmployeePayType) o;
        return getPayTypeId().equals(that.getPayTypeId());
    }

    @Override
    public int hashCode() {
        int result = payType.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        return result;
    }

    public List<ChargeCode> getChargeCodes() {
        return chargeCodes == null ? new ArrayList<>() : chargeCodes;
    }

    public void updateChargeCodes(List<ChargeCode> chargeCodes) {
        this.chargeCodes = chargeCodes;
    }

    public boolean hasChargeCodes() {
        return chargeCodes != null && chargeCodes.size() > 0;
    }

    public boolean isOvertime() {
        return payType.isOvertime();
    }

    public boolean isRegular() {
        return payType.isRegular();
    }

    public boolean isLeave() {
        return payType.isLeave();
    }

    public String getId() {
        return payType != null ? payType.getId().toString() : "";
    }

    public String getName() {
        return this.payType.getName();
    }

    public boolean isActive() {
        return this.payType.isActive();
    }

    public String getType() {
        return payType != null ? payType.getType() : "";
    }

    public ChargeCode getChargeCode() {
        return chargeCodes != null ? chargeCodes.get(0) : null;
    }
}
