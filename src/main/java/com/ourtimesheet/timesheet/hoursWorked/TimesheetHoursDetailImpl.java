package com.ourtimesheet.timesheet.hoursWorked;

import com.ourtimesheet.paidTimeOff.Leave;
import com.ourtimesheet.paytype.PayTypeConfiguration;
import com.ourtimesheet.timesheet.Timesheet;
import com.ourtimesheet.timesheet.chargeCode.AuthorizedCharge;
import com.ourtimesheet.timesheet.chargeCode.ChargeCode;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Abdus Salam on 10/19/2017.
 */
public class TimesheetHoursDetailImpl implements TimesheetHoursDetail {

    private final Timesheet timesheet;

    public TimesheetHoursDetailImpl(Timesheet timesheet) {
        this.timesheet = timesheet;
    }

    @Override
    public List<HoursDetail> getHoursDetailByAuthorizeCharge(PayTypeConfiguration payTypeConfiguration) {
        switch (payTypeConfiguration) {
            case EMPLOYEE_MANAGED:
                return createHoursDetailsForEmployeeManage();
            case SYSTEM_MANAGED:
                return createHoursDetailsForNoPaytype();
            case NO_PAYTYPE:
            default:
                return createHoursDetailsForNoPaytype();
        }
    }

    private List<HoursDetail> createHoursDetailsForEmployeeManage() {
        Map<AuthorizeChargeDetail, List<HoursWorked>> authorizeChargeDetailListMap = timesheet.hoursWorkedMatrix().getHoursWorkedMapDetail();
        List<HoursDetail> hoursDetails = new ArrayList<>();
        authorizeChargeDetailListMap.forEach(((authorizeChargeDetail, hoursWorkeds) -> hoursDetails.add(createHourDetail(authorizeChargeDetail, hoursWorkeds))));
        return hoursDetails;
    }

    private HoursDetail createHourDetail(AuthorizeChargeDetail authorizeChargeDetail, List<HoursWorked> hoursWorkeds) {
        return new HoursDetail(getTotalHourOfRow(hoursWorkeds), authorizeChargeDetail.getPayType(), authorizeChargeDetail.getAuthorizedCharge(), hoursWorkeds, getLeave(authorizeChargeDetail.getAuthorizedCharge()));
    }

    private double getTotalHourOfRow(List<HoursWorked> hoursWorked) {
        return hoursWorked.stream().mapToDouble(HoursWorked::getHours).sum();
    }

    private List<HoursDetail> createHoursDetailsForNoPaytype() {
        List<HoursDetail> hoursDetails = new ArrayList<>();
        HoursWorkedMatrix hoursWorkedMatrix = timesheet.hoursWorkedMatrix();
        hoursWorkedMatrix.getHoursWorkedTotalMapByAuthorizeCharge().forEach((authorizedCharge, hours) ->
                hoursDetails.add(new HoursDetail(hours, null, authorizedCharge, hoursWorkedMatrix.getHoursWorkedMap().get(authorizedCharge), getLeave(authorizedCharge))));
        return hoursDetails;
    }

    private Leave getLeave(AuthorizedCharge authorizedCharge) {
        Optional<ChargeCode> leaveCode = authorizedCharge.getAuthorizedCharge().stream().filter(chargeCode -> chargeCode != null &&  StringUtils.equalsIgnoreCase(chargeCode.getChargeCodeName(), "Leave")).findFirst();
        return authorizedCharge.isLeave() && leaveCode.isPresent() ? (Leave) leaveCode.get() : null;
    }
}