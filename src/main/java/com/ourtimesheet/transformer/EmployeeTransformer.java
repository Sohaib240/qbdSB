package com.ourtimesheet.transformer;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.employee.EmployeeStatus;
import com.ourtimesheet.repository.EmployeeRepository;
import com.ourtimesheet.security.Role;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created by Jazib on 9/21/2016.
 */
public class EmployeeTransformer {

    private final EmployeeRepository employeeRepository;

    public EmployeeTransformer(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee transform(Employee importedEmployee, TimeZone companyTimezone) {
        try {
            Employee employeeFromDb = employeeRepository.findByEmployeeNumber(importedEmployee.getEmployeeNumber());
            return getUpdatedEmployee(employeeFromDb, importedEmployee, companyTimezone);
        } catch (Exception ex) {
            return createNewEmployee(importedEmployee, companyTimezone);
        }
    }

    private Employee createNewEmployee(Employee importedEmployee, TimeZone companyTimezone) {
        return importedEmployee.isActive() ? new Employee.Builder(createFirstName(importedEmployee), createLastName(importedEmployee))
                .withId(UUID.randomUUID())
                .withEmployeeNumber(importedEmployee.getEmployeeNumber())
                .withDisplayName(importedEmployee.getDisplayName())
                .withEmployeeRoles(Collections.singletonList(Role.EMPLOYEE))
                .withEmployeeStatus(importedEmployee.isActive())
                .withStandardAuthentication(importedEmployee.getEmailAddress(), null)
                .withEmailStatus(EmployeeStatus.PENDING_INVITE)
                .withChargeCode(new HashSet<>())
                .withPayTypes(new ArrayList<>())
                .withEmailTimestamp(null)
                .withHireDate(getHireDate(importedEmployee, companyTimezone))
                .withUserId(getUserId(importedEmployee))
                .withLeaves(new ArrayList<>())
                .withAuthorizeChargeAssociations(new ArrayList<>())
                .withEmployeeTypes(importedEmployee.getEmployeeTypes())
                .withEmployeeTimeZone(companyTimezone.getID())
                .withSyncWithAccountingSystem(false)
                .build()
                : null;
    }

    private String getUserId(Employee importedEmployee) {
        return StringUtils.isNotBlank(importedEmployee.getUserId()) ? importedEmployee.getUserId() : null;
    }

    private OurDateTime getHireDate(Employee importedEmployee, TimeZone companyTimezone) {
        return importedEmployee.getHireDate() != null ? new OurDateTime(importedEmployee.getHireDate().getDateTime(), companyTimezone) : null;
    }

    private OurDateTime getTerminationDate(Employee importedEmployee, TimeZone companyTimezone) {
        return importedEmployee.getTerminationDate() != null ? new OurDateTime(importedEmployee.getHireDate().getDateTime(), companyTimezone) : null;
    }

    private Employee getUpdatedEmployee(Employee employeeFromDb, Employee importedEmployee, TimeZone companyTimezone) {
        if (!employeeFromDb.canSyncWithAccountingSystem()) {
            return new Employee.Builder(createFirstName(importedEmployee), createLastName(importedEmployee))
                    .withId(employeeFromDb.getId())
                    .withEmployeeNumber(importedEmployee.getEmployeeNumber())
                    .withDisplayName(importedEmployee.getDisplayName())
                    .withEmployeeRoles(employeeFromDb.getRoles())
                    .withEmployeeStatus(employeeFromDb.isAdmin() ? employeeFromDb.isActive() : importedEmployee.isActive())
                    .withStandardAuthentication(StringUtils.isNotBlank(importedEmployee.getEmailAddress()) ? importedEmployee.getEmailAddress() : employeeFromDb.getEmailAddress(), getPassword(employeeFromDb, importedEmployee))
                    .withEmailStatus(getEmailStatus(employeeFromDb, importedEmployee))
                    .withChargeCode(employeeFromDb.getAssignedChargeCodes())
                    .withPayTypes(employeeFromDb.getPayTypes())
                    .withEmailTimestamp(employeeFromDb.getEmailTimestamp())
                    .withLeaves(employeeFromDb.getLeaves())
                    .withHireDate(employeeFromDb.getHireDate() != null ? employeeFromDb.getHireDate() : getHireDate(importedEmployee, companyTimezone))
                    .withUserId(StringUtils.isNotBlank(employeeFromDb.getUserId()) ? employeeFromDb.getUserId() : importedEmployee.getUserId())
                    .withCellNo(importedEmployee.getCellNo())
                    .withTerminationDate(getTerminationDate(importedEmployee, companyTimezone))
                    .withOvertimeRule(employeeFromDb.getOvertimeRule())
                    .withLeaveRequests(employeeFromDb.getLeaveRequests())
                    .withClockAuthentication(employeeFromDb.getClockAuthentications())
                    .withEmployeeTypes(employeeFromDb.getEmployeeTypes().size() > 0 ? employeeFromDb.getEmployeeTypes() : importedEmployee.getEmployeeTypes())
                    .withAuthorizeChargeAssociations(employeeFromDb.getAssociations())
                    .withEmployeeTimeZone(employeeFromDb.hasTimeZone() ? employeeFromDb.getTimeZone() : companyTimezone.getID())
                    .withSyncWithAccountingSystem(employeeFromDb.canSyncWithAccountingSystem())
                    .build();
        }
        return null;
    }

    private String getPassword(Employee employeeFromDb, Employee importedEmployee) {
        return (StringUtils.isNotBlank(employeeFromDb.getEmailAddress()) && StringUtils.isBlank(importedEmployee.getEmailAddress())) ||
                employeeFromDb.getEmailAddress().equals(importedEmployee.getEmailAddress()) ? employeeFromDb.getPassword() : "";
    }

    private EmployeeStatus getEmailStatus(Employee employeeFromDb, Employee importedEmployee) {
        return (StringUtils.isNotBlank(employeeFromDb.getEmailAddress()) && StringUtils.isBlank(importedEmployee.getEmailAddress())) ||
                employeeFromDb.getEmailAddress().equals(importedEmployee.getEmailAddress()) ? employeeFromDb.getEmployeeStatus() : EmployeeStatus.PENDING_INVITE;
    }

    private String createFirstName(Employee employee) {
        return StringUtils.isBlank(employee.getFirstName()) ? StringUtils.capitalize(employee.getDisplayName().split(" ")[0]) : StringUtils.capitalize(employee.getFirstName());
    }

    private String createLastName(Employee employee) {
        return StringUtils.isBlank(employee.getLastName()) ? StringUtils.capitalize(StringUtils.substringAfter(employee.getDisplayName(), " ")) : StringUtils.capitalize(employee.getLastName());
    }
}