package com.ourtimesheet.repositorydo;

import com.ourtimesheet.data.QBDConnectionSessionDO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

/**
 * Created by hassan on 3/14/16.
 */
public interface QBDConnectionSessionDORepository extends MongoRepository<QBDConnectionSessionDO, String> {

    QBDConnectionSessionDO findByCompanyName(String companyName);

    QBDConnectionSessionDO findById(UUID sessionId);
}
