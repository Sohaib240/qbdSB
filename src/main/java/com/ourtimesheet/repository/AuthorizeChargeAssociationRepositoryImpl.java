package com.ourtimesheet.repository;

import com.mongodb.DBObject;
import com.ourtimesheet.AssociationDocumentCreator;
import com.ourtimesheet.association.AuthorizeChargeAssociation;
import com.ourtimesheet.exception.CompanyNotFoundException;
import com.ourtimesheet.multitenant.CompanyHolder;
import com.ourtimesheet.multitenant.MultiTenantMongoDbFactory;
import com.ourtimesheet.repositorydo.AuthorizeChargeAssociationDORepository;
import com.ourtimesheet.util.MasterConfigUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.TimeZone;


/**
 * Created by Abdus Salam on 11/2/2017.
 */
public class AuthorizeChargeAssociationRepositoryImpl extends GenericRepository<AuthorizeChargeAssociation> implements AuthorizeChargeAssociationRepository {

    private static final String AUTHORIZE_CHARGE_ASSOCIATION = "authorizeChargeAssociation";
    private final AuthorizeChargeAssociationDORepository authorizeChargeAssociationDORepository;
    private final MongoTemplate mongoTemplate;
    private final MasterConfigUtils masterConfigUtils;
    private final AssociationDocumentCreator associationDocumentCreator;

    public AuthorizeChargeAssociationRepositoryImpl(AuthorizeChargeAssociationDORepository repository, MongoTemplate mongoTemplate, MasterConfigUtils masterConfigUtils,
                                                    AssociationDocumentCreator associationDocumentCreator) {
        super(repository);
        this.authorizeChargeAssociationDORepository = repository;
        this.mongoTemplate = mongoTemplate;
        this.masterConfigUtils = masterConfigUtils;
        this.associationDocumentCreator = associationDocumentCreator;
    }


    @Override
    public void saveAll(List<AuthorizeChargeAssociation> authorizeChargeAssociations, List<String> allChargeCodesTypes, TimeZone timeZone) {
        try {
            setDatabase();
            for (AuthorizeChargeAssociation association : authorizeChargeAssociations) {
                DBObject associationObject = associationDocumentCreator.create(association, allChargeCodesTypes, timeZone);
                List<AuthorizeChargeAssociation> associationsFromDB = mongoTemplate.find(new Query(Criteria.where("searchString").is(associationObject.get("searchString"))), AuthorizeChargeAssociation.class);
                if (associationsFromDB.isEmpty()) {
                    mongoTemplate.save(associationObject, AUTHORIZE_CHARGE_ASSOCIATION);
                } else if (!associationsFromDB.get(0).isActive()) {
                    associationObject.put("_id", associationsFromDB.get(0).getId());
                    mongoTemplate.save(associationObject, AUTHORIZE_CHARGE_ASSOCIATION);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to save associations");
        } finally {
            MultiTenantMongoDbFactory.clearDatabaseNameForCurrentThread();
        }

    }

    private void setDatabase() throws CompanyNotFoundException {
        String dbName = masterConfigUtils.getDBForDomain(CompanyHolder.getCompanyName());
        MultiTenantMongoDbFactory.setDatabaseNameForCurrentThread(dbName);
    }
}