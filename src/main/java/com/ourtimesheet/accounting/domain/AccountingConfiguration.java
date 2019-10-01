package com.ourtimesheet.accounting.domain;

import com.ourtimesheet.common.Entity;
import com.ourtimesheet.paytype.PayTypeConfiguration;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * Created by Adeel on 6/1/2016.
 */
@Document(collection = "accountingConfiguration")
public abstract class AccountingConfiguration extends Entity implements AccountingSystemAware {

    protected AccountingConfiguration(UUID id) {
        super(id);
    }

    protected AccountingConfiguration() {
        super();
    }

    public abstract PayTypeConfiguration getPayTypeConfiguration();
}
