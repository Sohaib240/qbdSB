package com.ourtimesheet.repository;

import com.ourtimesheet.exception.CompanyNotFoundException;
import com.ourtimesheet.job.Job;
import com.ourtimesheet.job.JobStatus;
import com.ourtimesheet.multitenant.CompanyHolder;
import com.ourtimesheet.multitenant.MultiTenantMongoDbFactory;
import com.ourtimesheet.repositorydo.JobDORepository;
import com.ourtimesheet.util.MasterConfigUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.UUID;

/**
 * Created by Jazib on 03/03/2017.
 */
public class JobRepositoryImpl extends GenericRepository<Job> implements JobRepository {

    private final JobDORepository jobDORepository;
    private final MongoTemplate mongoTemplate;
    private final MasterConfigUtils masterConfigUtils;

    public JobRepositoryImpl(JobDORepository repository, MongoTemplate mongoTemplate, MasterConfigUtils masterConfigUtils) {
        super(repository);
        this.jobDORepository = repository;
        this.mongoTemplate = mongoTemplate;
        this.masterConfigUtils = masterConfigUtils;
    }

    @Override
    public List<Job> findAllInProcessJobs() {
        return jobDORepository.findByJobStatus(JobStatus.PROCESSING);
    }

    @Override
    public boolean isAlreadyProcessed(UUID id) {
        try {
            MultiTenantMongoDbFactory.setDatabaseNameForCurrentThread(masterConfigUtils.getDBForDomain(CompanyHolder.getCompanyName()));
            Query query = new Query();
            query.addCriteria(Criteria.where("timesheetExportJobList.timesheetID").is(id));
            return mongoTemplate.count(query, Job.class) > 0;
        } catch (CompanyNotFoundException e) {
            throw new RuntimeException("Unable to access database");
        } finally {
            MultiTenantMongoDbFactory.clearDatabaseNameForCurrentThread();
        }
    }

    @Override
    public boolean isTimesheetPresentInAnotherJobInProgress(UUID timesheetId, UUID jobId) {
        try {
            MultiTenantMongoDbFactory.setDatabaseNameForCurrentThread(masterConfigUtils.getDBForDomain(CompanyHolder.getCompanyName()));
            Query query = new Query();
            query.addCriteria(Criteria.where("timesheetExportJobList.timesheetID").is(timesheetId)
                    .andOperator(Criteria.where("jobStatus").is("PROCESSING")));
            query.addCriteria(Criteria.where("_id").nin(jobId));
            return mongoTemplate.count(query, Job.class) > 0;
        } catch (CompanyNotFoundException e) {
            throw new RuntimeException("Unable to access database");
        } finally {
            MultiTenantMongoDbFactory.clearDatabaseNameForCurrentThread();
        }
    }
}
