package com.ourtimesheet.employee;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by zohaib on 05/09/2016.
 */
public enum EmployeeStatus {
  PENDING_INVITE("Pending Invite"),
  INVITATION_SENT("Invitation Sent"),
  SIGNED_UP("Signed up");

  private String description;

  EmployeeStatus(String description) {
    this.description = description;
  }

  public static EmployeeStatus getEmployeeStatus(String value) {
    for (EmployeeStatus e : EmployeeStatus.values()) {
      if (StringUtils.equals(value, e.getDescription())) {
        return e;
      }
    }
    return null;
  }

  public String getDescription() {
    return description;
  }

}
