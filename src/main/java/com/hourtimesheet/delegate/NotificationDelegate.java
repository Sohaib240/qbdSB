package com.hourtimesheet.delegate;

import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.notification.builder.ImportProcessCompleteNotificationBuilder;
import com.ourtimesheet.notification.domain.Notification;
import com.ourtimesheet.notification.service.NotificationService;

/**
 * Created by Muhammad Talha on 2/15/2017.
 */
public class NotificationDelegate {

    private final NotificationService notificationService;
    private final ImportProcessCompleteNotificationBuilder importProcessCompleteNotificationBuilder;

    public NotificationDelegate(NotificationService notificationService, ImportProcessCompleteNotificationBuilder importProcessCompleteNotificationBuilder) {
        this.notificationService = notificationService;
        this.importProcessCompleteNotificationBuilder = importProcessCompleteNotificationBuilder;
    }


    public void sendImportCompleteNotification(Employee employee) {
        notificationService.send(createExportNotification(employee));
    }

    private Notification createExportNotification(Employee employee) {
        return importProcessCompleteNotificationBuilder.withEmailAddress(employee.getEmailAddress()).withNotificationTemplate(employee).createNotification();
    }

}
