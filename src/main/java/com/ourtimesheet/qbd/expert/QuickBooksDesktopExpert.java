package com.ourtimesheet.qbd.expert;

import com.ourtimesheet.accounting.service.AccountingExpert;
import com.ourtimesheet.exception.CompanyNotFoundException;
import com.ourtimesheet.multitenant.CompanyHolder;
import com.ourtimesheet.multitenant.MultiTenantMongoDbFactory;
import com.ourtimesheet.util.MasterConfigUtils;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adeel on 5/31/2016.
 */
public class QuickBooksDesktopExpert implements AccountingExpert {


    private final MongoTemplate mongoTemplate;
    private final MasterConfigUtils masterConfigUtils;

    public QuickBooksDesktopExpert(MongoTemplate mongoTemplate, MasterConfigUtils masterConfigUtils) {
        this.mongoTemplate = mongoTemplate;
        this.masterConfigUtils = masterConfigUtils;
    }

    @Override
    public List<String> findAllChargeCodesTypes() {
        try {
            MultiTenantMongoDbFactory.setDatabaseNameForCurrentThread(masterConfigUtils.getDBForDomain(CompanyHolder.getCompanyName()));
            List<String> allTypes = new ArrayList<>();
            if (mongoTemplate.collectionExists("customer"))
                allTypes.add("Customer");
            if (mongoTemplate.collectionExists("class"))
                allTypes.add("Class Type");
            if (mongoTemplate.collectionExists("serviceItem"))
                allTypes.add("Service Item");
            if (mongoTemplate.collectionExists("paytype"))
                allTypes.add("Pay Type");
            return allTypes;

        } catch (CompanyNotFoundException e) {
            throw new RuntimeException("Unable to access database");
        } finally {
            MultiTenantMongoDbFactory.clearDatabaseNameForCurrentThread();
        }
    }
}