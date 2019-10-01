package com.ourtimesheet.qbd.expert;

import com.ourtimesheet.qbd.domain.Customer;
import com.ourtimesheet.qbd.domain.CustomerJob;
import com.ourtimesheet.qbd.repository.DesktopCustomerRepository;
import com.ourtimesheet.timesheet.chargeCode.ChargeCode;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Click Chain on 15/02/2017.
 */
public class QbdCustomerExpert {
    private final DesktopCustomerRepository customerRepository;
    private final QbdCustomerJobExpert customerJobExpert;

    public QbdCustomerExpert(DesktopCustomerRepository customerRepository, QbdCustomerJobExpert customerJobExpert) {
        this.customerRepository = customerRepository;
        this.customerJobExpert = customerJobExpert;
    }

    public void saveCustomers(Set<Customer> importedCustomers) {
        importedCustomers.forEach(importedCustomer -> {
            Set<CustomerJob> customerJobs = getCustomerJobs(importedCustomer.children(), new HashSet<>());
            Set<CustomerJob> updatedCustomerJobs = getUpdatedCustomerJobs(customerJobs);
            customerJobExpert.saveCustomerJobs(updatedCustomerJobs);
            customerRepository.save(updateCustomer(importedCustomer, updatedCustomerJobs));
        });
    }

    private Set<CustomerJob> getUpdatedCustomerJobs(Set<CustomerJob> customerJobs) {
        Set<CustomerJob> updatedCustomerJobs = new HashSet<>();
        for (CustomerJob customerJob : customerJobs) {
            try {
                CustomerJob parentCustomerJob = customerJobs.stream().filter(customerJob1 -> customerJob1.getQuickBooksId().equals(customerJob.getParentId())).findFirst().get();
                parentCustomerJob.addChild(customerJob);
                updatedCustomerJobs.add(parentCustomerJob);
                updatedCustomerJobs.add(customerJob);
            } catch (Exception ex) {
                updatedCustomerJobs.add(customerJob);
            }
        }
        return updatedCustomerJobs;
    }


    private Set<CustomerJob> getCustomerJobs(Set<ChargeCode> children, HashSet<CustomerJob> customerJobs) {
        if (children.isEmpty()) {
            return customerJobs;
        } else {
            for (ChargeCode importedCustomerJob : children) {
                CustomerJob customerJobFromDb = customerJobExpert.findByQuickBooksId(((CustomerJob) importedCustomerJob).getQuickBooksId());
                customerJobs.add(new CustomerJob(customerJobFromDb != null ? customerJobFromDb.getId() : importedCustomerJob.getId(), importedCustomerJob.getName(), ((CustomerJob) importedCustomerJob).getQuickBooksId(), importedCustomerJob.isActive(), new HashSet<>(), importedCustomerJob.getHierarchicalName(), ((CustomerJob) importedCustomerJob).getParentId()));
            }
            Set<ChargeCode> subJobs = new HashSet<>();
            children.stream().forEach(chargeCode -> subJobs.addAll(chargeCode.children()));
            return getCustomerJobs(subJobs, customerJobs);
        }
    }

    private Customer updateCustomer(Customer importedCustomer, Set<CustomerJob> updatedCustomerJobs) {
        Customer customerFromDb = customerRepository.findByQuickBooksId(importedCustomer.getQuickBooksId());
        Set<CustomerJob> customerJobs = new HashSet<>();
        for (ChargeCode job : importedCustomer.children()) {
            for (CustomerJob customerJob : updatedCustomerJobs) {
                if (((CustomerJob) job).getQuickBooksId().equals(customerJob.getQuickBooksId())) {
                    customerJobs.add(customerJob);
                }
            }
        }
        return new Customer(customerFromDb != null ? customerFromDb.getId() : importedCustomer.getId(), importedCustomer.getName(), importedCustomer.isActive(), importedCustomer.getQuickBooksId(), customerJobs);
    }

    public Iterable<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(UUID id) {
        return customerRepository.findById(id).get();
    }

    public CustomerJob findCustomerJobById(UUID id) {
        return customerJobExpert.findOne(id);
    }

    public ChargeCode findByName(String customerName) {
        return customerRepository.findByName(customerName);
    }
}