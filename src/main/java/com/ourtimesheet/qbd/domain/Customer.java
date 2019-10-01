package com.ourtimesheet.qbd.domain;

import com.ourtimesheet.common.Entity;
import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.timesheet.chargeCode.ChargeCode;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Abdus Salam on 5/30/2016.
 */
@Document(collection = "customer")
public class Customer extends Entity implements ChargeCode {

    private final String name;
    private final String quickBooksId;
    private Set<CustomerJob> children;
    private boolean active;

    @PersistenceConstructor
    public Customer(UUID id, String name, boolean active, String quickBooksId, Set<CustomerJob> children) {
        super(id);
        this.name = name;
        this.active = active;
        this.quickBooksId = quickBooksId;
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void addChild(CustomerJob customerJob) {
        children.add(customerJob);
    }

    public Set<CustomerJob> getChildren() {
        return children;
    }

    public boolean isActive() {
        return active;
    }

    public void setActiveStatus(boolean status) {
        this.active = status;
    }

    public String getQuickBooksId() {
        return quickBooksId;
    }

    @Override
    public OurDateTime getEndDate() {
        return null;
    }

    @Override
    public boolean isEffective(OurDateTime ourDateTime) {
        return isActive();
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public String getChargeCodeName() {
        return "Customer";
    }

    @Override
    public String getCollectionName() {
        return "customer";
    }

    @Override
    public Set<ChargeCode> children() {
        if (children == null) {
            return Collections.emptySet();
        }
        Set<ChargeCode> result = new HashSet<>(children.size());
        result.addAll(children);
        return result;
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
        Customer customer = (Customer) o;

        return quickBooksId.equals(customer.quickBooksId);

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
        return false;
    }
}