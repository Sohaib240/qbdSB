package com.ourtimesheet.qbd.repository;

import com.ourtimesheet.qbd.domain.QuickBooksClass;
import com.ourtimesheet.repository.Repository;

/**
 * Created by Jazib on 31/05/2016.
 */
public interface DesktopClassRepository extends Repository<QuickBooksClass> {
    QuickBooksClass findByQuickBooksId(String classNumber);
}
