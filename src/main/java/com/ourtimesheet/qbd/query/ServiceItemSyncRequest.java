package com.ourtimesheet.qbd.query;

import com.ourtimesheet.qbd.factory.ChargeCodeQueryFactory;
import com.ourtimesheet.qbd.helper.QBDRequestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by Click Chain on 06/02/2017.
 */
public class ServiceItemSyncRequest extends QBDRequest {

    private static final Logger log = LoggerFactory.getLogger(ServiceItemSyncRequest.class);

    public ServiceItemSyncRequest(UUID id, QBDRequestStatus qbdRequestStatus, String errorMessage) {
        super(id, qbdRequestStatus, errorMessage);
    }

    @Override
    public String generateQuery() {
        try {
            return new ChargeCodeQueryFactory().create(getRequestXmlFileInputStream());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }


    @Override
    public InputStream getRequestXmlFileInputStream() throws IOException {
        return getClass().getResource("/query/serviceItemQuery.xml").openStream();
    }

    @Override
    public QBDRequest onSuccess() {
        return new ServiceItemSyncRequest.Builder(getId(), QBDRequestStatus.COMPLETE).build();
    }

    @Override
    public QBDRequest onError(String errorMessage) {
        return new ServiceItemSyncRequest.Builder(getId(), QBDRequestStatus.FAILURE).withErrorMessage(errorMessage).build();
    }

    @Override
    public boolean isImportRequest() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Service Item Import Request";
    }

    public static class Builder {
        private UUID id;
        private QBDRequestStatus qbdRequestStatus;
        private String errorMessage;

        public Builder(UUID id, QBDRequestStatus qbdRequestStatus) {
            this.id = id;
            this.qbdRequestStatus = qbdRequestStatus;
        }

        public Builder withErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public ServiceItemSyncRequest build() {
            return new ServiceItemSyncRequest(id, qbdRequestStatus, errorMessage);
        }
    }
}