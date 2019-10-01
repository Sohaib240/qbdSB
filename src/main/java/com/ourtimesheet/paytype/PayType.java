package com.ourtimesheet.paytype;

import com.ourtimesheet.common.Entity;
import com.ourtimesheet.datetime.OurDateTime;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import java.util.UUID;

/**
 * Created by Abdus Salam on 2/15/2017.
 */
@Document(collection = "paytype")
public abstract class PayType extends Entity {

    private final String name;
    private final String quickBooksId;
    private boolean active;

    protected PayType(UUID id, String name, boolean active, String quickBooksId) {
        super(id);
        Assert.isTrue(StringUtils.isNotBlank(name), "Name cannot be empty");
        this.name = name;
        this.active = active;
        this.quickBooksId = quickBooksId;
    }

    public String getName() {
        return name;
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

    public abstract boolean isLeave();

    public abstract boolean isOvertime();

    public abstract boolean isRegular();

    public abstract boolean isDoubleOvertime();

    public abstract String getType();

    public abstract String getAliasName();

    public abstract boolean isEffective(OurDateTime dateTime);

    public abstract OurDateTime getEndDate();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PayType payType = (PayType) o;
        if (active != payType.active) return false;
        return name.equals(payType.name);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (active ? 1 : 0);
        return result;
    }
}
