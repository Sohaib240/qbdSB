package com.ourtimesheet.qbd.domain;

import com.ourtimesheet.common.Entity;
import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.timesheet.chargeCode.ChargeCode;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Abdus Salam on 5/31/2016.
 */

@Document(collection = "serviceItem")
public class ServiceItem extends Entity implements ChargeCode {

    private final String name;
    private final String quickBooksId;
    private boolean active;
    private String leafName;

    @PersistenceConstructor
    public ServiceItem(UUID id, String name, boolean active, String quickBooksId, String leafName) {
        super(id);
        this.name = name;
        this.active = active;
        this.quickBooksId = quickBooksId;
        this.leafName = leafName;
    }

    public String getLeafName() {
        return leafName;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public OurDateTime getEndDate() {
        return null;
    }

    @Override
    public String getCollectionName() {
        return "serviceItem";
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public boolean isEffective(OurDateTime ourDateTime) {
        return isActive();
    }

    public void setActiveStatus(boolean status) {
        this.active = status;
    }

    public String getQuickBooksId() {
        return quickBooksId;
    }

    @Override
    public String getChargeCodeName() {
        return "Service Item";
    }

    @Override
    public Set<ChargeCode> children() {
        return Collections.emptySet();
    }

    @Override
    public boolean isRequired() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceItem that = (ServiceItem) o;

        return that != null ? quickBooksId.equals(that.quickBooksId) : false;

    }

    @Override
    public int hashCode() {
        return quickBooksId.hashCode();
    }

    @Override
    public String getHierarchicalName() {
        return name;
    }

    @Override
    public boolean isBillable() {
        return true;
    }
}
