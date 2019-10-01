package com.ourtimesheet.repositorydo;

import com.ourtimesheet.association.AuthorizeChargeAssociation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * Created by Abdus Salam on 11/2/2017.
 */
public interface AuthorizeChargeAssociationDORepository extends MongoRepository<AuthorizeChargeAssociation, UUID> {

}