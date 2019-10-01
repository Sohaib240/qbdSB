package com.ourtimesheet.profile;

import com.ourtimesheet.common.Entity;
import com.ourtimesheet.datetime.OurDateTime;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.UUID;

public class ClockTerminal extends Entity {

    private String deviceName;
    private String activationCode;
    private boolean activeStatus;
    private OurDateTime codeActivationTime;
    private String deviceAddress;

    @PersistenceConstructor
    public ClockTerminal(UUID id, String deviceName, String activationCode, boolean activeStatus, OurDateTime codeActivationTime) {
        super(id);
        this.deviceName = deviceName;
        this.activationCode = activationCode;
        this.activeStatus = activeStatus;
        this.codeActivationTime = codeActivationTime;
    }

    public ClockTerminal(String deviceName) {
        super(UUID.randomUUID());
        this.deviceName = deviceName;
        this.activationCode = "";
        this.activeStatus = false;
        this.codeActivationTime = null;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public boolean activeStatus() {
        return activeStatus;
    }

    public OurDateTime getCodeActivationTime() {
        return codeActivationTime;
    }

    public void setCodeActivationTime(OurDateTime codeActivationTime) {
        this.codeActivationTime = codeActivationTime;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public boolean isAuthenticated(OurDateTime now) {
        return getCodeActivationTime() == null || now.isBeforeOrSame(getCodeActivationTime().plusDays(3));
    }
}