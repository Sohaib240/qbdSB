package com.hourtimesheet.callBack;

import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.job.Job;
import com.ourtimesheet.notification.builder.ExportProcessFailureNotificationBuilder;
import com.ourtimesheet.notification.domain.Notification;
import com.ourtimesheet.qbd.domain.exportJob.TimesheetExportJob;
import com.ourtimesheet.repository.JobRepository;
import com.ourtimesheet.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Click Chain on 3/6/2017.
 */
public class HoursExportFailureJobCallBack implements JobsCallBack<List<TimesheetExportJob>> {

    private final JobRepository jobRepository;
    private final TimesheetExportFailureJobCallBack timesheetExportFailureJobCallBack;
    private final ExportProcessFailureNotificationBuilder exportProcessFailureNotificationBuilder;
    private final EmployeeService employeeService;

    public HoursExportFailureJobCallBack(JobRepository jobRepository, TimesheetExportFailureJobCallBack timesheetExportFailureJobCallBack, ExportProcessFailureNotificationBuilder exportProcessFailureNotificationBuilder, EmployeeService employeeService) {
        this.jobRepository = jobRepository;
        this.timesheetExportFailureJobCallBack = timesheetExportFailureJobCallBack;
        this.exportProcessFailureNotificationBuilder = exportProcessFailureNotificationBuilder;
        this.employeeService = employeeService;
    }

    @Override
    public List<Notification> updateTimesheetsStatusAndSendNotifications(Job hoursExportJob, Employee employee, List<TimesheetExportJob> timesheetExportJobList, boolean processTimesheet) {
        List<Notification> notifications = new ArrayList<>();
        timesheetExportJobList.forEach(timesheetExportJob -> {
            if (timesheetExportJob.isAlive() || timesheetExportJob.hasAnyRequestFailed() || timesheetExportJob.hasNoHoursRequest()) {
                timesheetExportFailureJobCallBack.updateTimesheetStatus(timesheetExportJob.getTimesheetId(), timesheetExportJob.getJobData(), processTimesheet);
            }
        });
        jobRepository.save(hoursExportJob);
        notifications.add(createNotification(employee));
        employeeService.findAdmins().forEach(admin -> notifications.add(createNotification(admin)));
        return notifications;
    }

    private Notification createNotification(Employee employee) {
        exportProcessFailureNotificationBuilder.withNotificationTemplate(employee);
        exportProcessFailureNotificationBuilder.withEmailAddress(employee.getEmailAddress());
        return exportProcessFailureNotificationBuilder.createNotification();
    }
}
