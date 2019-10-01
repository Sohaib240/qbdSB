package com.ourtimesheet.employee;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.paytype.EmployeePayType;
import com.ourtimesheet.paytype.PayType;
import com.ourtimesheet.timesheet.chargeCode.ChargeCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Talha on 11/1/2017.
 */
public class PayTypeDetail {

    private final PayType payType;
    private OurDateTime startDate;
    private OurDateTime endDate;
    private List<ChargeCode> chargeCodes;
    private boolean isAllPayTypesAssigned;

    public PayTypeDetail(PayType payType, OurDateTime startDate, OurDateTime endDate, List<ChargeCode> chargeCodes, boolean isAllPayTypesAssigned) {
        this.payType = payType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.chargeCodes = chargeCodes;
        this.isAllPayTypesAssigned = isAllPayTypesAssigned;
    }

    public PayTypeDetail(EmployeePayType employeePayType, boolean isAllPayTypesAssigned) {
        this.payType = employeePayType.getPayType();
        this.startDate = employeePayType.getStartDate();
        this.endDate = employeePayType.getEndDate();
        this.chargeCodes = employeePayType.getChargeCodes();
        this.isAllPayTypesAssigned = isAllPayTypesAssigned;
    }

    public PayType getPayType() {
        return payType;
    }

    public OurDateTime getStartDate() {
        return startDate;
    }

    public OurDateTime getEndDate() {
        return endDate;
    }

    public List<ChargeCode> getChargeCodes() {
        return chargeCodes == null ? new ArrayList<>() : chargeCodes;
    }

    public boolean isAllPayTypesAssigned() {
        return isAllPayTypesAssigned;
    }

    public String getPayTypeId(){
        return payType.getId() != null ? payType.getId().toString() : "";
    }

    public String getPayTypeName(){
        return payType.getName();
    }

    public String getTypeOfPayType() {
        return payType != null ? payType.getType() : "";
    }

    public String getPayTypeStartDate(){
         return startDate == null ? "" : startDate.dateOnlyStandardFormat();
    }

    public String getPayTypeEndDate(){
        return endDate == null ? "" : endDate.dateOnlyStandardFormat();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PayTypeDetail that = (PayTypeDetail) o;

        return payType != null ? payType.equals(that.payType) : that.payType == null;
    }

    @Override
    public int hashCode() {
        return payType != null ? payType.hashCode() : 0;
    }
}