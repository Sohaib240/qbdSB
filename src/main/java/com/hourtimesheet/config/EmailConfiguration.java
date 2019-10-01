package com.hourtimesheet.config;

import com.ourtimesheet.notification.builder.ExportProcessCompleteNotificationBuilder;
import com.ourtimesheet.notification.builder.ExportProcessFailureNotificationBuilder;
import com.ourtimesheet.notification.builder.ImportProcessCompleteNotificationBuilder;
import com.ourtimesheet.notification.factory.EmbeddedMailUrlFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Click Chain on 9/16/2016.
 */
@Configuration
@PropertySource("classpath:link-${envTarget:local}.properties")
public class EmailConfiguration {

    @Value("${signup-url}")
    private String signupURL;

    @Value("${sync-details-url}")
    private String syncDetailsUrl;

    @Value("${reset-password-url}")
    private String resetPasswordUrl;

    @Value("${change-password-url}")
    private String changePasswordUrl;

    @Value("${timesheet-url}")
    private String timesheetUrl;

    @Value("${timesheet-review-url}")
    private String timesheetReviewUrl;

    @Value("${timesheet-export-url}")
    private String timesheetExportUrl;

    @Value("${payment-notification-url}")
    private String paymentNotificationUrl;

    @Value("${settings-url}")
    private String settingsUrl;

    @Value("${static-resources-url}")
    private String resourcesUrl;

    @Value("${company-login-url}")
    private String companyLoginUrl;

    @Value("${create-leave-request-url}")
    private String createLeaveRequestUrl;

    @Value("${view-leave-request-url}")
    private String viewLeaveRequestUrl;

    @Value("${company-url}")
    private String companyUrl;
    @Value(("${timesheet-url-by-date}"))
    private String timesheeturlByDate;


    @Bean
    public EmbeddedMailUrlFactory embeddedMailUrlFactory() {
        return new EmbeddedMailUrlFactory(signupURL, syncDetailsUrl, resetPasswordUrl, changePasswordUrl, timesheetUrl, timesheetReviewUrl, timesheetExportUrl, paymentNotificationUrl, settingsUrl, companyLoginUrl, timesheeturlByDate, viewLeaveRequestUrl, createLeaveRequestUrl);
    }

    @Bean
    @Autowired
    public ExportProcessCompleteNotificationBuilder exportProcessCompleteNotificationBuilder(EmbeddedMailUrlFactory embeddedMailUrlFactory) {
        return new ExportProcessCompleteNotificationBuilder(embeddedMailUrlFactory, resourcesUrl);
    }

    @Bean
    @Autowired
    public ExportProcessFailureNotificationBuilder exportProcessFailureNotificationBuilder(EmbeddedMailUrlFactory embeddedMailUrlFactory) {
        return new ExportProcessFailureNotificationBuilder(embeddedMailUrlFactory, resourcesUrl);
    }

    @Bean
    @Autowired
    public ImportProcessCompleteNotificationBuilder importProcessCompleteNotificationBuilder(EmbeddedMailUrlFactory embeddedMailUrlFactory) {
        return new ImportProcessCompleteNotificationBuilder(embeddedMailUrlFactory, resourcesUrl);
    }

}
