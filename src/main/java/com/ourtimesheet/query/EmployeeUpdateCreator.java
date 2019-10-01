package com.ourtimesheet.query;

import com.ourtimesheet.employee.Employee;
import org.springframework.data.mongodb.core.query.Update;

/**
 * Created by Abdus Salam on 12/7/2017.
 */
public class EmployeeUpdateCreator {

    public static Update createEmployeeUpdate(Employee employee) {
        Update update = new Update();
        update.set("standardAuthentication", employee.getStandardAuthentication());
        update.set("firstName", employee.getFirstName());
        update.set("lastName", employee.getLastName());
        update.set("employeeNumber", employee.getEmployeeNumber());
        update.set("employeeRoles", employee.getRoles());
        update.set("displayName", employee.getDisplayName());
        update.set("employeeTypes", employee.getEmployeeTypes());
        update.set("active", employee.isActive());
        update.set("employeeStatus", employee.getEmployeeStatus());
        update.set("userId", employee.getUserId());
        update.set("cellNo", employee.getCellNo());
        update.set("hireDate", employee.getHireDate());
        update.set("terminationDate", employee.getTerminationDate());
        update.set("emailTimestamp", employee.getEmailTimestamp());
        update.set("lockTime", employee.getLockTime());
        update.set("loginAttempts", employee.getLoginAttempts());
        update.set("overtimeRule", employee.getOvertimeRule());
        update.set("leaveRequests", employee.getLeaveRequests());
        update.set("clockAuthentications", employee.getClockAuthentications());
        update.set("employeeTimeZone", employee.getTimeZone());
        update.set("syncWithAccountingSystem", employee.canSyncWithAccountingSystem());
        return update;
    }
}
