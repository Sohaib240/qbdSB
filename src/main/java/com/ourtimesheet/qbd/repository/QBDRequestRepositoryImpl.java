package com.ourtimesheet.qbd.repository;

import com.ourtimesheet.exception.CompanyNotFoundException;
import com.ourtimesheet.job.ResultMapping;
import com.ourtimesheet.multitenant.CompanyHolder;
import com.ourtimesheet.multitenant.MultiTenantMongoDbFactory;
import com.ourtimesheet.qbd.helper.QBDRequestStatus;
import com.ourtimesheet.qbd.query.QBDRequest;
import com.ourtimesheet.qbd.repositorydo.DesktopQBDRequestDORepository;
import com.ourtimesheet.repository.GenericRepository;
import com.ourtimesheet.timesheet.hoursWorked.HoursWorked;
import com.ourtimesheet.util.MasterConfigUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by Abdus Salam on 5/31/2016.
 */
public class QBDRequestRepositoryImpl extends GenericRepository<QBDRequest> implements QBDRequestRepository {

    private final DesktopQBDRequestDORepository desktopQbdRequestDORepository;
    private final MongoTemplate mongoTemplate;
    private final MasterConfigUtils masterConfigUtils;

    public QBDRequestRepositoryImpl(DesktopQBDRequestDORepository repository, MongoTemplate mongoTemplate, MasterConfigUtils masterConfigUtils) {
        super(repository);
        desktopQbdRequestDORepository = repository;
        this.mongoTemplate = mongoTemplate;
        this.masterConfigUtils = masterConfigUtils;
    }

    @Override
    public QBDRequest findOneUnfinishedQuery() {
        return desktopQbdRequestDORepository.findByQbdRequestStatus(QBDRequestStatus.PROCESSING).get(0);
    }

    @Override
    public List<QBDRequest> findPreferenceQueries() {
        return desktopQbdRequestDORepository.findByQbdRequestStatus(QBDRequestStatus.PROCESSING);
    }

    @Override
    public QBDRequest findPreferenceQuery() {
        return desktopQbdRequestDORepository.findOneByQbdRequestStatus(QBDRequestStatus.PROCESSING);
    }

    @Override
    public List<HoursWorked> findFailedHoursWorked(List<UUID> hoursWorkedIds) {
        try {
            MultiTenantMongoDbFactory.setDatabaseNameForCurrentThread(masterConfigUtils.getDBForDomain(CompanyHolder.getCompanyName()));
            Aggregation aggregation = newAggregation(match(Criteria.where("hoursWorked._id").in(hoursWorkedIds)
                            .andOperator(Criteria.where("qbdRequestStatus").is("COMPLETE"))),
                    project(Fields.fields("hoursWorked")));
            return mongoTemplate.aggregate(aggregation, "desktopQueryRequests", ResultMapping.class)
                    .getMappedResults()
                    .stream()
                    .map(ResultMapping::getHoursWorked)
                    .collect(Collectors.toList());
        } catch (CompanyNotFoundException e) {
            throw new RuntimeException("Unable to access database");
        } finally {
            MultiTenantMongoDbFactory.clearDatabaseNameForCurrentThread();
        }
    }
}
