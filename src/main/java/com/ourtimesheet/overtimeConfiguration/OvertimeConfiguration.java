package com.ourtimesheet.overtimeConfiguration;

import com.ourtimesheet.paytype.PayType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

/**
 * Created by Abdus Salam on 3/7/2018.
 */
public abstract class OvertimeConfiguration {

    private final double hours;
    @DBRef
    private final PayType payType;
    @Transient
    protected List<PayType> regularPayTypes;

    protected OvertimeConfiguration(double hours, PayType payType) {
        this.hours = hours;
        this.payType = payType;
    }


    public abstract int getOrder();

    public double getHours() {
        return hours;
    }

    public PayType getPayType() {
        return payType;
    }

    public String getPayTypeId() {
        return payType != null ? payType.getId().toString() : StringUtils.EMPTY;
    }

    public boolean hasPayType() {
        return payType != null;
    }

    protected void updateRegularPayTypes(List<PayType> regularPayTypes) {
        this.regularPayTypes = regularPayTypes;
    }

    protected boolean hasPayTypes() {
        return regularPayTypes != null && !regularPayTypes.isEmpty();
    }
}