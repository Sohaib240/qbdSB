package com.ourtimesheet.repositorydo;

import com.ourtimesheet.paytype.PayType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by Abdus Salam on 2/15/2017.
 */
public interface PayTypeDORepository extends MongoRepository<PayType, UUID> {

    PayType findByQuickBooksId(String quickBooksId);
}
