package com.ourtimesheet.notification.helper;

import com.ourtimesheet.notification.domain.Email;
import com.ourtimesheet.notification.domain.Notification;
import com.ourtimesheet.notification.domain.NotificationTemplate;

/**
 * Created by hassan on 12/19/16.
 */
public class AmazonEmailCreator {

  private final EmailTemplateResolver emailTemplateResolver;

  private final String fromEmail;

  public AmazonEmailCreator(EmailTemplateResolver emailTemplateResolver, String fromEmail) {
    this.emailTemplateResolver = emailTemplateResolver;
    this.fromEmail = fromEmail;
  }

  Email createEmail(Notification notification) {
    NotificationTemplate notificationTemplate = notification.getNotificationTemplate();
    String subject = emailTemplateResolver.resolve(notificationTemplate.getSubjectViewName(), notificationTemplate.getPropertyValueMap());
    String body = emailTemplateResolver.resolve(notificationTemplate.getBodyViewName(), notificationTemplate.getPropertyValueMap());
    return new Email(fromEmail, notification.getEmailAddress(), subject, body);
  }
}
