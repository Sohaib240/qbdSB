package com.ourtimesheet.qbd.repositorydo;

import com.ourtimesheet.qbd.domain.ServiceItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

/**
 * Created by Abdus Salam on 5/31/2016.
 */
public interface DesktopServiceItemDORepository extends MongoRepository<ServiceItem, UUID> {
    ServiceItem findByQuickBooksId(String name);
}
