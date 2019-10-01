package com.ourtimesheet.timesheet.hoursWorked;

import com.ourtimesheet.paidTimeOff.Leave;
import com.ourtimesheet.paytype.PayType;
import com.ourtimesheet.timesheet.chargeCode.AuthorizedCharge;
import com.ourtimesheet.timesheet.chargeCode.ChargeCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Abdus Salam on 10/19/2017.
 */
public class HoursDetail {

    private double totalHours;
    private PayType payType;
    private Leave leave;
    private AuthorizedCharge authorizedCharge;
    private List<HoursWorked> hoursWorkeds;

    public HoursDetail(double totalHours, PayType payType, AuthorizedCharge authorizedCharge, List<HoursWorked> hoursWorkeds, Leave leave) {
        this.totalHours = totalHours;
        this.payType = payType;
        this.authorizedCharge = authorizedCharge;
        this.hoursWorkeds = hoursWorkeds;
        this.leave = leave;
    }

    public HoursDetail(double totalHours, PayType payType, AuthorizedCharge authorizedCharge, List<HoursWorked> hoursWorkeds) {
        this.totalHours = totalHours;
        this.payType = payType;
        this.authorizedCharge = authorizedCharge;
        this.hoursWorkeds = hoursWorkeds;
    }

    public double getTotalHours() {
        return totalHours;
    }

    public PayType getPayType() {
        return leave != null ? leave.getPayType() : payType;
    }

    public Leave getLeave() {
        return leave;
    }

    public String getPayTypeName() {
        return hasLeave() ? leave.getPayType() != null ? leave.getPayType().getName() : "" : payType != null ? payType.getName() : "";
    }

    public AuthorizedCharge getAuthorizedCharge() {
        return authorizedCharge;
    }

    public List<HoursWorked> getHoursWorkeds() {
        return hoursWorkeds != null ? hoursWorkeds.stream().sorted(Comparator.comparing(HoursWorked::getWorkedDate)).collect(Collectors.toList()) : new ArrayList<>();
    }

    public List<ChargeCode> getChargeCodes() {
        return authorizedCharge.getAuthorizedCharge();
    }

    public boolean hasLeave() {
        return getLeaveCharge().isPresent();
    }

    public String getLeaveName() {
        Optional<ChargeCode> code = getLeaveCharge();
        return code.isPresent() ? code.get().getName() : StringUtils.EMPTY;
    }

    public boolean isBillable() {
        return hoursWorkeds.stream().anyMatch(hoursWorked -> hoursWorked.getHours() > 0 && hoursWorked.isBillable());
    }

    public boolean hasDepartmentAuthorizeCharge() {
        try {
            return authorizedCharge.getAuthorizedCharge().stream().anyMatch(chargeCode -> StringUtils.equalsIgnoreCase(chargeCode.getChargeCodeName(), "department"));
        } catch (Exception ex) {
            return false;
        }
    }

    public String getDepartmentAuthorizeChargeName() {
        Optional<ChargeCode> code = authorizedCharge.getAuthorizedCharge().stream().filter(chargeCode -> chargeCode != null && StringUtils.equalsIgnoreCase(chargeCode.getChargeCodeName(), "department")).findFirst();
        return code.isPresent() ? code.get().getName() : StringUtils.EMPTY;
    }

    public String getEmployeeUserId() {
        return this.hoursWorkeds.get(0) != null ? hoursWorkeds.get(0).getEmployee().getUserId() : StringUtils.EMPTY;
    }

    public boolean hasPayType() {
        return hasLeave() ? leave != null && leave.getPayType() != null : payType != null;
    }

    public String getAuthorizeCharge() {
        StrBuilder authorizeCharge = new StrBuilder(authorizedCharge.toString());
        authorizeCharge.append(hasPayType() ? ", " + getPayTypeName() : "");
        return authorizeCharge.build();
    }

    public boolean hasAuthorizeCharge(String type) {
        return authorizedCharge != null && StringUtils.isNotBlank(type) ? authorizedCharge.getAuthorizedCharge().stream().filter(chargeCode -> chargeCode != null && chargeCode.getChargeCodeName().equalsIgnoreCase(type)).count() > 0 : null;
    }

    public String getChargeCodeName(String type) {
        Optional<ChargeCode> code = authorizedCharge.getAuthorizedCharge().stream().filter(chargeCode -> chargeCode != null && chargeCode.getChargeCodeName().equals(type)).findFirst();
        return code.map(ChargeCode::getName).orElse(null);
    }

    public String getPayTypeAliseName() {
        return payType != null ? StringUtils.isNotBlank(payType.getAliasName()) ? payType.getAliasName() : payType.getName() : StringUtils.EMPTY;
    }

    private Optional<ChargeCode> getLeaveCharge() {
        return authorizedCharge.getAuthorizedCharge().stream().filter(chargeCode -> chargeCode != null && chargeCode.getChargeCodeName().equalsIgnoreCase("leave")).findFirst();
    }
}