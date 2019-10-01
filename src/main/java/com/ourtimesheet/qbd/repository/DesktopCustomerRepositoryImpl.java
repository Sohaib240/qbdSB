package com.ourtimesheet.qbd.repository;

import com.ourtimesheet.qbd.domain.Customer;
import com.ourtimesheet.qbd.repositorydo.DesktopCustomerDoRepository;
import com.ourtimesheet.repository.GenericRepository;

/**
 * Created by Abdus Salam on 5/30/2016.
 */
public class DesktopCustomerRepositoryImpl extends GenericRepository<Customer> implements DesktopCustomerRepository {

    private final DesktopCustomerDoRepository desktopCustomerDoRepository;

    public DesktopCustomerRepositoryImpl(DesktopCustomerDoRepository desktopCustomerDoRepository) {
        super(desktopCustomerDoRepository);
        this.desktopCustomerDoRepository = desktopCustomerDoRepository;
    }

    @Override
    public Customer findByQuickBooksId(String quickBooksId) {
        return desktopCustomerDoRepository.findByQuickBooksId(quickBooksId);
    }

    @Override
    public Customer findByName(String customerName) {
        return desktopCustomerDoRepository.findByName(customerName);
    }

}
