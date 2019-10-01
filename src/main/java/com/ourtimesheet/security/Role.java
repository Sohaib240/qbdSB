package com.ourtimesheet.security;

/**
 * Created by Click Chain on 3/15/2016.
 */
public enum Role {
    EMPLOYEE("employee"),
    ADMIN("admin"),
    SUPERVISOR("supervisor"),
    ACCOUNTANT("accountant");


    private String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
