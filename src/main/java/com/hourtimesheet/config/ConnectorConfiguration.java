package com.hourtimesheet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.UUID;

/**
 * Created by hassan on 2/7/17.
 */
@Configuration
@PropertySource({"classpath:connector-${envTarget:local}.properties"})
public class ConnectorConfiguration {

    @Value("${APP_ID}")
    private String appID;

    @Value("${APP_NAME}")
    private String appName;

    @Value("${APP_URL}")
    private String appURL;

    @Value("${CERT_URL}")
    private String certUrl;

    @Value("${APP_DESCRIPTION}")
    private String appDescription;

    @Value("${SCHEDULER_REPT}")
    private String schedulerRepition;

    @Value("${APP_SUPPORT}")
    private String appSupport;

    @Value("${QB_TYPE}")
    private String qbType;

    @Value("${PERSONAL_DATA_PREF}")
    private String personalDataPreference;

    @Value("${IS_READ_ONLY}")
    private String readOnly;

    @Value("${USER_NAME}")
    private String userName;

    public String getAppID() {
        return appID;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppURL() {
        return appURL;
    }

    public String getAppDescription() {
        return appDescription;
    }

    public String getSchedulerRepition() {
        return schedulerRepition;
    }

    public String getOwnerID() {
        return UUID.randomUUID().toString();
    }

    public String getFileId() {
        return UUID.randomUUID().toString();
    }

    public String getAppSupport() {
        return appSupport;
    }

    public String getQbType() {
        return qbType;
    }

    public String getPersonalDataPreference() {
        return personalDataPreference;
    }

    public String getReadOnly() {
        return readOnly;
    }

    public String getUserName() {
        return userName;
    }

    public String getCertUrl() {
        return certUrl;
    }
}