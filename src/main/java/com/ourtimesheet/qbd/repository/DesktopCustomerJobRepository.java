package com.ourtimesheet.qbd.repository;

import com.ourtimesheet.qbd.domain.CustomerJob;
import com.ourtimesheet.repository.Repository;

/**
 * Created by Talha on 5/30/2016.
 */
public interface DesktopCustomerJobRepository extends Repository<CustomerJob> {
    CustomerJob findByQuickBooksId(String customerJobNumber);
}
