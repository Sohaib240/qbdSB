package com.ourtimesheet.notification.domain;

import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.notification.factory.EmbeddedMailUrlFactory;

import java.util.List;

/**
 * Created by Noor's on 11/24/2016.
 */
public abstract class NotificationEvent {

    private final String actionByUsername;
    private final String companyName;
    private final List<Employee> targetEmployees;

    public NotificationEvent(String actionByUsername, String companyName, List<Employee> targetEmployees) {
        this.actionByUsername = actionByUsername;
        this.companyName = companyName;
        this.targetEmployees = targetEmployees;
    }

    public String getActionByUsername() {
        return actionByUsername;
    }

    public String getCompanyName() {
        return companyName;
    }

    public List<Employee> getTargetEmployees() {
        return targetEmployees;
    }

    public abstract List<Notification> toNotifications(EmbeddedMailUrlFactory embeddedMailUrlFactory, String resourcesUrl);
}