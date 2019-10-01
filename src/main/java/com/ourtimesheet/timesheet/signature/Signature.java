package com.ourtimesheet.timesheet.signature;

import com.ourtimesheet.datetime.OurDateTime;
import org.springframework.data.annotation.PersistenceConstructor;

/**
 * Created by Talha on 6/23/2016.
 */
public abstract class Signature {

    private final OurDateTime timeStamp;
    private final String userId;
    private final String userName;

    @PersistenceConstructor
    protected Signature(OurDateTime timeStamp, String userId, String userName) {
        this.timeStamp = timeStamp;
        this.userId = userId;
        this.userName = userName;
    }

    public OurDateTime getTimeStamp() {
        return timeStamp;
    }

    public abstract String getSignatureType();

    public String getUserName() {
        return this.userName;
    }

    public String getUserId() {
        return userId;
    }
}