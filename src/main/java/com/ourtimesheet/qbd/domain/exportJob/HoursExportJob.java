package com.ourtimesheet.qbd.domain.exportJob;

import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.job.Job;
import com.ourtimesheet.job.JobStatus;
import org.joda.time.DateTime;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Created by Abdus Salam on 3/2/2017.
 */
public class HoursExportJob extends Job<TimesheetExportJob> {

    private final List<TimesheetExportJob> timesheetExportJobList;

    @PersistenceConstructor
    public HoursExportJob(DateTime timestamp, JobStatus jobStatus, List<TimesheetExportJob> timesheetExportJobList, Employee employee, boolean processTimesheet) {
        super(timestamp, jobStatus, employee, processTimesheet);
        this.timesheetExportJobList = timesheetExportJobList;
    }

    private HoursExportJob(UUID uuid, DateTime timestamp, JobStatus jobStatus, List<TimesheetExportJob> timesheetExportJobList, Employee employee, boolean processTimesheet) {
        super(uuid, timestamp, jobStatus, employee, processTimesheet);
        this.timesheetExportJobList = timesheetExportJobList;
    }

    @Override
    public JobStatus run() {
        return isAlive() ? isExpired() ? JobStatus.FAILURE : JobStatus.PROCESSING : JobStatus.SUCCESS;
    }

    private boolean isExpired() {
        return !timestamp.plusHours(10).isAfterNow();
    }

    @Override
    protected boolean isAlive() {
        return timesheetExportJobList.stream().anyMatch(TimesheetExportJob::isAlive);
    }

    @Override
    public List<TimesheetExportJob> getJobData() {
        return timesheetExportJobList;
    }

    @Override
    public Job onError() {
        return new HoursExportJob(getId(), timestamp, JobStatus.FAILURE, timesheetExportJobList, getEmployee(), isProcessTimesheet());
    }

    @Override
    public Job onSuccess() {
        return new HoursExportJob(getId(), timestamp, JobStatus.SUCCESS, timesheetExportJobList, getEmployee(), isProcessTimesheet());
    }

}
