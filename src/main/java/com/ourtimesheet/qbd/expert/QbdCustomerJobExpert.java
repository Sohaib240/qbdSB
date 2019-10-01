package com.ourtimesheet.qbd.expert;

import com.ourtimesheet.qbd.domain.CustomerJob;
import com.ourtimesheet.qbd.repository.DesktopCustomerJobRepository;

import java.util.Set;
import java.util.UUID;

/**
 * Created by Click Chain on 15/02/2017.
 */
public class QbdCustomerJobExpert {

    private final DesktopCustomerJobRepository customerJobRepository;

    public QbdCustomerJobExpert(DesktopCustomerJobRepository customerJobRepository) {
        this.customerJobRepository = customerJobRepository;
    }

    public void saveCustomerJobs(Set<CustomerJob> customerJobList) {
        customerJobRepository.saveAll(customerJobList);
    }


    public CustomerJob findByQuickBooksId(String quickBooksId) {
        return customerJobRepository.findByQuickBooksId(quickBooksId);
    }

    public Iterable<CustomerJob> findAll() {
        return customerJobRepository.findAll();
    }

    public CustomerJob findOne(UUID id) {
        return customerJobRepository.findById(id).get();
    }

}
