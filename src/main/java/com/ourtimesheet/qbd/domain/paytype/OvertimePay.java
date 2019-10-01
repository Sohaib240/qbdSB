package com.ourtimesheet.qbd.domain.paytype;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.paytype.PayType;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.UUID;

/**
 * Created by Abdus Salam on 2/15/2017.
 */
public class OvertimePay extends PayType {

    @PersistenceConstructor
    public OvertimePay(UUID id, String name, boolean active, String quickBooksId) {
        super(id, name, active, quickBooksId);
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
    public OurDateTime getEndDate() {
        return null;
    }

    @Override
    public boolean isOvertime() {
        return true;
    }

    @Override
    public boolean isRegular() {
        return false;
    }

    @Override
    public boolean isDoubleOvertime() {
        return false;
    }

    @Override
    public String getType() {
        return "Overtime";
    }

    @Override
    public String getAliasName() {
        return getName();
    }
}
