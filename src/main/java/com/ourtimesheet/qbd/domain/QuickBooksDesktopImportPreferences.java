package com.ourtimesheet.qbd.domain;

import com.ourtimesheet.accounting.domain.AccountingImportPreferences;

/**
 * Created by Abdus Salam on 1/19/2017.
 */
public class QuickBooksDesktopImportPreferences extends AccountingImportPreferences {
    private boolean payrollItem;

    public QuickBooksDesktopImportPreferences(boolean customersAndJobs, boolean assignToAll, boolean serviceItem, boolean qbClass, boolean vendorsAsEmployee, boolean payrollItem) {
        super(customersAndJobs, assignToAll, serviceItem, qbClass, vendorsAsEmployee);
        this.payrollItem = payrollItem;
    }
}
