package com.ourtimesheet.timesheet.chargeCode;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Abdus Salam on 11/14/2016.
 */
public class LeaveAuthorizedCharge implements AuthorizedCharge {

    private List<ChargeCode> chargeCodes;

    public LeaveAuthorizedCharge(List<ChargeCode> chargeCodes) {
        this.chargeCodes = chargeCodes;
    }

    @Override
    public List<ChargeCode> getAuthorizedCharge() {
        return getChargeCodes().stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public boolean isLeave() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LeaveAuthorizedCharge that = (LeaveAuthorizedCharge) o;

        return chargeCodes != null ? chargeCodes.equals(that.chargeCodes) : that.chargeCodes == null;
    }

    @Override
    public int hashCode() {
        return chargeCodes != null ? chargeCodes.hashCode() : 0;
    }

    private List<ChargeCode> getChargeCodes() {
        if (this.chargeCodes == null) {
            this.chargeCodes = new ArrayList<>();
        }
        return this.chargeCodes;
    }

    @Override
    public String toString() {
        return StringUtils.substringBetween(getAuthorizedCharge().stream().filter(Objects::nonNull).map(ChargeCode::getName).collect(Collectors.toList()).toString(), "[", "]");
    }
}