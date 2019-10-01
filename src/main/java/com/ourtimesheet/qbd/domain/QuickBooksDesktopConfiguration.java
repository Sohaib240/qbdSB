package com.ourtimesheet.qbd.domain;

import com.ourtimesheet.accounting.domain.AccountingConfiguration;
import com.ourtimesheet.accounting.domain.AccountingSystem;
import com.ourtimesheet.paytype.PayTypeConfiguration;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.UUID;

/**
 * Created by Adeel on 6/1/2016.
 */
public class QuickBooksDesktopConfiguration extends AccountingConfiguration {

    private QuickBooksDesktopImportPreferences quickBooksImportPreferences;

    private QuickBooksDesktopConnection quickBooksDesktopConnection;

    private PayTypeConfiguration payTypeConfiguration;

    @PersistenceConstructor
    public QuickBooksDesktopConfiguration(UUID id, QuickBooksDesktopImportPreferences quickBooksImportPreferences, QuickBooksDesktopConnection quickBooksDesktopConnection, PayTypeConfiguration payTypeConfiguration) {
        super(id);
        this.quickBooksImportPreferences = quickBooksImportPreferences;
        this.quickBooksDesktopConnection = quickBooksDesktopConnection;
        this.payTypeConfiguration = payTypeConfiguration;
    }

    public QuickBooksDesktopConfiguration(QuickBooksDesktopImportPreferences quickBooksImportPreferences, QuickBooksDesktopConnection quickBooksDesktopConnection, PayTypeConfiguration payTypeConfiguration) {
        super();
        this.quickBooksImportPreferences = quickBooksImportPreferences;
        this.quickBooksDesktopConnection = quickBooksDesktopConnection;
        this.payTypeConfiguration = payTypeConfiguration;
    }

    @Override
    public AccountingSystem resolve() {
        return AccountingSystem.QUICKBOOKS_DESKTOP;
    }

    public QuickBooksDesktopImportPreferences getQuickBooksImportPreferences() {
        return quickBooksImportPreferences;
    }

    public PayTypeConfiguration getPayTypeConfiguration() {
        return payTypeConfiguration;
    }
}
