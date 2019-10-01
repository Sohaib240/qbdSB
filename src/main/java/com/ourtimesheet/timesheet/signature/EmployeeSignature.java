package com.ourtimesheet.timesheet.signature;

import com.ourtimesheet.datetime.OurDateTime;

/**
 * Created by Talha on 6/23/2016.
 */
public class EmployeeSignature extends Signature {

    public EmployeeSignature(OurDateTime timeStamp, String userId, String userName) {
        super(timeStamp, userId, userName);
    }

    @Override
    public String getSignatureType() {
        return "Employee Signature";
    }
}
