package com.ourtimesheet.repository.superAdmin;

import com.ourtimesheet.employee.SuperAdmin;
import com.ourtimesheet.repository.GenericRepository;
import com.ourtimesheet.repositorydo.SuperAdminDORepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * Created by Abdus Salam on 10/4/2017.
 */
public class SuperAdminRepositoryImpl extends GenericRepository<SuperAdmin> implements SuperAdminRepository {

    private final SuperAdminDORepository superAdminDORepository;
    private final MongoTemplate mongoTemplate;

    public SuperAdminRepositoryImpl(SuperAdminDORepository repository, MongoTemplate mongoTemplate) {
        super(repository);
        this.superAdminDORepository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public SuperAdmin findByEmail(String emailAddress) {
        return superAdminDORepository.findByEmailIgnoreCase(emailAddress);
    }
}