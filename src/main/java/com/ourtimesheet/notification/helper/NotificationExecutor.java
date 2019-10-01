package com.ourtimesheet.notification.helper;

import com.ourtimesheet.notification.domain.MailStatus;
import com.ourtimesheet.notification.domain.Notification;

/**
 * Created by Abdus Salam on 9/6/2016.
 */
public class NotificationExecutor implements Runnable {
    private final EmailSender emailSender;
    private final Notification notification;
    private String companyName;

    public NotificationExecutor(EmailSender emailSender, Notification notification, String companyName) {
        this.emailSender = emailSender;
        this.notification = notification;
        this.companyName = companyName;
    }

    public NotificationExecutor(EmailSender emailSender, Notification notification) {
        this.emailSender = emailSender;
        this.notification = notification;
        this.companyName = notification.getCompanyName();
    }

    @Override
    public void run() {
        MailStatus mailStatus = emailSender.sendMail(notification);
        notification.processPostNotification(mailStatus, companyName);
    }
}
