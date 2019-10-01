package com.ourtimesheet.timesheet.signature;

import com.ourtimesheet.datetime.OurDateTime;

/**
 * Created by Zohaib on 8/04/2016.
 */
public class SupervisorSignature extends Signature {

    public SupervisorSignature(OurDateTime timeStamp, String userId, String userName) {
        super(timeStamp, userId, userName);
    }

    @Override
    public String getSignatureType() {
        return "Supervisor Signature";
    }
}