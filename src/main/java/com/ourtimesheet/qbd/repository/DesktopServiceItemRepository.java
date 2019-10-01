package com.ourtimesheet.qbd.repository;


import com.ourtimesheet.qbd.domain.ServiceItem;
import com.ourtimesheet.repository.Repository;

/**
 * Created by Abdus Salam on 5/31/2016.
 */
public interface DesktopServiceItemRepository extends Repository<ServiceItem> {
    ServiceItem findByQuickBooksId(String name);
}
