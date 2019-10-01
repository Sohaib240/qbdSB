package com.ourtimesheet.data;

import com.ourtimesheet.common.Entity;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * Created by zohaib on 02/09/17.
 */
@Document(collection = "qbdConnectionSession")
public class QBDConnectionSessionDO extends Entity {

    private final String companyName;

    private final String lastError;

    @PersistenceConstructor
    public QBDConnectionSessionDO(UUID id, String companyName, String lastError) {
        super(id);
        this.companyName = companyName;
        this.lastError = lastError;
    }

    public QBDConnectionSessionDO(String companyName, String lastError) {
        super();
        this.companyName = companyName;
        this.lastError = lastError;
    }

    public String getCompanyName() {
        return companyName;
    }

}
