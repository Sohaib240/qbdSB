package com.ourtimesheet.audit;

import com.google.gson.JsonObject;
import com.ourtimesheet.common.Entity;
import com.ourtimesheet.datetime.OurDateTime;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * Created by hassan on 6/12/16.
 */
@Document(collection = "auditEventLog")
public abstract class AuditEvent extends Entity {

    private final String user;

    private final OurDateTime timestamp;

    private final String ipAddress;

    private final AuditEventCategory auditEventCategory;

    @PersistenceConstructor
    public AuditEvent(UUID id, String user, OurDateTime timestamp, String ipAddress, AuditEventCategory auditEventCategory) {
        super(id);
        this.user = user;
        this.timestamp = timestamp;
        this.ipAddress = ipAddress;
        this.auditEventCategory = auditEventCategory;
    }

    public AuditEvent(String user, OurDateTime timestamp, String ipAddress, AuditEventCategory auditEventCategory) {
        super();
        this.user = user;
        this.timestamp = timestamp;
        this.ipAddress = ipAddress;
        this.auditEventCategory = auditEventCategory;
    }

    public abstract String getEventDescription();

    @Override
    public String toString() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Timestamp", timestamp.format("MM/dd/yyyy HH:mm z"));
        jsonObject.addProperty("FullTimeStamp", timestamp.getTime());
        jsonObject.addProperty("User", user);
        jsonObject.addProperty("IPAddress", ipAddress);
        jsonObject.addProperty("ActivityCategory", auditEventCategory.name());
        jsonObject.addProperty("ActivityDescription", getEventDescription());
        return jsonObject.toString();
    }

    public OurDateTime getTimestamp() {
        return timestamp;
    }

    public String getUser() {
        return user;
    }

    public String getIpAddress() {
        return ipAddress;
    }
}
