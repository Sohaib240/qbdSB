package com.ourtimesheet.paidTimeOff.carryOver;

import com.ourtimesheet.common.Entity;
import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.paidTimeOff.carryOver.carryOverType.CarryOverType;
import org.springframework.util.Assert;

import java.util.TimeZone;
import java.util.UUID;

/**
 * Created by Abdus Salam on 1/12/2018.
 */
public abstract class CarryOver extends Entity {

    protected final CarryOverType carryOverType;

    protected CarryOver(UUID id, CarryOverType carryOverType) {
        super(id);
        Assert.isTrue(carryOverType != null, "Carry over type cannot be null");
        this.carryOverType = carryOverType;
    }

    public CarryOverType getCarryOverType() {
        return carryOverType;
    }

    public abstract OurDateTime getCarryOverDate(TimeZone timeZone);

    public abstract boolean isEffective(TimeZone timeZone);

    public double getQty() {
        return carryOverType.getQty();
    }
}