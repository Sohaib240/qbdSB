package com.ourtimesheet.paidTimeOff.request;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.timesheet.signature.Signature;

import java.util.List;
import java.util.UUID;

/**
 * Created by umars on 5/19/2018.
 */
public class RejectedLeaveRequest extends LeaveRequest {
    private final String rejectionComment;

    protected RejectedLeaveRequest(UUID id, String leaveName, OurDateTime startDate, OurDateTime endDate, double hoursPerDay,
                                   boolean includeWeekend, String comments, OurDateTime submissionDate, String rejectionComment, List<Signature> signatures) {
        super(id, leaveName, startDate, endDate, hoursPerDay, includeWeekend, comments, submissionDate, signatures);
        this.rejectionComment = rejectionComment;
    }

    @Override
    public LeaveRequest approve(Employee employee, OurDateTime dateTime) {
        throw new IllegalArgumentException("Rejected leave cannot be approved");
    }

    @Override
    public LeaveRequest reject(String comments) {
        throw new IllegalArgumentException("Rejected leave cannot be rejected again");
    }


    @Override
    public LeaveRequest cancel() {
        return new CanceledLeaveRequest(getId(), getLeaveName(), getStartDate(), getEndDate(), getHoursPerDay(), isIncludeWeekend(), getRequestComments(), getSubmissionDate(), signatures);
    }

    public String getRejectionComment() {
        return rejectionComment;
    }
}