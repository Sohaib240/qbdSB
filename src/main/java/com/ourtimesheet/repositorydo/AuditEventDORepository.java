package com.ourtimesheet.repositorydo;

import com.ourtimesheet.audit.AuditEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AuditEventDORepository extends MongoRepository<AuditEvent, UUID> {

    @Query(value = "{'hoursWorked._id' : {'$in' : ?0}}", delete = true)
    void deleteByHoursWorkedID(List<UUID> uuids);

}