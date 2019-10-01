package com.ourtimesheet.repositorydo;

import com.ourtimesheet.profile.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

/**
 * Created by Click Chain on 3/16/2016.
 */
public interface CompanyDORepository extends MongoRepository<Company, UUID> {
}
