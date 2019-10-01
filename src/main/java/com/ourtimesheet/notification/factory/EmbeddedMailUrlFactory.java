package com.ourtimesheet.notification.factory;


import java.text.MessageFormat;

/**
 * Created by Abdus Salam on 9/7/2016.
 */
public class EmbeddedMailUrlFactory {

    private final String signupURL;
    private final String syncDetailsUrl;
    private final String resetPasswordUrl;
    private final String changePasswordUrl;
    private final String timesheetUrl;
    private final String timesheetReviewUrl;
    private final String timesheetExportUrl;
    private final String paymentNotificationUrl;
    private final String settingsUrl;
    private final String companyLoginUrl;
    private final String timesheeturlByDate;
    private final String viewLeaveRequestUrl;
    private final String createLeaveRequestUrl;

    public EmbeddedMailUrlFactory(String signupURL, String syncDetailsUrl, String resetPasswordUrl, String changePasswordUrl, String timesheetUrl, String timesheetReviewUrl, String timesheetExportUrl, String paymentNotificationUrl, String settingsUrl, String companyLoginUrl, String timesheeturlByDate, String viewLeaveRequestUrl, String createLeaveRequestUrl) {
        this.signupURL = signupURL;
        this.syncDetailsUrl = syncDetailsUrl;
        this.resetPasswordUrl = resetPasswordUrl;
        this.changePasswordUrl = changePasswordUrl;
        this.timesheetUrl = timesheetUrl;
        this.timesheetReviewUrl = timesheetReviewUrl;
        this.timesheetExportUrl = timesheetExportUrl;
        this.paymentNotificationUrl = paymentNotificationUrl;
        this.settingsUrl = settingsUrl;
        this.companyLoginUrl = companyLoginUrl;
        this.timesheeturlByDate = timesheeturlByDate;
        this.viewLeaveRequestUrl = viewLeaveRequestUrl;
        this.createLeaveRequestUrl = createLeaveRequestUrl;
    }

    public String createSignUpUrl(String companyName, String employeeId) {
        return MessageFormat.format(signupURL, companyName, employeeId);
    }

    public String createSyncDetailUrl(String companyName) {
        return MessageFormat.format(syncDetailsUrl, companyName);
    }

    public String createResetPasswordUrl(String companyName, String employeeId) {
        return MessageFormat.format(resetPasswordUrl, companyName, employeeId);
    }

    public String createChangePasswordUrl(String companyName) {
        return MessageFormat.format(changePasswordUrl, companyName);
    }

    public String createTimesheetUrl(String companyName) {
        return MessageFormat.format(timesheetUrl, companyName);
    }

    public String createTimesheetReviewUrl(String companyName) {
        return MessageFormat.format(timesheetReviewUrl, companyName);
    }

    public String createTimesheetExportUrl(String companyName) {
        return MessageFormat.format(timesheetExportUrl, companyName);
    }

    public String createPaymentNotificationUrl(String companyName) {
        return MessageFormat.format(paymentNotificationUrl, companyName);
    }

    public String createSettingsUrl(String companyName) {
        return MessageFormat.format(settingsUrl, companyName);
    }

    public String createCompanyLoginUrl(String companyName){
        return MessageFormat.format(companyLoginUrl, companyName);
    }

    public String createLeaveRequestUrl(String companyName){
        return MessageFormat.format(createLeaveRequestUrl, companyName);
    }
    public String createViewLeaveRequestUrl(String companyName){
        return MessageFormat.format(viewLeaveRequestUrl, companyName);
    }

    public Object createTimesheetByDateUrl(String companyName , String date) {
        return MessageFormat.format(timesheeturlByDate, companyName , date);
    }
}
