package com.ourtimesheet.timesheet.hoursWorked;

import com.ourtimesheet.paytype.PayType;
import com.ourtimesheet.timesheet.chargeCode.AuthorizedCharge;

/**
 * Created by umars on 11/24/2017.
 */
public class AuthorizeChargeDetail {

    private AuthorizedCharge authorizedCharge;
    private PayType payType;

    public AuthorizeChargeDetail(AuthorizedCharge authorizedCharge, PayType payType) {
        this.authorizedCharge = authorizedCharge;
        this.payType = payType;
    }

    public AuthorizedCharge getAuthorizedCharge() {
        return authorizedCharge;
    }

    public PayType getPayType() {
        return payType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthorizeChargeDetail that = (AuthorizeChargeDetail) o;

        if (!authorizedCharge.equals(that.authorizedCharge)) return false;
        return payType == null || payType.equals(that.payType);
    }

    @Override
    public int hashCode() {
        int result = authorizedCharge.hashCode();
        result = payType != null ? result = 31 * result + payType.hashCode() : 31 * result;
        return result;
    }
}
