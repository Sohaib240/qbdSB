package com.ourtimesheet.paidTimeOff.request;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.timesheet.signature.Signature;
import com.ourtimesheet.timesheet.signature.SupervisorSignature;

import java.util.List;
import java.util.UUID;

/**
 * Created by umars on 5/18/2018.
 */
public class ApprovedLeaveRequest extends LeaveRequest {

    protected ApprovedLeaveRequest(UUID id, String leaveName, OurDateTime startDate,
                                   OurDateTime endDate, double hoursPerDay, boolean includeWeekend, String comments,
                                   OurDateTime submissionDate, List<Signature> signatures) {
        super(id, leaveName, startDate, endDate, hoursPerDay, includeWeekend, comments, submissionDate, signatures);
    }

    @Override
    public LeaveRequest approve(Employee employee, OurDateTime dateTime) {
        LeaveRequest leaveRequest = new ApprovedLeaveRequest(getId(), getLeaveName(), getStartDate(), getEndDate(), getHoursPerDay(), isIncludeWeekend(), getRequestComments(), getSubmissionDate(), signatures);
        leaveRequest.sign(new SupervisorSignature(dateTime, employee.getId().toString(), employee.getFullNameFirstNameFirst()));
        return leaveRequest;
    }

    @Override
    public LeaveRequest reject(String comments) {
        super.unsign();
        return new RejectedLeaveRequest(getId(), getLeaveName(), getStartDate(), getEndDate(),
                getHoursPerDay(), isIncludeWeekend(), getRequestComments(), getSubmissionDate(), comments, signatures);
    }

    @Override
    public LeaveRequest cancel() {
        return new CanceledLeaveRequest(getId(), getLeaveName(), getStartDate(), getEndDate(), getHoursPerDay(), isIncludeWeekend(), getRequestComments(), getSubmissionDate(), signatures);
    }
}
