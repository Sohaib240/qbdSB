package com.hourtimesheet.handler;

import com.ourtimesheet.job.Job;
import com.ourtimesheet.job.JobStatus;
import com.ourtimesheet.repository.JobRepository;

import java.util.List;

/**
 * Created by Jazib on 06/03/2017.
 */

public class RequestHandler {

    private final JobRepository jobRepository;
    private final HoursExportExpert hoursExportExpert;

    public RequestHandler(JobRepository jobRepository, HoursExportExpert hoursExportExpert) {
        this.jobRepository = jobRepository;
        this.hoursExportExpert = hoursExportExpert;
    }

    public void executeAllProcessingJobs() {
        List<Job> allInProcessJobs = jobRepository.findAllInProcessJobs();
        allInProcessJobs.forEach(job -> {
            JobStatus jobStatus = job.run();
            hoursExportExpert.handleJob(jobStatus, job);
        });
    }
}
