package com.ourtimesheet.qbd.repositorydo;

import com.ourtimesheet.qbd.domain.CustomerJob;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

/**
 * Created by Talha on 5/30/2016.
 */
public interface DesktopCustomerJobDORepository extends MongoRepository<CustomerJob, UUID> {
    CustomerJob findByQuickBooksId(String quickBooksId);
}
