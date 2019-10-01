package com.ourtimesheet.paidTimeOff;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.paidTimeOff.log.EmployeeLeaveLog;
import com.ourtimesheet.paidTimeOff.log.LeaveLog;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

/**
 * Created by UMAR BHATTI on 5/23/2017.
 */
public class EmployeeLeave {

    @DBRef
    private final Leave leave;
    @DBRef
    private LeavePolicy leavePolicy;
    @DBRef
    private EmployeeLeaveLog employeeLeaveLog;
    private OurDateTime startDate;
    private OurDateTime endDate;
    private Balance balance;
    private List<LeaveLog> leaveLogs;
    private boolean accrualIndicator = false;
    @Transient
    private boolean isUpdated = false;

    @PersistenceConstructor
    private EmployeeLeave(Leave leave, OurDateTime startDate, OurDateTime endDate, LeavePolicy leavePolicy, List<LeaveLog> leaveLogs) {
        this.leave = leave;
        this.leavePolicy = leavePolicy;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveLogs = leaveLogs;
    }
}