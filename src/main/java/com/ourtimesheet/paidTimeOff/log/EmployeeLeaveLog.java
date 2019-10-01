package com.ourtimesheet.paidTimeOff.log;

import com.ourtimesheet.common.Entity;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document
public class EmployeeLeaveLog extends Entity {

    private final UUID employeeId;
    private final UUID leaveId;
    private final List<LeaveLog> leaveLogs;

    public EmployeeLeaveLog(UUID id, UUID employeeId, UUID leaveId, List<LeaveLog> leaveLogs) {
        super(id);
        this.employeeId = employeeId;
        this.leaveId = leaveId;
        this.leaveLogs = leaveLogs;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public UUID getLeaveId() {
        return leaveId;
    }

    public List<LeaveLog> getLeaveLogs() {
        return leaveLogs != null ? leaveLogs : new ArrayList<>();
    }
}
