package com.hourtimesheet.handler;

import com.hourtimesheet.callBack.HourExportProcessingJobCallBack;
import com.hourtimesheet.callBack.HoursExportFailureJobCallBack;
import com.hourtimesheet.callBack.HoursExportSuccessJobCallBack;
import com.ourtimesheet.job.Job;
import com.ourtimesheet.job.JobStatus;
import com.ourtimesheet.multitenant.CompanyHolder;
import com.ourtimesheet.notification.domain.Notification;
import com.ourtimesheet.notification.service.NotificationService;
import com.ourtimesheet.qbd.domain.exportJob.TimesheetExportJob;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Click Chain on 3/7/2017.
 */
public class HoursExportExpert {

    private final HoursExportSuccessJobCallBack hoursExportSuccessJobCallBack;
    private final HoursExportFailureJobCallBack hoursExportFailureJobCallBack;
    private final HourExportProcessingJobCallBack hourExportProcessingJobCallBack;

    private final NotificationService notificationService;

    public HoursExportExpert(HoursExportSuccessJobCallBack hoursExportSuccessJobCallBack, HoursExportFailureJobCallBack hoursExportFailureJobCallBack, HourExportProcessingJobCallBack hourExportProcessingJobCallBack, NotificationService notificationService) {
        this.hoursExportSuccessJobCallBack = hoursExportSuccessJobCallBack;
        this.hoursExportFailureJobCallBack = hoursExportFailureJobCallBack;
        this.hourExportProcessingJobCallBack = hourExportProcessingJobCallBack;
        this.notificationService = notificationService;
    }

    public void handleJob(JobStatus jobStatus, Job job) {
        List<Notification> notifications = new ArrayList<>();
        if (jobStatus.equals(JobStatus.FAILURE))
            notifications.addAll(hoursExportFailureJobCallBack.updateTimesheetsStatusAndSendNotifications(job.onError(), job.getEmployee(), ((List<TimesheetExportJob>) job.getJobData()), job.isProcessTimesheet()));
        else if (jobStatus.equals(JobStatus.SUCCESS))
            notifications.addAll(hoursExportSuccessJobCallBack.updateTimesheetsStatusAndSendNotifications(job.onSuccess(), job.getEmployee(), ((List<TimesheetExportJob>) job.getJobData()), job.isProcessTimesheet()));
        else
            hourExportProcessingJobCallBack.updateTimesheetStatus((List<TimesheetExportJob>) job.getJobData(), job.getId(), job.isProcessTimesheet());
        if (notifications.size() > 0) {
            notificationService.sendAsync(notifications, CompanyHolder.getCompanyName());
        }
    }
}
