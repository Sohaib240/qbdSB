package com.ourtimesheet.notification.builder;

import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.multitenant.CompanyHolder;
import com.ourtimesheet.notification.domain.Notification;
import com.ourtimesheet.notification.domain.NotificationTemplate;
import com.ourtimesheet.notification.factory.EmbeddedMailUrlFactory;
import com.ourtimesheet.timesheet.Timesheet;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Muhammad Talha on 2/15/2017.
 */
public class ExportProcessCompleteNotificationBuilder {

    private String emailAddress;
    private NotificationTemplate notificationTemplate;
    private EmbeddedMailUrlFactory embeddedMailUrlFactory;
    private String resourcesUrl;

    public ExportProcessCompleteNotificationBuilder(EmbeddedMailUrlFactory embeddedMailUrlFactory, String resourcesUrl) {
        this.embeddedMailUrlFactory = embeddedMailUrlFactory;
        this.resourcesUrl = resourcesUrl;
    }

    public ExportProcessCompleteNotificationBuilder withEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public ExportProcessCompleteNotificationBuilder withNotificationTemplate(Employee employee, List<Timesheet> exportedTimesheets, List<Timesheet> failedTimesheets) {
        String companyName = CompanyHolder.getCompanyName();
        String imageSource = MessageFormat.format(resourcesUrl, companyName);
        Map<String, Object> propertyValueMap = new HashMap<>();
        propertyValueMap.put("firstName", employee.getFirstName());
        propertyValueMap.put("exportedTimesheetsCount", String.valueOf(exportedTimesheets.size()));
        propertyValueMap.put("failedTimesheetsCount", String.valueOf(failedTimesheets.size()));
        propertyValueMap.put("totalTimesheetsCount", String.valueOf(exportedTimesheets.size() + failedTimesheets.size()));
        propertyValueMap.put("hasFailedTimesheets", failedTimesheets.size() > 0);
        propertyValueMap.put("failureTimesheets", transformTimesheetsInfoToStringList(failedTimesheets));
        propertyValueMap.put("url", embeddedMailUrlFactory.createTimesheetExportUrl(CompanyHolder.getCompanyName()));
        propertyValueMap.put("imgSrc", imageSource);
        this.notificationTemplate = new NotificationTemplate("exportProcessCompleteNotificationBody.html", "exportProcessCompleteNotificationSubject.html", propertyValueMap);
        return this;
    }

    private List<TimesheetInfo> transformTimesheetsInfoToStringList(List<Timesheet> failedTimesheets) {
        List<TimesheetInfo> failedTimesheetsInfo = new ArrayList<>();
        failedTimesheets.forEach(timesheet -> {
            failedTimesheetsInfo.add(new TimesheetInfo(timesheet));
        });
        return failedTimesheetsInfo;
    }

    public Notification createNotification() {
        return new Notification(emailAddress, notificationTemplate, null);
    }

    private class TimesheetInfo {
        private final String startDate;
        private final String endDate;
        private final String employeeName;
        private final String hours;


        TimesheetInfo(Timesheet timesheet) {
            startDate = timesheet.getStartDate().dateOnlyStandardFormat();
            endDate = timesheet.getEndDate().dateOnlyStandardFormat();
            employeeName = timesheet.getEmployee().getDisplayName();
            hours = String.valueOf(timesheet.hoursWorkedMatrix().getHoursWorkedGrandTotal(timesheet.getEmployee().getOvertimeRule()));
        }

        public String getStartDate() {
            return startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public String getEmployeeName() {
            return employeeName;
        }

        public String getHours() {
            return hours;
        }
    }

}
