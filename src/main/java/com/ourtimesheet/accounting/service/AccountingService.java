package com.ourtimesheet.accounting.service;

import com.ourtimesheet.accounting.domain.AccountingConfiguration;

import java.util.List;

/**
 * Created by Adeel on 6/1/2016.
 */
public interface AccountingService {
    AccountingConfiguration findAccountingConfiguration();

    void addAccountingConfiguration(AccountingConfiguration accountingConfiguration);

    List<String> findAllChargeCodesTypes();
}