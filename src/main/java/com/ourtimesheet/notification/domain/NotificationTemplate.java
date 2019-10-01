package com.ourtimesheet.notification.domain;

import java.util.Map;

/**
 * Created by Abdus Salam on 9/16/2016.
 */
public class NotificationTemplate {

  private final Map<String, Object> propertyValueMap;
  private final String subjectViewName;
  private final String bodyViewName;

  public NotificationTemplate(String bodyViewName, String subjectViewName, Map<String, Object> propertyValueMap) {
    this.bodyViewName = bodyViewName;
    this.subjectViewName = subjectViewName;
    this.propertyValueMap = propertyValueMap;
  }

  public String getBodyViewName() {
    return bodyViewName;
  }

  public String getSubjectViewName() {
    return subjectViewName;
  }

  public Map<String, Object> getPropertyValueMap() {
    return propertyValueMap;
  }
}
