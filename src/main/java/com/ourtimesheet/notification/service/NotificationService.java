package com.ourtimesheet.notification.service;

import com.ourtimesheet.notification.domain.Notification;
import com.ourtimesheet.notification.domain.NotificationEvent;

import java.util.List;

/**
 * Created by Abdus Salam on 9/2/2016.
 */
public interface NotificationService {

  void sendAsync(List<Notification> notifications, String CompanyName);

  void send(NotificationEvent notificationEvent);

  void send(Notification notification);

  void send(List<NotificationEvent> notificationEvents);
}
