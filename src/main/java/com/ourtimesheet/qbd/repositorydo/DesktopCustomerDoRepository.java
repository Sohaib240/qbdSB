package com.ourtimesheet.qbd.repositorydo;

import com.ourtimesheet.qbd.domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

/**
 * Created by Abdus Salam on 5/30/2016.
 */
public interface DesktopCustomerDoRepository extends MongoRepository<Customer, UUID> {

    Customer findByQuickBooksId(String quickBooksId);

    Customer findByName(String customerName);
}
