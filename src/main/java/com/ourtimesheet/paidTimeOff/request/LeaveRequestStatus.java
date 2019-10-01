package com.ourtimesheet.paidTimeOff.request;

/**
 * Created by Jazib on 5/15/2018.
 */
public enum LeaveRequestStatus {


    APPROVE("Approved"),
    CANCELED("Canceled"),
    PENDING("Pending"),
    REJECTED("Rejected");

    private String description;

    LeaveRequestStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
