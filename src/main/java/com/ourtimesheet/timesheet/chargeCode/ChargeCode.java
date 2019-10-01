package com.ourtimesheet.timesheet.chargeCode;

import com.ourtimesheet.datetime.OurDateTime;

import java.util.Set;
import java.util.UUID;

/**
 * Created by hassan on 6/7/16.
 */
public interface ChargeCode {

    boolean isActive();

    UUID getId();

    String getName();

    int getOrder();

    String getChargeCodeName();

    Set<ChargeCode> children();

    boolean isRequired();

    String getHierarchicalName();

    boolean isBillable();

    boolean isEffective(OurDateTime ourDateTime);

    OurDateTime getEndDate();

    String getCollectionName();
}
