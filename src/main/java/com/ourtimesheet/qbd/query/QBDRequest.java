package com.ourtimesheet.qbd.query;

import com.ourtimesheet.common.Entity;
import com.ourtimesheet.qbd.helper.QBDRequestStatus;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by Click Chain on 06/02/2017.
 */
@Document(collection = "desktopQueryRequests")
public abstract class QBDRequest extends Entity {

    private final QBDRequestStatus qbdRequestStatus;
    private final String errorMessage;

    @PersistenceConstructor
    public QBDRequest(UUID id, QBDRequestStatus qbdRequestStatus, String errorMessage) {
        super(id);
        this.qbdRequestStatus = qbdRequestStatus;
        this.errorMessage = errorMessage;
    }

    public abstract String generateQuery();

    public abstract InputStream getRequestXmlFileInputStream() throws IOException;

    public abstract QBDRequest onSuccess();

    public abstract QBDRequest onError(String description);

    public abstract boolean isImportRequest();

    public abstract String getDescription();

    public QBDRequestStatus getQbdRequestStatus() {
        return qbdRequestStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isProcessing() {
        return qbdRequestStatus == null ? false : qbdRequestStatus.equals(QBDRequestStatus.PROCESSING);
    }

    public boolean isFailed() {
        return qbdRequestStatus == null ? false : qbdRequestStatus.equals(QBDRequestStatus.FAILURE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QBDRequest that = (QBDRequest) o;

        return qbdRequestStatus == that.qbdRequestStatus;
    }

    @Override
    public int hashCode() {
        return qbdRequestStatus.hashCode();
    }
}