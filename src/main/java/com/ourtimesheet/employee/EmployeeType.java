package com.ourtimesheet.employee;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Noor's on 6/22/2017.
 */
public enum EmployeeType {
    SALARIED("salaried"),
    HOURLY("hourly"),
    EXEMPT("Exempt"),
    NON_EXEMPT("Non Exempt");

    private final String description;

    EmployeeType(String description) {
        this.description = description;
    }

    public static EmployeeType getEmployeeType(String value) {
        for (EmployeeType e : EmployeeType.values()) {
            if (StringUtils.equalsIgnoreCase(value, e.getDescription())) {
                return e;
            }
        }
        return null;
    }

    public String getDescription() {
        return description;
    }
}
