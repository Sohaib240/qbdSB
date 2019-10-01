package com.ourtimesheet.qbd.repository;

import com.ourtimesheet.qbd.domain.ServiceItem;
import com.ourtimesheet.qbd.repositorydo.DesktopServiceItemDORepository;
import com.ourtimesheet.repository.GenericRepository;

/**
 * Created by Abdus Salam on 5/31/2016.
 */
public class DesktopServiceItemRepositoryImpl extends GenericRepository<ServiceItem> implements DesktopServiceItemRepository {

    private final DesktopServiceItemDORepository desktopServiceItemDORepository;

    public DesktopServiceItemRepositoryImpl(DesktopServiceItemDORepository desktopServiceItemDORepository) {
        super(desktopServiceItemDORepository);
        this.desktopServiceItemDORepository = desktopServiceItemDORepository;
    }

    @Override
    public ServiceItem findByQuickBooksId(String name) {
        return desktopServiceItemDORepository.findByQuickBooksId(name);
    }
}
