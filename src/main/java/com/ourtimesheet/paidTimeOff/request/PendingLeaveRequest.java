package com.ourtimesheet.paidTimeOff.request;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.timesheet.signature.EmployeeSignature;
import com.ourtimesheet.timesheet.signature.Signature;
import com.ourtimesheet.timesheet.signature.SupervisorSignature;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Jazib on 5/15/2018.
 */
public class PendingLeaveRequest extends LeaveRequest {

    @PersistenceConstructor
    public PendingLeaveRequest(UUID id, String leaveName, OurDateTime startDate, OurDateTime endDate,
                               double hoursPerDay, boolean includeWeekend, String comments, OurDateTime submissionDate, List<Signature> signatures) {
        super(id, leaveName, startDate, endDate, hoursPerDay, includeWeekend, comments, submissionDate, signatures);
    }

    public PendingLeaveRequest(UUID id, String leaveName, OurDateTime startDate, OurDateTime endDate, double hoursPerDay,
                               boolean includeWeekend, String comments, OurDateTime submissionDate, Employee employee) {
        super(id, leaveName, startDate, endDate, hoursPerDay, includeWeekend, comments, submissionDate, new ArrayList<>());
        super.sign(new EmployeeSignature(submissionDate, employee.getId().toString(), employee.getFullNameFirstNameFirst()));
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
        return new RejectedLeaveRequest(getId(), getLeaveName(), getStartDate(), getEndDate(), getHoursPerDay(), isIncludeWeekend(), getRequestComments(), getSubmissionDate(), comments, signatures);
    }

    @Override
    public LeaveRequest cancel() {
        return new CanceledLeaveRequest(getId(), getLeaveName(), getStartDate(), getEndDate(), getHoursPerDay(), isIncludeWeekend(), getRequestComments(), getSubmissionDate(), signatures);
    }
}