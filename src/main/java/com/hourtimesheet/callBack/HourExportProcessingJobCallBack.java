package com.hourtimesheet.callBack;

import com.ourtimesheet.qbd.domain.exportJob.TimesheetExportJob;
import com.ourtimesheet.repository.JobRepository;

import java.util.List;
import java.util.UUID;

public class HourExportProcessingJobCallBack {

    private final TimesheetExportFailureJobCallBack timesheetExportFailureJobCallBack;
    private final TimesheetExportSuccessJobCallBack timesheetExportSuccessJobCallBack;
    private final JobRepository jobRepository;

    public HourExportProcessingJobCallBack(TimesheetExportFailureJobCallBack timesheetExportFailureJobCallBack, TimesheetExportSuccessJobCallBack timesheetExportSuccessJobCallBack, JobRepository jobRepository) {
        this.timesheetExportFailureJobCallBack = timesheetExportFailureJobCallBack;
        this.timesheetExportSuccessJobCallBack = timesheetExportSuccessJobCallBack;
        this.jobRepository = jobRepository;
    }

    public void updateTimesheetStatus(List<TimesheetExportJob> timesheetExportJobList, UUID jobId, boolean processTimesheet) {
        timesheetExportJobList.forEach(timesheetExportJob -> {
            if (!jobRepository.isTimesheetPresentInAnotherJobInProgress(timesheetExportJob.getTimesheetId(), jobId)) {
                if (!timesheetExportJob.hasAnyRequestInProcessing()) {
                    if (timesheetExportJob.hasAnyRequestFailed())
                        timesheetExportFailureJobCallBack.revertTimesheet(timesheetExportJob.getTimesheetId());
                    else
                        timesheetExportSuccessJobCallBack.updateTimesheetStatus(timesheetExportJob.getTimesheetId(), timesheetExportJob.getJobData(),processTimesheet);
                }
            }
        });
    }
}
