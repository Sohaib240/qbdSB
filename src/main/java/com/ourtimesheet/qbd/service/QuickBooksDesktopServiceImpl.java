package com.ourtimesheet.qbd.service;

import com.ourtimesheet.paytype.PayType;
import com.ourtimesheet.qbd.domain.Customer;
import com.ourtimesheet.qbd.domain.CustomerJob;
import com.ourtimesheet.qbd.domain.QuickBooksClass;
import com.ourtimesheet.qbd.domain.ServiceItem;
import com.ourtimesheet.qbd.expert.*;
import com.ourtimesheet.qbd.query.QBDRequest;
import com.ourtimesheet.qbd.repository.QBDRequestRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Click Chain on 09/02/2017.
 */
public class QuickBooksDesktopServiceImpl implements QuickBooksDesktopService {

    private final QBDRequestRepository qbdRequestRepository;

    private final QbdCustomerExpert qbdCustomerExpert;

    private final QbdCustomerJobExpert qbdCustomerJobExpert;

    private final QbdClassExpert qbdClassExpert;

    private final QbdServiceItemExpert qbdServiceItemExpert;

    private final QbdPayTypeExpert qbdPayTypeExpert;

    public QuickBooksDesktopServiceImpl(QBDRequestRepository qbdRequestRepository, QbdCustomerExpert qbdCustomerExpert, QbdCustomerJobExpert qbdCustomerJobExpert, QbdClassExpert qbdClassExpert, QbdServiceItemExpert qbdServiceItemExpert, QbdPayTypeExpert qbdPayTypeExpert) {
        this.qbdRequestRepository = qbdRequestRepository;
        this.qbdCustomerExpert = qbdCustomerExpert;
        this.qbdCustomerJobExpert = qbdCustomerJobExpert;
        this.qbdClassExpert = qbdClassExpert;
        this.qbdServiceItemExpert = qbdServiceItemExpert;
        this.qbdPayTypeExpert = qbdPayTypeExpert;
    }

    @Override
    public QBDRequest findPreferenceQuery() {
        return qbdRequestRepository.findPreferenceQuery();
    }

    @Override
    public void saveImportRequest(QBDRequest qbdRequest) {
        qbdRequestRepository.save(qbdRequest);
    }

    @Override
    public void saveCustomers(Set<Customer> customers) {
        qbdCustomerExpert.saveCustomers(customers);
    }

    @Override
    public void saveClasses(Set<QuickBooksClass> quickBooksClasses) {
        qbdClassExpert.saveAll(quickBooksClasses);
    }

    @Override
    public void saveServiceItems(Set<ServiceItem> serviceItems) {
        qbdServiceItemExpert.saveServiceItems(serviceItems);
    }

    @Override
    public void savePayRollItems(Set<PayType> payTypes) {
        qbdPayTypeExpert.saveAll(payTypes);
    }

    @Override
    public void inactiveAllCustomers() {
        Set<Customer> inactiveCustomers = new HashSet<>();
        for (Customer customer : qbdCustomerExpert.findAll()) {
            customer.setActiveStatus(false);
            inactiveCustomers.add(customer);
        }
        qbdCustomerExpert.saveCustomers(inactiveCustomers);
    }

    @Override
    public void inactiveAllCustomerJobs() {
        Set<CustomerJob> inactiveCustomerJobs = new HashSet<>();
        for (CustomerJob customerJob : qbdCustomerJobExpert.findAll()) {
            customerJob.setActiveStatus(false);
            inactiveCustomerJobs.add(customerJob);
        }
        qbdCustomerJobExpert.saveCustomerJobs(inactiveCustomerJobs);
    }

    @Override
    public void inactiveAllClasses() {
        Set<QuickBooksClass> inactiveQuickBooksClasses = new HashSet<>();
        for (QuickBooksClass quickBooksClass : qbdClassExpert.findAll()) {
            quickBooksClass.setActiveStatus(false);
            inactiveQuickBooksClasses.add(quickBooksClass);
        }
        qbdClassExpert.saveAll(inactiveQuickBooksClasses);
    }

    @Override
    public void inactiveAllServiceItems() {
        Set<ServiceItem> inactiveServiceItems = new HashSet<>();
        for (ServiceItem serviceItem : qbdServiceItemExpert.findAll()) {
            serviceItem.setActiveStatus(false);
            inactiveServiceItems.add(serviceItem);
        }
        qbdServiceItemExpert.saveServiceItems(inactiveServiceItems);
    }

    @Override
    public void inactiveAllPayRolls() {
        Set<PayType> inactivePayTypes = new HashSet<>();
        for (PayType payType : qbdPayTypeExpert.findAll()) {
            payType.setActiveStatus(false);
            inactivePayTypes.add(payType);
        }
        qbdPayTypeExpert.saveAll(inactivePayTypes);
    }
}
