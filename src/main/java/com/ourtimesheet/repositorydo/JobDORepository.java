package com.ourtimesheet.repositorydo;

import com.ourtimesheet.job.Job;
import com.ourtimesheet.job.JobStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by Jazib on 03/03/2017.
 */
public interface JobDORepository extends MongoRepository<Job, UUID> {

    List<Job> findByJobStatus(JobStatus processing);
}
