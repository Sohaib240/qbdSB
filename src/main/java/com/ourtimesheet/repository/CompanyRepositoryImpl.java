package com.ourtimesheet.repository;

import com.ourtimesheet.profile.Company;
import com.ourtimesheet.repositorydo.CompanyDORepository;

/**
 * Created by Click Chain on 3/16/2016.
 */
public class CompanyRepositoryImpl extends GenericRepository<Company> implements CompanyRepository {

  public CompanyRepositoryImpl(CompanyDORepository repository) {
    super(repository);
  }
}
