package com.ourtimesheet.timesheet.search;

import com.ourtimesheet.datetime.OurDateTime;

import java.util.UUID;

/**
 * Created by Abdus Salam on 9/27/2016.
 */
public class HoursWorkedCriteria extends TimesheetItemSearchCriteria {

  private HoursWorkedCriteria(Builder builder) {
    super(builder.employeeId, builder.startDate, builder.endDate, builder.revisionNumber);
  }

  public static class Builder {
    private UUID employeeId;
    private OurDateTime startDate;
    private OurDateTime endDate;
    private int revisionNumber;

    public Builder(OurDateTime startDate, OurDateTime endDate) {
      this.startDate = startDate;
      this.endDate = endDate;
    }

    public Builder withEmployeeId(UUID employeeId) {
      this.employeeId = employeeId;
      return this;
    }

    public Builder withRevisionNumber(int revisionNumber) {
      this.revisionNumber = revisionNumber;
      return this;
    }

    public HoursWorkedCriteria createCriteria() {
      return new HoursWorkedCriteria(this);
    }
  }
}
