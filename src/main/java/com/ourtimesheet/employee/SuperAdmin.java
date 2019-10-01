package com.ourtimesheet.employee;

import com.ourtimesheet.security.Role;
import com.ourtimesheet.security.StandardAuthentication;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

/**
 * Created by Abdus Salam on 10/4/2017.
 */
@Document(collection = "superAdmin")
public class SuperAdmin extends Employee {

    protected SuperAdmin(UUID id, String firstName, String lastName, List<Role> employeeRoles,
                         StandardAuthentication standardAuthentication, boolean active, EmployeeStatus employeeStatus, String displayName) {
        super(id, firstName, lastName, null, employeeRoles, standardAuthentication, active, employeeStatus, displayName, null, null, null, null, null, null);
    }

    public static class Builder {
        private UUID id;
        private String firstName;
        private String lastName;
        private boolean active;
        private EmployeeStatus employeeStatus;
        private List<Role> employeeRoles;
        private StandardAuthentication standardAuthentication;
        private String displayName;

        public Builder(UUID id, String firstName, String lastName) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public Builder withEmployeeStatus(boolean active) {
            this.active = active;
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

        public SuperAdmin build() {
            return new SuperAdmin(id, firstName, lastName, employeeRoles, standardAuthentication, active, employeeStatus, displayName);
        }
    }
}