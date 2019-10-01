package com.hourtimesheet.callBack;

import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.job.Job;
import com.ourtimesheet.notification.domain.Notification;
import com.ourtimesheet.qbd.domain.exportJob.TimesheetExportJob;

import java.util.List;

/**
 * Created by Click Chain on 3/6/2017.
 */
public interface JobsCallBack<T> {
    List<Notification> updateTimesheetsStatusAndSendNotifications(Job hoursExportJob, Employee employee, List<TimesheetExportJob> timesheetExportJobList, boolean processTimesheet);
}
