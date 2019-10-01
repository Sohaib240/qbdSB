package com.ourtimesheet.qbd.repository;

import com.ourtimesheet.qbd.domain.QuickBooksClass;
import com.ourtimesheet.qbd.repositorydo.DesktopClassDoRepository;
import com.ourtimesheet.repository.GenericRepository;

/**
 * Created by Jazib on 31/05/2016.
 */
public class DesktopClassRepositoryImpl extends GenericRepository<QuickBooksClass> implements DesktopClassRepository {

    private final DesktopClassDoRepository desktopClassDoRepository;

    public DesktopClassRepositoryImpl(DesktopClassDoRepository desktopClassDoRepository) {
        super(desktopClassDoRepository);
        this.desktopClassDoRepository = desktopClassDoRepository;
    }

    @Override
    public QuickBooksClass findByQuickBooksId(String quickBooksId) {
        return desktopClassDoRepository.findByQuickBooksId(quickBooksId);
    }
}
