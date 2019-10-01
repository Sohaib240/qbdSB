package com.ourtimesheet.repository;

import com.ourtimesheet.exception.CompanyNotFoundException;
import com.ourtimesheet.multitenant.CompanyHolder;
import com.ourtimesheet.multitenant.MultiTenantMongoDbFactory;
import com.ourtimesheet.repositorydo.TimesheetDORepository;
import com.ourtimesheet.timesheet.PaginatedTimesheetResponse;
import com.ourtimesheet.timesheet.Timesheet;
import com.ourtimesheet.util.MasterConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static javafx.scene.input.KeyCode.T;

public class TimesheetRepositoryImpl extends GenericRepository<Timesheet> implements TimesheetRepository {

    private static final Logger LOG = LoggerFactory.getLogger(TimesheetRepositoryImpl.class);

    private final MongoTemplate mongoTemplate;
    private final MasterConfigUtils masterConfigUtils;

    public TimesheetRepositoryImpl(TimesheetDORepository repository, MongoTemplate mongoTemplate, MasterConfigUtils masterConfigUtils) {
        super(repository);
        this.mongoTemplate = mongoTemplate;
        this.masterConfigUtils = masterConfigUtils;
    }

    @Override
    public PaginatedTimesheetResponse findTimesheetsByQuery(Query query) {
        try {
            setDatabase();
            List<Timesheet> timesheets = mongoTemplate.find(query, Timesheet.class);
            long totalTimesheets = mongoTemplate.count(query, Timesheet.class);
            return new PaginatedTimesheetResponse(timesheets, totalTimesheets);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            MultiTenantMongoDbFactory.clearDatabaseNameForCurrentThread();
        }
    }

    private void setDatabase() throws CompanyNotFoundException {
        String dbName = masterConfigUtils.getDBForDomain(CompanyHolder.getCompanyName());
        MultiTenantMongoDbFactory.setDatabaseNameForCurrentThread(dbName);
    }

    @Override
    public Optional<Timesheet> findById(UUID uuid) {
        return null;
    }

    @Override
    public boolean existsById(UUID uuid) {
        return false;
    }

    @Override
    public Iterable<Timesheet> findAllById(Iterable<UUID> uuids) {
        return null;
    }

    @Override
    public void deleteById(UUID uuid) {

    }

    @Override
    public void deleteAll(Iterable<? extends Timesheet> entities) {

    }
}
