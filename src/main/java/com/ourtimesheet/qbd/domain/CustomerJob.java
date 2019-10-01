package com.ourtimesheet.qbd.domain;

import com.ourtimesheet.common.Entity;
import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.timesheet.chargeCode.ChargeCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Talha on 5/30/2016.
 */
@Document(collection = "customerJobs")
public class CustomerJob extends Entity implements ChargeCode {

    private final String name;
    private final String quickBooksId;
    private Set<CustomerJob> children;
    private boolean active;
    private final String hierarchicalName;
    private final String parentId;

    @PersistenceConstructor
    public CustomerJob(UUID id, String name, String quickBooksId, boolean active, Set<CustomerJob> children, String hierarchicalName, String parentId) {
        super(id);
        this.name = name;
        this.quickBooksId = quickBooksId;
        this.active = active;
        this.children = children;
        this.hierarchicalName = hierarchicalName;
        this.parentId = parentId;
    }

    public String getQuickBooksId() {
        return quickBooksId;
    }

    @Override
    public OurDateTime getEndDate() {
        return null;
    }

    @Override
    public String getCollectionName() {
        return "customerJobs";
    }

    public boolean isActive() {
        return active;
    }

    public void setActiveStatus(boolean status) {
        this.active = status;
    }

    public void addChild(CustomerJob customerJob) {
        children.add(customerJob);
    }

    public String getHierarchicalName() {
        return StringUtils.isNoneBlank(hierarchicalName) ? hierarchicalName : "";
    }

    @Override
    public boolean isEffective(OurDateTime ourDateTime) {
        return isActive();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public String getChargeCodeName() {
        return "Job";
    }

    public String getParentId() {
        return parentId;
    }

    public Set<CustomerJob> getChildren() {
        return children;
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

        CustomerJob that = (CustomerJob) o;

        return that != null && quickBooksId.equals(that.quickBooksId);

    }

    @Override
    public int hashCode() {
        return quickBooksId.hashCode();
    }

    @Override
    public boolean isBillable() {
        return false;
    }
}
