package com.ourtimesheet.qbd.repository;

import com.ourtimesheet.qbd.domain.CustomerJob;
import com.ourtimesheet.qbd.repositorydo.DesktopCustomerJobDORepository;
import com.ourtimesheet.repository.GenericRepository;

/**
 * Created by Talha on 5/30/2016.
 */
public class DesktopCustomerJobRepositoryImpl extends GenericRepository<CustomerJob> implements DesktopCustomerJobRepository {

    private DesktopCustomerJobDORepository desktopCustomerJobDORepository;

    public DesktopCustomerJobRepositoryImpl(DesktopCustomerJobDORepository desktopCustomerJobDORepository) {
        super(desktopCustomerJobDORepository);
        this.desktopCustomerJobDORepository = desktopCustomerJobDORepository;
    }

    @Override
    public CustomerJob findByQuickBooksId(String quickBooksId) {
        return desktopCustomerJobDORepository.findByQuickBooksId(quickBooksId);
    }

}
