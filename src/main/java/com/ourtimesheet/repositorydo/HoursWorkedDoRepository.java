package com.ourtimesheet.repositorydo;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.timesheet.hoursWorked.HoursWorked;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * Created by Abdus Salam on 6/7/2016.
 */
public interface HoursWorkedDoRepository extends MongoRepository<HoursWorked, UUID> {

    @Query(value = "{$and:[{ 'employee.$id' : ?0 },{$and:[{'workedDate' : { '$gte' : ?1}},{'workedDate' : { '$lte' : ?2}}]},{'revisionNumber' :?3}]}")
    List<HoursWorked> findEmployeeHoursWorkedBetweenTwoDatesInclusive(UUID employeeId, OurDateTime startDate, OurDateTime endDate, int revisionNumber);
}
