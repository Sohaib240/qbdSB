package com.hourtimesheet.facade;

import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.paytype.PayType;
import com.ourtimesheet.qbd.domain.Customer;
import com.ourtimesheet.qbd.domain.QuickBooksClass;
import com.ourtimesheet.qbd.domain.ServiceItem;

import java.util.List;
import java.util.Set;

/**
 * Created by Abdus Salam on 2/14/2017.
 */
public interface QBDesktopFacade {

    List<Employee> extractEmployees(String rawData);

    List<Employee> extractVendors(String rawData);

    Set<QuickBooksClass> extractClasses(String rawData);

    Set<ServiceItem> extractServiceItems(String rawData);

    Set<Customer> extractCustomers(String rawData);

    Set<PayType> extractPayRollItems(String rawData);

    void extractHoursSyncResponse(String rawData) throws Exception;
}
