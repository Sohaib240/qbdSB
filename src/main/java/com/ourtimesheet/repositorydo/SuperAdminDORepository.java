package com.ourtimesheet.repositorydo;

import com.ourtimesheet.employee.SuperAdmin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.UUID;

/**
 * Created by Abdus Salam on 10/4/2017.
 */
public interface SuperAdminDORepository extends MongoRepository<SuperAdmin, UUID> {

    @Query(value = "{'standardAuthentication.emailAddress' : ?0}")
    SuperAdmin findByEmailIgnoreCase(String emailAddress);
}
