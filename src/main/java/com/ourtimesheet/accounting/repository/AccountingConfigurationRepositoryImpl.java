package com.ourtimesheet.accounting.repository;

import com.ourtimesheet.accounting.domain.AccountingConfiguration;
import com.ourtimesheet.accounting.repositorydo.AccountingConfigurationDORepository;
import com.ourtimesheet.repository.GenericRepository;

/**
 * Created by Abdus Salam on 6/1/2016.
 */
public class AccountingConfigurationRepositoryImpl extends GenericRepository<AccountingConfiguration> implements AccountingConfigurationRepository {

  public AccountingConfigurationRepositoryImpl(AccountingConfigurationDORepository repository) {
    super(repository);
  }


}
