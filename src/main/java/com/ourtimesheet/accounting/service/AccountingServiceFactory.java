package com.ourtimesheet.accounting.service;

import com.ourtimesheet.accounting.domain.AccountingSystem;
import com.ourtimesheet.accounting.domain.AccountingSystemAware;
import com.ourtimesheet.qbd.expert.QuickBooksDesktopExpert;

/**
 * Created by Adeel on 6/1/2016.
 */
public class AccountingServiceFactory {

    private final QuickBooksDesktopExpert quickBooksDesktopExpert;

    public AccountingServiceFactory(QuickBooksDesktopExpert quickBooksDesktopExpert) {
        this.quickBooksDesktopExpert = quickBooksDesktopExpert;
    }

    public AccountingExpert create(AccountingSystemAware accountingSystemAware) {
        if (accountingSystemAware.resolve().equals(AccountingSystem.QUICKBOOKS_DESKTOP)) {
            return quickBooksDesktopExpert;
        }
        throw new RuntimeException("No Accounting Expert Found.");
    }
}
