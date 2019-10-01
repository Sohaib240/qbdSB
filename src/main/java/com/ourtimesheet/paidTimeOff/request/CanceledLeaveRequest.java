package com.ourtimesheet.paidTimeOff.request;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.timesheet.signature.Signature;

import java.util.List;
import java.util.UUID;

/**
 * Created by umars on 5/19/2018.
 */
public class CanceledLeaveRequest extends LeaveRequest {

    protected CanceledLeaveRequest(UUID id, String leaveName, OurDateTime startDate, OurDateTime endDate, double hoursPerDay,
                                   boolean includeWeekend, String comments, OurDateTime submissionDate, List<Signature> signatures) {
        super(id, leaveName, startDate, endDate, hoursPerDay, includeWeekend, comments, submissionDate, signatures);
    }

    @Override
    public LeaveRequest approve(Employee employee, OurDateTime dateTime) {
        throw new IllegalArgumentException("Canceled leave cannot be approved");
    }

    @Override
    public LeaveRequest reject(String comments) {
        throw new IllegalArgumentException("Canceled leave cannot be rejected again");
    }



    @Override
    public LeaveRequest cancel() {
        throw new IllegalArgumentException("Canceled leave cannot be canceled again");
    }

}