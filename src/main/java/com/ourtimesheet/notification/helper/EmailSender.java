package com.ourtimesheet.notification.helper;

import com.ourtimesheet.notification.domain.MailStatus;
import com.ourtimesheet.notification.domain.Notification;

/**
 * Created by Abdus Salam on 9/7/2016.
 */
public interface EmailSender {

  MailStatus sendMail(Notification notification);
}
