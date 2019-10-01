package com.ourtimesheet.qbd.helper;

/**
 * Created by zohaib on 06/09/2016.
 */
public enum QBDRequestStatus {

    PROCESSING("In Process"),
    COMPLETE("Complete"),
    FAILURE("Failure");

    private String description;

    QBDRequestStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
