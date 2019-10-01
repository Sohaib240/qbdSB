package com.ourtimesheet.notification.service;

import com.ourtimesheet.notification.domain.Notification;
import com.ourtimesheet.notification.domain.NotificationEvent;
import com.ourtimesheet.notification.factory.EmbeddedMailUrlFactory;
import com.ourtimesheet.notification.helper.EmailSender;
import com.ourtimesheet.notification.helper.NotificationExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Abdus Salam on 9/2/2016.
 */
public class NotificationServiceImpl implements NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);
    private final ThreadPoolExecutor threadPoolExecutor;
    private final EmailSender emailSender;
    private final EmbeddedMailUrlFactory embeddedMailUrlFactory;
    private final String resourcesUrl;

    public NotificationServiceImpl(ThreadPoolTaskExecutor threadPoolTaskExecutor, EmailSender emailSender, EmbeddedMailUrlFactory embeddedMailUrlFactory, String resourcesUrl) {
        this.embeddedMailUrlFactory = embeddedMailUrlFactory;
        this.threadPoolExecutor = threadPoolTaskExecutor.getThreadPoolExecutor();
        this.emailSender = emailSender;
        this.resourcesUrl = resourcesUrl;
    }

    @Override
    @Async
    public void sendAsync(List<Notification> notifications, String companyName) {
        log.debug("Sending emails using async method");
        for (Notification notification : notifications) {
            try {
                threadPoolExecutor.submit(new NotificationExecutor(emailSender, notification, companyName));
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }
    }

    @Override
    public void send(NotificationEvent notificationEvent) {
        for (Notification notification : notificationEvent.toNotifications(embeddedMailUrlFactory, resourcesUrl)) {
            try {
                threadPoolExecutor.submit(new NotificationExecutor(emailSender, notification));
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }
    }

    @Override
    public void send(Notification notification) {
        log.debug("Sending email using simple method");
        emailSender.sendMail(notification);
    }

    @Override
    public void send(List<NotificationEvent> notificationEvents) {
        for (NotificationEvent notificationEvent : notificationEvents) {
            for (Notification notification : notificationEvent.toNotifications(embeddedMailUrlFactory, resourcesUrl)) {
                try {
                    threadPoolExecutor.submit(new NotificationExecutor(emailSender, notification));
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                }
            }
        }
    }
}
