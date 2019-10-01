package com.ourtimesheet.notification.domain;

import com.ourtimesheet.notification.NotificationType;
import com.ourtimesheet.notification.callBack.NotificationCallback;

/**
 * Created by Abdus Salam on 9/16/2016.
 */
public class Notification {

    private final String emailAddress;
    private final NotificationTemplate notificationTemplate;
    private final NotificationCallback notificationCallback;
    private final String companyName;
    private final NotificationType notificationType;

    public Notification(String emailAddress, NotificationTemplate notificationTemplate, NotificationCallback notificationCallback) {
        this.emailAddress = emailAddress;
        this.notificationTemplate = notificationTemplate;
        this.notificationCallback = notificationCallback;
        this.companyName = null;
        this.notificationType = null;
    }

    public Notification(String emailAddress, NotificationTemplate notificationTemplate, NotificationCallback notificationCallback, String companyName) {
        this.emailAddress = emailAddress;
        this.notificationTemplate = notificationTemplate;
        this.notificationCallback = notificationCallback;
        this.companyName = companyName;
        this.notificationType = null;
    }

    public Notification(String emailAddress, NotificationTemplate notificationTemplate, NotificationCallback notificationCallback, String companyName, NotificationType notificationType) {
        this.emailAddress = emailAddress;
        this.notificationTemplate = notificationTemplate;
        this.notificationCallback = notificationCallback;
        this.companyName = companyName;
        this.notificationType = notificationType;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public NotificationTemplate getNotificationTemplate() {
        return notificationTemplate;
    }

    public void processPostNotification(MailStatus mailStatus, String... companyName) {
        if (notificationCallback != null) {
            notificationCallback.callback(mailStatus, emailAddress, companyName[0], notificationType);
        }
    }

    public void processPostNotification(MailStatus mailStatus) {
        if (notificationCallback != null) {
            notificationCallback.callback(mailStatus, emailAddress, this.companyName, notificationType);
        }
    }
}
