package com.ourtimesheet.qbd.service;

import com.ourtimesheet.paytype.PayType;
import com.ourtimesheet.qbd.domain.Customer;
import com.ourtimesheet.qbd.domain.QuickBooksClass;
import com.ourtimesheet.qbd.domain.ServiceItem;
import com.ourtimesheet.qbd.query.QBDRequest;

import java.util.Set;

/**
 * Created by Click Chain on 09/02/2017.
 */
public interface QuickBooksDesktopService {

    QBDRequest findPreferenceQuery();

    void saveImportRequest(QBDRequest qbdRequest);

    void saveCustomers(Set<Customer> customers);

    void inactiveAllCustomers();

    void inactiveAllCustomerJobs();

    void saveClasses(Set<QuickBooksClass> quickBooksClasses);

    void inactiveAllClasses();

    void saveServiceItems(Set<ServiceItem> serviceItems);

    void inactiveAllServiceItems();

    void savePayRollItems(Set<PayType> payTypes);

    void inactiveAllPayRolls();
}