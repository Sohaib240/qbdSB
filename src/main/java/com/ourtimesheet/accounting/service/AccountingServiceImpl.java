package com.ourtimesheet.accounting.service;

import com.ourtimesheet.accounting.domain.AccountingConfiguration;
import com.ourtimesheet.accounting.repository.AccountingConfigurationRepository;

import java.util.List;

/**
 * Created by Adeel on 6/1/2016.
 */
public class AccountingServiceImpl implements AccountingService {

    private final AccountingConfigurationRepository accountingConfigurationRepository;
    private final AccountingServiceFactory accountingServiceFactory;

    public AccountingServiceImpl(AccountingServiceFactory accountingServiceFactory, AccountingConfigurationRepository accountingConfigurationRepository) {
        this.accountingConfigurationRepository = accountingConfigurationRepository;
        this.accountingServiceFactory = accountingServiceFactory;
    }


    @Override
    public AccountingConfiguration findAccountingConfiguration() {
        return accountingConfigurationRepository.find();
    }

    @Override
    public void addAccountingConfiguration(AccountingConfiguration accountingConfiguration) {
        accountingConfigurationRepository.save(accountingConfiguration);
    }

    @Override
    public List<String> findAllChargeCodesTypes() {
        AccountingExpert expert = accountingServiceFactory.create(findAccountingConfiguration());
        return expert.findAllChargeCodesTypes();
    }

}