package com.ourtimesheet.notification.callBack;

import com.ourtimesheet.notification.NotificationType;
import com.ourtimesheet.notification.domain.MailStatus;

/**
 * Created by Abdus Salam on 9/16/2016.
 */
public abstract class NotificationCallback {

  public abstract void callback(MailStatus mailStatus, String address, String companyName, NotificationType notificationType);
}
