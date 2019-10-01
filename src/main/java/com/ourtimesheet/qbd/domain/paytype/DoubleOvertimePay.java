package com.ourtimesheet.qbd.domain.paytype;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.paytype.PayType;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.UUID;

/**
 * Created by umars on 2/19/2018.
 */
public class DoubleOvertimePay extends PayType {

    @PersistenceConstructor
    public DoubleOvertimePay(UUID id, String name, boolean active, String quickBooksId) {
        super(id, name, active, quickBooksId);
    }

    @Override
    public OurDateTime getEndDate() {
        return null;
    }

    @Override
    public boolean isLeave() {
        return false;
    }

    @Override
    public boolean isEffective(OurDateTime dateTime) {
        return isActive();
    }

    @Override
    public boolean isOvertime() {
        return false;
    }

    @Override
    public boolean isRegular() {
        return false;
    }

    @Override
    public boolean isDoubleOvertime() {
        return true;
    }

    @Override
    public String getType() {
        return "DoubleOvertime";
    }

    @Override
    public String getAliasName() {
        return getName();
    }
}
