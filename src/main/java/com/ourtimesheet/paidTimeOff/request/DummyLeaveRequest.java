package com.ourtimesheet.paidTimeOff.request;


import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.employee.Employee;

import java.util.UUID;

/**
 * Created by Abdus Salam on 5/16/2018.
 */
public class DummyLeaveRequest extends LeaveRequest {

    public DummyLeaveRequest() {
        super(UUID.randomUUID(), null, null, null, 0D, false, null, null, null);

    }

    @Override
    public LeaveRequest approve(Employee employee, OurDateTime dateTime) {
        return null;
    }

    @Override
    public LeaveRequest reject(String comments) {
        return null;
    }


    @Override
    public LeaveRequest cancel() {
        return null;
    }
}