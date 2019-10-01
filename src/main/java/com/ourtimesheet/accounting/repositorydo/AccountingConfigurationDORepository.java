package com.ourtimesheet.accounting.repositorydo;

import com.ourtimesheet.accounting.domain.AccountingConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

/**
 * Created by Abdus Salam on 6/1/2016.
 */
public interface AccountingConfigurationDORepository extends MongoRepository<AccountingConfiguration, UUID> {
}
