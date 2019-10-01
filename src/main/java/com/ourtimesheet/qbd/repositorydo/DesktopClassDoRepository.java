package com.ourtimesheet.qbd.repositorydo;

import com.ourtimesheet.qbd.domain.QuickBooksClass;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

/**
 * Created by Jazib on 31/05/2016.
 */
public interface DesktopClassDoRepository extends MongoRepository<QuickBooksClass, UUID> {

    QuickBooksClass findByQuickBooksId(String quickBooksId);
}
