package com.ourtimesheet.service;

import com.ourtimesheet.profile.Company;
import com.ourtimesheet.repository.CompanyRepository;

/**
 * Created by Talha Zahid on 3/1/2016.
 */
public class DefaultCompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;


    public DefaultCompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;

    }


    @Override
    public Company getCompany() {
        return companyRepository.find();
    }

}