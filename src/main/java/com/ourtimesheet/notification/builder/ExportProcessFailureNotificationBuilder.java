package com.ourtimesheet.notification.builder;

import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.multitenant.CompanyHolder;
import com.ourtimesheet.notification.domain.Notification;
import com.ourtimesheet.notification.domain.NotificationTemplate;
import com.ourtimesheet.notification.factory.EmbeddedMailUrlFactory;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Muhammad Talha on 2/15/2017.
 */
public class ExportProcessFailureNotificationBuilder {

    private String emailAddress;
    private NotificationTemplate notificationTemplate;
    private EmbeddedMailUrlFactory embeddedMailUrlFactory;
    private String resourcesUrl;

    public ExportProcessFailureNotificationBuilder(EmbeddedMailUrlFactory embeddedMailUrlFactory, String resourcesUrl) {
        this.embeddedMailUrlFactory = embeddedMailUrlFactory;
        this.resourcesUrl = resourcesUrl;
    }

    public ExportProcessFailureNotificationBuilder withEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public ExportProcessFailureNotificationBuilder withNotificationTemplate(Employee employee) {
        String companyName = CompanyHolder.getCompanyName();
        String imageSource = MessageFormat.format(resourcesUrl, companyName);
        Map<String, Object> propertyValueMap = new HashMap<>();
        propertyValueMap.put("firstName", employee.getFirstName());
        propertyValueMap.put("url", embeddedMailUrlFactory.createTimesheetExportUrl(CompanyHolder.getCompanyName()));
        propertyValueMap.put("imgSrc", imageSource);
        this.notificationTemplate = new NotificationTemplate("exportProcessFailureNotificationBody.html", "exportProcessFailureNotificationSubject.html", propertyValueMap);
        return this;
    }

    public Notification createNotification() {
        return new Notification(emailAddress, notificationTemplate, null);
    }
}
