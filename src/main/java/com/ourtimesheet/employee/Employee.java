package com.ourtimesheet.employee;

import com.ourtimesheet.common.Entity;
import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.employee.authentication.Authentication;
import com.ourtimesheet.employee.authentication.BioMetricAuthentication;
import com.ourtimesheet.overtimeConfiguration.OvertimeRule;
import com.ourtimesheet.paidTimeOff.EmployeeLeave;
import com.ourtimesheet.paidTimeOff.request.LeaveRequest;
import com.ourtimesheet.paytype.EmployeePayType;
import com.ourtimesheet.security.Role;
import com.ourtimesheet.security.StandardAuthentication;
import com.ourtimesheet.timesheet.chargeCode.ChargeCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Abdus Salam on 5/24/2016.
 */

public class Employee extends Entity {

    private static final Logger log = LoggerFactory.getLogger(Employee.class);
    private List<EmployeeAuthorizeChargeAssociation> authorizeChargeAssociations;
    private String firstName;
    private String lastName;
    private String employeeNumber;
    private boolean active;
    private EmployeeStatus employeeStatus;
    private List<Role> employeeRoles;
    private StandardAuthentication standardAuthentication;
    private OurDateTime emailTimestamp;
    private OurDateTime lockTime;
    private int loginAttempts;
    private String displayName;
    private Set<EmployeeChargeCode> chargeCodes;
    private List<EmployeePayType> payTypes;
    private List<EmployeeLeave> leaves;
    private String userId;
    private String cellNo;
    private OurDateTime hireDate;
    private OurDateTime terminationDate;
    private List<EmployeeType> employeeTypes;
    private boolean syncWithAccountingSystem;

    @DBRef
    private OvertimeRule overtimeRule;
    private List<LeaveRequest> leaveRequests;
    private List<Authentication> clockAuthentications;
    private String employeeTimeZone;

    @PersistenceConstructor
    protected Employee(UUID id, String firstName, String lastName, String employeeNumber, List<Role> employeeRoles, StandardAuthentication standardAuthentication,
                       boolean active, EmployeeStatus employeeStatus, String displayName, List<EmployeeLeave> leaves, String userId, String cellNo,
                       OurDateTime hireDate, OurDateTime terminationDate, List<EmployeeType> employeeTypes) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeRoles = employeeRoles;
        this.standardAuthentication = standardAuthentication;
        this.employeeNumber = employeeNumber;
        this.active = active;
        this.employeeStatus = employeeStatus;
        this.displayName = displayName;
        this.leaves = leaves;
        this.userId = userId;
        this.cellNo = cellNo;
        this.hireDate = hireDate;
        this.terminationDate = terminationDate;
        this.employeeTypes = employeeTypes;
    }

    public Employee(UUID id) {
        super(id);
    }

    public Employee(UUID id, String firstName, String lastName, String employeeNumber, List<Role> employeeRoles, StandardAuthentication standardAuthentication,
                    boolean active, EmployeeStatus employeeStatus, String displayName, List<EmployeeLeave> leaves, String userId, String cellNo,
                    OurDateTime hireDate, OurDateTime terminationDate, List<EmployeeType> employeeTypes, OurDateTime lockTime, int loginAttempts, boolean syncWithAccountingSystem, String employeeTimeZone) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeRoles = employeeRoles;
        this.standardAuthentication = standardAuthentication;
        this.employeeNumber = employeeNumber;
        this.active = active;
        this.employeeStatus = employeeStatus;
        this.displayName = displayName;
        this.leaves = leaves;
        this.userId = userId;
        this.cellNo = cellNo;
        this.hireDate = hireDate;
        this.terminationDate = terminationDate;
        this.employeeTypes = employeeTypes;
        this.lockTime = lockTime;
        this.loginAttempts = loginAttempts;
        this.syncWithAccountingSystem = syncWithAccountingSystem;
        this.employeeTimeZone = employeeTimeZone;
    }


    public String getEmailAddress() {
        return standardAuthentication.getEmailAddress();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return StringUtils.capitalize(lastName);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public boolean isActive() {
        return active;
    }

    public boolean canSyncWithAccountingSystem() {
        return syncWithAccountingSystem;
    }

    public void setActiveStatus(boolean status) {
        this.active = status;
    }

    public EmployeeStatus getEmployeeStatus() {
        return employeeStatus;
    }

    public String getEmployeeName() {
        return getLastName() + ", " + getFirstName();
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<Role> getRoles() {
        return this.employeeRoles != null ? this.employeeRoles : new ArrayList<>();
    }


    public String getTimeZone() {
        return employeeTimeZone;
    }

    public void setTimeZone(String timeZone) {
        if (StringUtils.isBlank(this.employeeTimeZone)) {
            this.employeeTimeZone = timeZone;
        }
    }

    public List<EmployeeAuthorizeChargeAssociation> getAssociations() {
        return CollectionUtils.isEmpty(authorizeChargeAssociations) ? new ArrayList<>() : authorizeChargeAssociations;
    }

    public List<EmployeeLeave> getLeaves() {
        return leaves;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Employee employee = (Employee) o;
        if (StringUtils.isNoneBlank(this.userId) && StringUtils.isNoneBlank(employee.getUserId())) {
            return userId.equals(employee.userId);
        } else if (standardAuthentication != null && StringUtils.isNoneBlank(standardAuthentication.getEmailAddress())) {
            return standardAuthentication.getEmailAddress().equals(employee.standardAuthentication.getEmailAddress());
        } else {
            return this.getId().equals(employee.getId());
        }
    }

    @Override
    public int hashCode() {
        int result = standardAuthentication.getEmailAddress() != null && StringUtils.isNoneBlank(standardAuthentication.getEmailAddress()) ? standardAuthentication.hashCode() : 1;
        result = StringUtils.isNoneBlank(userId) ? 31 * result + userId.hashCode() : StringUtils.isNoneBlank(employeeNumber) ? 31 * result + employeeNumber.hashCode() : 31 * result + getId().hashCode();
        return result;
    }

    public String getPassword() {
        return standardAuthentication.getPassword();
    }

    public void assignRoles(List<Role> roles) {
        this.employeeRoles = roles;
    }

    public StandardAuthentication getStandardAuthentication() {
        return standardAuthentication;
    }

    public OurDateTime getEmailTimestamp() {
        return emailTimestamp;
    }

    public void assignEmailTime(OurDateTime emailTimestamp) {
        this.emailTimestamp = emailTimestamp;
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }


    public OurDateTime getLockTime() {
        return lockTime;
    }

    public boolean isAccountant() {
        return employeeRoles != null && employeeRoles.contains(Role.ACCOUNTANT);
    }

    public boolean isEmployee() {
        return employeeRoles != null && employeeRoles.contains(Role.EMPLOYEE);
    }

    public boolean isAdmin() {
        return employeeRoles != null && employeeRoles.contains(Role.ADMIN);
    }

    public String getUserId() {
        return userId != null ? userId : "";
    }

    public String getCellNo() {
        return cellNo;
    }

    public OvertimeRule getOvertimeRule() {
        return overtimeRule;
    }

    public void setOvertimeRule(OvertimeRule overtimeRule) {
        this.overtimeRule = overtimeRule;
    }


    public OurDateTime getHireDate() {
        return hireDate;
    }

    public OurDateTime getTerminationDate() {
        return terminationDate;
    }

    public List<EmployeeType> getEmployeeTypes() {
        return employeeTypes != null ? employeeTypes : new ArrayList<>();
    }

    public Set<EmployeeChargeCode> getAssignedChargeCodes() {
        return chargeCodes == null ? new HashSet<>() : chargeCodes.stream().filter(chargeCode -> chargeCode.getChargeCode() != null).collect(Collectors.toSet());
    }

    public List<EmployeePayType> getPayTypes() {
        return payTypes != null ? payTypes : new ArrayList<>();
    }


    public boolean hasTimeZone() {
        return StringUtils.isNotBlank(employeeTimeZone);
    }

    public EmployeePayType getRegularPayType(OurDateTime workedDate) {
        try {
            return payTypes.stream().filter(payType -> workedDate.isAfterOrSame(payType.getStartDate()) && (payType.getEndDate() == null || (workedDate.isBeforeOrSame(payType.getEndDate()))) && payType.isRegular()).findFirst().get();
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

    public EmployeePayType getOvertimePayType(OurDateTime workedDate) {
        try {
            return payTypes.stream().filter(payType -> workedDate.isAfterOrSame(payType.getStartDate()) && (payType.getEndDate() == null || (workedDate.isBeforeOrSame(payType.getEndDate()))) && !payType.isRegular()).findFirst().get();
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

    public void updateChargeCodes(Set<EmployeeChargeCode> chargeCodes) {
        if (this.chargeCodes == null) {
            this.chargeCodes = new HashSet<>();
        }
        this.chargeCodes.addAll(chargeCodes);
    }

    public void assignPayTypeWithGreedyRule(EmployeePayType newEmployeePayType, boolean mappingByChargeCodes, boolean employeeManaged) {
        if (hasPayTypes()) {
            if (!employeeManaged) {
                if (mappingByChargeCodes) {
                    addPayTypeWithSystemStateWithChargeCode(newEmployeePayType);
                } else {
                    addPayTypeWithSystemStateWithNoChargeCodeGreedy(newEmployeePayType);
                }
            } else {
                addPayTypeWithEmployeeManagedStateGreedy(newEmployeePayType);
            }
        } else {
            this.payTypes = new ArrayList<>();
            addPayType(newEmployeePayType, mappingByChargeCodes, employeeManaged);
        }
    }

    public List<EmployeePayType> getOvertimePayTypes() {
        return payTypes != null ? payTypes.stream().filter(EmployeePayType::isOvertime).collect(Collectors.toList()) : new ArrayList<>();
    }

    public List<EmployeePayType> getRegularPayTypes() {
        return payTypes != null ? payTypes.stream().filter(EmployeePayType::isRegular).collect(Collectors.toList()) : new ArrayList<>();
    }

    public List<EmployeePayType> getLeavePayTypes() {
        return payTypes != null ? payTypes.stream().filter(EmployeePayType::isLeave).collect(Collectors.toList()) : new ArrayList<>();
    }

    public String getFullNameFirstNameFirst() {
        return getFirstName() + " " + getLastName();
    }

    public void assignAuthorizeChargeAssociations(List<EmployeeAuthorizeChargeAssociation> employeeAssociations) {
        if (this.authorizeChargeAssociations == null || this.authorizeChargeAssociations.isEmpty()) {
            this.authorizeChargeAssociations = new ArrayList<>();
            this.authorizeChargeAssociations.addAll(employeeAssociations);
        } else {
            updateAssociations(employeeAssociations);
        }
    }

    protected void addLeaveRequests(List<LeaveRequest> leaveRequests) {
        if (this.leaveRequests == null) {
            this.leaveRequests = new ArrayList<>();
        }
        this.leaveRequests.addAll(leaveRequests);
    }


    public List<LeaveRequest> getLeaveRequests() {
        if (leaveRequests != null) {
            leaveRequests.sort((o1, o2) -> o2.getStartDate().compareTo(o1.getStartDate()));
            leaveRequests.forEach(leaveRequest -> leaveRequest.loadEmployee(this));
            return leaveRequests;
        }
        return new ArrayList<>();
    }

    public void addClockAuthentication(List<Authentication> clockAuthentications) {
        List<Authentication> bioMetric = getClockAuthentications().stream().filter(auth -> auth.getClass().isAssignableFrom(BioMetricAuthentication.class)).collect(Collectors.toList());
        clockAuthentications.addAll(bioMetric);
        this.clockAuthentications = clockAuthentications;
    }


    public List<Authentication> getClockAuthentications() {
        return clockAuthentications != null ? clockAuthentications : new ArrayList<>();
    }

    private void updateAssociations(List<EmployeeAuthorizeChargeAssociation> employeeAssociations) {
        employeeAssociations.forEach(newAssociation -> {
            try {
                EmployeeAuthorizeChargeAssociation prevAssociation = this.authorizeChargeAssociations.stream().filter(oldAssociation -> oldAssociation.equals(newAssociation)).findFirst().get();
                updateStartDate(newAssociation, prevAssociation);
                updateEndDate(newAssociation, prevAssociation);
                updateBillable(newAssociation, prevAssociation);
            } catch (Exception ex) {
                this.authorizeChargeAssociations.add(newAssociation);
            }
        });
    }

    private void updateEndDate(EmployeeAuthorizeChargeAssociation newAssociation, EmployeeAuthorizeChargeAssociation prevAssociation) {
        if (prevAssociation.getEndDate() != null) {
            if (newAssociation.getEndDate() == null) {
                prevAssociation.updateEndDate(null);
            } else if (prevAssociation.getEndDate().isBeforeOrSame(newAssociation.getEndDate())) {
                prevAssociation.updateEndDate(newAssociation.getEndDate());
            }
        }
    }

    private void updateStartDate(EmployeeAuthorizeChargeAssociation newAssociation, EmployeeAuthorizeChargeAssociation prevAssociation) {
        if (prevAssociation.getStartDate().isAfterOrSame(newAssociation.getStartDate())) {
            prevAssociation.updateStartDate(newAssociation.getStartDate());
        }
    }

    private void updateBillable(EmployeeAuthorizeChargeAssociation newAssociation, EmployeeAuthorizeChargeAssociation prevAssociation) {
        if (prevAssociation.isBillable() != newAssociation.isBillable()) {
            prevAssociation.updateBillable(newAssociation.isBillable());
        }
    }


    private boolean isSameType(EmployeePayType payTypeToUpdate, EmployeePayType oldPayType) {
        return payTypeToUpdate.getPayType().getClass().isAssignableFrom(oldPayType.getPayType().getClass());
    }

    private void addPayTypeWithSystemStateWithChargeCode(EmployeePayType payTypeToUpdate) {
        removeUnAssignedChargeCodes(payTypeToUpdate);
        try {
            List<EmployeePayType> oldEmployeePayTypes = this.payTypes.stream().filter(payType -> isSameType(payTypeToUpdate, payType) && CollectionUtils.containsAny(payType.getChargeCodes(), payTypeToUpdate.getChargeCodes())).collect(Collectors.toList());
            if (oldEmployeePayTypes.isEmpty()) {
                throw new NoSuchElementException();
            } else {
                if (oldEmployeePayTypes.stream().anyMatch(oldEmployeePayType -> oldEmployeePayType.getPayType().equals(payTypeToUpdate.getPayType()))) {
                    EmployeePayType payTypeToCompare = oldEmployeePayTypes.stream().filter(oldEmployeePayType -> oldEmployeePayType.getPayType().equals(payTypeToUpdate.getPayType())).findFirst().get();
                    payTypeToCompare.getChargeCodes().removeAll(getChargeCodesToRemove(payTypeToUpdate, payTypeToCompare));
                    removePayTypeWithNoChargeCode(payTypeToCompare);
                } else {
                    if (hasSameDates(payTypeToUpdate, oldEmployeePayTypes.get(0))) {
                        oldEmployeePayTypes.get(0).getChargeCodes().removeAll(getChargeCodesToRemove(payTypeToUpdate, oldEmployeePayTypes.get(0)));
                        removePayTypeWithNoChargeCode(oldEmployeePayTypes.get(0));
                    }
                }
            }
            this.payTypes.add(payTypeToUpdate);
        } catch (NoSuchElementException ex) {
            try {
                EmployeePayType oldEmployeePayType = this.payTypes.stream().filter(payType -> hasSameDates(payTypeToUpdate, payType) && isSameType(payTypeToUpdate, payType) && payType.getPayType().equals(payTypeToUpdate.getPayType()) && !payType.getChargeCodes().isEmpty()).findFirst().get();
                oldEmployeePayType.getChargeCodes().addAll(payTypeToUpdate.getChargeCodes());
            } catch (NoSuchElementException ex1) {
                if (payTypeToUpdate.getChargeCodes().size() > 0) {
                    this.payTypes.add(payTypeToUpdate);
                }
            }
        }
    }

    private void removePayTypeWithNoChargeCode(EmployeePayType previousPayType) {
        if (previousPayType.getChargeCodes().size() == 0) {
            this.payTypes.remove(previousPayType);
        }
    }

    private List<ChargeCode> getChargeCodesToRemove(EmployeePayType payTypeToUpdate, EmployeePayType previousPayType) {
        return previousPayType.getChargeCodes()
                .stream()
                .filter(chargeCode -> payTypeToUpdate.getChargeCodes().contains(chargeCode))
                .collect(Collectors.toList());
    }

    private boolean hasSameDates(EmployeePayType newEmployeePayType, EmployeePayType oldEmployeePayType) {
        return (newEmployeePayType.getStartDate().isSameDay(oldEmployeePayType.getStartDate()) && (newEmployeePayType.getEndDate() == oldEmployeePayType.getEndDate() || newEmployeePayType.getEndDate() != null && oldEmployeePayType.getEndDate() != null && newEmployeePayType.getEndDate().isSameDay(oldEmployeePayType.getEndDate())));
    }

    private void removeUnAssignedChargeCodes(EmployeePayType employeePayType) {
        List<ChargeCode> employeeChargeCodes = getChargeCodesFromEmployee();
        employeePayType.getChargeCodes().removeAll(getChargeCodesToRemove(employeePayType, employeeChargeCodes));
    }

    private List<ChargeCode> getChargeCodesToRemove(EmployeePayType employeePayType, List<ChargeCode> chargeCodesFromEmployee) {
        return employeePayType.getChargeCodes().stream().filter(chargeCode -> !chargeCodesFromEmployee.contains(chargeCode)).collect(Collectors.toList());
    }

    private void updateEndDate(EmployeePayType oldEmployeePayType, EmployeePayType newEmployeePayType) {
        if (oldEmployeePayType.getEndDate() == null || newEmployeePayType.getEndDate() == null) {
            oldEmployeePayType.updateEndDate(null);
        } else if (oldEmployeePayType.getEndDate() != null && newEmployeePayType.getEndDate() != null) {
            if (newEmployeePayType.getEndDate().isAfterOrSame(oldEmployeePayType.getEndDate())) {
                oldEmployeePayType.updateEndDate(newEmployeePayType.getEndDate());
            }
        }
    }

    private void updateStartDate(EmployeePayType oldEmployeePayType, EmployeePayType newEmployeePayType) {
        if (newEmployeePayType.getStartDate().isBeforeOrSame(oldEmployeePayType.getStartDate())) {
            oldEmployeePayType.updateStartDate(newEmployeePayType.getStartDate());
        }
    }

    private List<ChargeCode> getChargeCodesFromEmployee() {
        List<ChargeCode> chargeCodes = new ArrayList<>();
        this.chargeCodes.forEach(chargeCode -> chargeCodes.add(chargeCode.getChargeCode()));
        return chargeCodes;
    }


    private void addPayType(EmployeePayType newEmployeePayType, boolean mappingByChargeCodes, boolean employeeManaged) {
        if (!employeeManaged && mappingByChargeCodes) {
            removeUnAssignedChargeCodes(newEmployeePayType);
            if (newEmployeePayType.hasChargeCodes()) {
                this.payTypes.add(newEmployeePayType);
            }
        } else {
            this.payTypes.add(newEmployeePayType);
        }
    }

    private boolean hasPayTypes() {
        return this.payTypes != null && this.payTypes.size() > 0;
    }


    private void addPayTypeWithEmployeeManagedStateGreedy(EmployeePayType payTypeToUpdate) {
        try {
            EmployeePayType previousPayType = getPayTypes().stream().filter(employeePayType -> employeePayType.equals(payTypeToUpdate)).findFirst().get();
            updateStartDate(previousPayType, payTypeToUpdate);
            updateEndDate(previousPayType, payTypeToUpdate);
        } catch (NoSuchElementException ex) {
            this.payTypes.add(payTypeToUpdate);
        }
    }

    private void addPayTypeWithSystemStateWithNoChargeCodeGreedy(EmployeePayType payTypeToUpdate) {
        try {
            EmployeePayType previousPayType = getPayTypes().stream().filter(employeePayType -> employeePayType.equals(payTypeToUpdate) && employeePayType.getChargeCodes().isEmpty()).findFirst().get();
            updateStartDate(previousPayType, payTypeToUpdate);
            updateEndDate(previousPayType, payTypeToUpdate);
        } catch (NoSuchElementException ex) {
            try {
                EmployeePayType employeePayType = this.payTypes.stream().filter(oldPayType -> hasSameDates(payTypeToUpdate, oldPayType) &&
                        isSameType(payTypeToUpdate, oldPayType) && oldPayType.getChargeCodes().isEmpty()).findFirst().get();
                this.payTypes.remove(employeePayType);
                this.payTypes.add(payTypeToUpdate);
            } catch (NoSuchElementException ex1) {
                this.payTypes.add(payTypeToUpdate);
            }
        }
    }

    /**
     * The type Builder
     */
    public static class Builder {
        private UUID id;
        private String firstName;
        private String lastName;
        private String employeeNumber;
        private boolean active;
        private EmployeeStatus employeeStatus;
        private List<Role> employeeRoles;
        private StandardAuthentication standardAuthentication;
        private String displayName;
        private Set<EmployeeChargeCode> chargeCodes;
        private OurDateTime emailTimestamp;
        private OurDateTime lockTime;
        private int loginAttempts;
        private List<EmployeePayType> payTypes;
        private List<EmployeeLeave> leaves;
        private String userId;
        private String cellNo;
        private OurDateTime hireDate;
        private OurDateTime terminationDate;
        private List<EmployeeType> employeeTypes;
        private List<EmployeeAuthorizeChargeAssociation> authorizeChargeAssociations;
        private boolean syncWithAccountingSystem;
        private OvertimeRule overtimeRule;
        private List<LeaveRequest> leaveRequests;
        private List<Authentication> clockAuthentications;
        private String employeeTimeZone;

        public Builder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public Builder withLockTime(OurDateTime lockTime) {
            this.lockTime = lockTime;
            return this;
        }

        public Builder withLoginAttempts(int loginAttempts) {
            this.loginAttempts = loginAttempts;
            return this;
        }

        public Builder withId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder withEmployeeNumber(String employeeNumber) {
            if (StringUtils.isNotBlank(employeeNumber)) {
                this.employeeNumber = employeeNumber;
            }
            return this;
        }

        public Builder withEmployeeStatus(boolean active) {
            this.active = active;
            return this;
        }

        public Builder withEmployeeStatus(OurDateTime now, OurDateTime terminationDate) {
            if (terminationDate == null) {
                this.active = true;
            } else this.active = terminationDate.isAfterOrSame(now);
            return this;
        }

        public Builder withEmployeeRoles(List<Role> employeeRoles) {
            this.employeeRoles = employeeRoles;
            return this;
        }

        public Builder withStandardAuthentication(String email, String password) {
            this.standardAuthentication = new StandardAuthentication(email, password);
            return this;
        }

        public Builder withEmail(String email) {
            if (standardAuthentication == null) {
                this.standardAuthentication = new StandardAuthentication(email, "");
            } else {
                this.standardAuthentication.updateEmailAddress(email);
            }
            return this;
        }

        public Builder withEmailStatus(EmployeeStatus employeeStatus) {
            this.employeeStatus = employeeStatus;
            return this;
        }

        public Builder withDisplayName(String displayName) {
            if (StringUtils.isNotBlank(displayName)) {
                this.displayName = displayName;
            } else {
                this.displayName = firstName + " " + lastName;
            }
            return this;
        }

        public Builder withEmailTimestamp(OurDateTime emailTimestamp) {
            this.emailTimestamp = emailTimestamp;
            return this;
        }

        public Builder withAuthorizeChargeAssociations(List<EmployeeAuthorizeChargeAssociation> authorizeChargeAssociations) {
            if (!CollectionUtils.isEmpty(authorizeChargeAssociations)) {
                this.authorizeChargeAssociations = authorizeChargeAssociations;
            }
            return this;
        }

        public Builder withChargeCode(Set<EmployeeChargeCode> chargeCodes) {
            this.chargeCodes = chargeCodes;
            return this;
        }

        public Builder withPayTypes(List<EmployeePayType> payTypes) {
            this.payTypes = payTypes;
            return this;
        }

        public Builder withLeaves(List<EmployeeLeave> leaves) {
            this.leaves = leaves;
            return this;
        }

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withCellNo(String cellNo) {
            this.cellNo = cellNo;
            return this;
        }

        public Builder withHireDate(OurDateTime hireDate) {
            this.hireDate = hireDate;
            return this;
        }

        public Builder withTerminationDate(OurDateTime terminationDate) {
            this.terminationDate = terminationDate;
            return this;
        }

        public Builder withEmployeeTypes(List<EmployeeType> employeeTypes) {
            this.employeeTypes = employeeTypes;
            return this;

        }

        public Builder withSyncWithAccountingSystem(boolean syncWithAccountingSystem) {
            this.syncWithAccountingSystem = syncWithAccountingSystem;
            return this;
        }

        public Builder withOvertimeRule(OvertimeRule overtimeRule) {
            if (overtimeRule != null) {
                this.overtimeRule = overtimeRule;
            }
            return this;
        }

        public Builder withLeaveRequests(List<LeaveRequest> leaveRequests) {
            if (leaveRequests != null) {
                this.leaveRequests = leaveRequests;

            }
            return this;
        }

        public Builder withClockAuthentication(List<Authentication> clockAuthentications) {
            if (clockAuthentications != null) {
                this.clockAuthentications = clockAuthentications;

            }
            return this;
        }

        public Builder withEmployeeTimeZone(String timeZone) {
            this.employeeTimeZone = timeZone;
            return this;
        }

        /**
         * This method is use to create employee.
         * If termination date is smaller then hire date this method will mark termination date to null.
         *
         * @return the employee
         */
        public Employee build() {
            updateTerminationDate();
            Employee employee = new Employee(id, firstName, lastName, employeeNumber, employeeRoles, standardAuthentication, active, employeeStatus, displayName, leaves, userId, cellNo, hireDate, terminationDate, employeeTypes, lockTime, loginAttempts, syncWithAccountingSystem, employeeTimeZone);
            employee.assignEmailTime(emailTimestamp);
            if (chargeCodes != null) {
                employee.updateChargeCodes(chargeCodes);
            }
            if (payTypes != null) {
                payTypes.forEach(payType -> employee.assignPayTypeWithGreedyRule(payType, false, false));
            }
            if (authorizeChargeAssociations != null) {
                employee.assignAuthorizeChargeAssociations(authorizeChargeAssociations);
            }
            if (leaveRequests != null) {
                employee.addLeaveRequests(leaveRequests);
            }
            if (clockAuthentications != null) {
                employee.addClockAuthentication(clockAuthentications);
            }
            employee.setOvertimeRule(overtimeRule);
            employee.setTimeZone(employeeTimeZone);
            return employee;
        }

        private void updateTerminationDate() {
            if (terminationDate != null && !hireDate.isBeforeOrSame(terminationDate)) {
                this.terminationDate = null;
            }
        }
    }
}