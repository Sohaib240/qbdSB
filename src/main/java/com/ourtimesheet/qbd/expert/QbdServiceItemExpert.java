package com.ourtimesheet.qbd.expert;

import com.ourtimesheet.qbd.domain.ServiceItem;
import com.ourtimesheet.qbd.repository.DesktopServiceItemRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Click Chain on 15/02/2017.
 */
public class QbdServiceItemExpert {

    private final DesktopServiceItemRepository serviceItemRepository;

    public QbdServiceItemExpert(DesktopServiceItemRepository serviceItemRepository) {
        this.serviceItemRepository = serviceItemRepository;
    }

    public void saveServiceItems(Set<ServiceItem> serviceItems) {
        Set<ServiceItem> updatedServiceItems = new HashSet<>();
        serviceItems.forEach(importedServiceItem -> {
            ServiceItem serviceItemFromDb = serviceItemRepository.findByQuickBooksId(importedServiceItem.getQuickBooksId());
            updatedServiceItems.add(new ServiceItem(serviceItemFromDb != null ? serviceItemFromDb.getId() : importedServiceItem.getId(),
                    importedServiceItem.getName(), importedServiceItem.isActive(), importedServiceItem.getQuickBooksId(), importedServiceItem.getLeafName()));
        });
        serviceItemRepository.saveAll(updatedServiceItems);
    }

    public Iterable<ServiceItem> findAll() {
        return serviceItemRepository.findAll();
    }

    public ServiceItem findById(UUID id) {
        return serviceItemRepository.findById(id).get();
    }
}
