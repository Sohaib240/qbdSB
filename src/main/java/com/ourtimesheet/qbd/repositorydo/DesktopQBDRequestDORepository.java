package com.ourtimesheet.qbd.repositorydo;

import com.ourtimesheet.qbd.helper.QBDRequestStatus;
import com.ourtimesheet.qbd.query.QBDRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by Abdus Salam on 5/31/2016.
 */
public interface DesktopQBDRequestDORepository extends MongoRepository<QBDRequest, UUID> {
    List<QBDRequest> findByQbdRequestStatus(QBDRequestStatus qbdRequestStatus);

    QBDRequest findOneByQbdRequestStatus(QBDRequestStatus qbdRequestStatus);
}
