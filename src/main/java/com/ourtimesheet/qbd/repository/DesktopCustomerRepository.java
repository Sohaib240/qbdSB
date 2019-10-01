package com.ourtimesheet.qbd.repository;

import com.ourtimesheet.qbd.domain.Customer;
import com.ourtimesheet.repository.Repository;

/**
 * Created by Abdus Salam on 5/30/2016.
 */
public interface DesktopCustomerRepository extends Repository<Customer> {
    Customer findByQuickBooksId(String customerNumber);

    Customer findByName(String customerName);
}
