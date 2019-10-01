package com.ourtimesheet.repository;

import com.ourtimesheet.job.Job;

import java.util.List;
import java.util.UUID;

/**
 * Created by Jazib on 03/03/2017.
 */
public interface JobRepository extends Repository<Job> {

    List<Job> findAllInProcessJobs();

    boolean isAlreadyProcessed(UUID id);

    boolean isTimesheetPresentInAnotherJobInProgress(UUID timesheetId, UUID jobId);
}
