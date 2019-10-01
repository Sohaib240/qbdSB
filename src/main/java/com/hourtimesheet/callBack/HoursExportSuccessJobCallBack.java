package com.hourtimesheet.callBack;

import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.job.Job;
import com.ourtimesheet.notification.builder.ExportProcessCompleteNotificationBuilder;
import com.ourtimesheet.notification.domain.Notification;
import com.ourtimesheet.qbd.domain.exportJob.TimesheetExportJob;
import com.ourtimesheet.repository.JobRepository;
import com.ourtimesheet.timesheet.Timesheet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Click Chain on 3/6/2017.
 */
public class HoursExportSuccessJobCallBack implements JobsCallBack<List<TimesheetExportJob>> {

    private final JobRepository jobRepository;
    private final TimesheetExportSuccessJobCallBack timesheetExportSuccessJobCallBack;
    private final TimesheetExportFailureJobCallBack timesheetExportFailureJobCallBack;
    private final ExportProcessCompleteNotificationBuilder exportProcessCompleteNotificationBuilder;

    public HoursExportSuccessJobCallBack(JobRepository jobRepository, TimesheetExportSuccessJobCallBack timesheetExportSuccessJobCallBack, TimesheetExportFailureJobCallBack timesheetExportFailureJobCallBack, ExportProcessCompleteNotificationBuilder exportProcessCompleteNotificationBuilder) {
        this.jobRepository = jobRepository;
        this.timesheetExportSuccessJobCallBack = timesheetExportSuccessJobCallBack;
        this.timesheetExportFailureJobCallBack = timesheetExportFailureJobCallBack;
        this.exportProcessCompleteNotificationBuilder = exportProcessCompleteNotificationBuilder;
    }

    @Override
    public List<Notification> updateTimesheetsStatusAndSendNotifications(Job hoursExportJob, Employee employee, List<TimesheetExportJob> timesheetExportJobList, boolean processTimesheet) {
        List<Timesheet> exportedTimesheets = new ArrayList<>();
        List<Timesheet> failedTimesheets = new ArrayList<>();
        timesheetExportJobList.forEach(timesheetExportJob -> {
            if (timesheetExportJob.hasAnyRequestFailed())
                if (!jobRepository.isTimesheetPresentInAnotherJobInProgress(timesheetExportJob.getTimesheetId(), hoursExportJob.getId()))
                    failedTimesheets.add(timesheetExportFailureJobCallBack.revertTimesheet(timesheetExportJob.getTimesheetId()));
                else
                    failedTimesheets.add(timesheetExportFailureJobCallBack.getTimesheet(timesheetExportJob.getTimesheetId()));
            else
                exportedTimesheets.add(timesheetExportSuccessJobCallBack.updateTimesheetStatus(timesheetExportJob.getTimesheetId(), timesheetExportJob.getJobData(), processTimesheet));

        });
        jobRepository.save(hoursExportJob);
        return Collections.singletonList(createNotification(exportedTimesheets.stream().filter(Objects::nonNull).collect(Collectors.toList()), employee, failedTimesheets));
    }

    private Notification createNotification(List<Timesheet> exportedTimesheets, Employee employee, List<Timesheet> failedTimesheets) {
        exportProcessCompleteNotificationBuilder.withNotificationTemplate(employee, exportedTimesheets, failedTimesheets);
        exportProcessCompleteNotificationBuilder.withEmailAddress(employee.getEmailAddress());
        return exportProcessCompleteNotificationBuilder.createNotification();
    }
}
