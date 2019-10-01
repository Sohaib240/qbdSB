package com.ourtimesheet.accounting.domain;

/**
 * Created by Zohaib on 02/09/2017.
 */
public class AccountingImportPreferences {
    private boolean customersAndJobs;
    private boolean assignToAll;
    private boolean serviceItem;
    private boolean qbClass;
    private boolean vendorsAsEmployee;

    public AccountingImportPreferences(boolean customersAndJobs, boolean assignToAll,
                                       boolean serviceItem, boolean qbClass, boolean vendorsAsEmployee) {
        this.customersAndJobs = customersAndJobs;
        this.assignToAll = assignToAll;
        this.serviceItem = serviceItem;
        this.qbClass = qbClass;
        this.vendorsAsEmployee = vendorsAsEmployee;
    }
}
