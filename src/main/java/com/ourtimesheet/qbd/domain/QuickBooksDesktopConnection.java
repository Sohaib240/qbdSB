package com.ourtimesheet.qbd.domain;

/**
 * Created by Talha on 5/11/2016.
 */
public class QuickBooksDesktopConnection {

    private final String accessToken;

    private final boolean systemConnected;

    public QuickBooksDesktopConnection(String accessToken, boolean systemConnected) {
        this.accessToken = accessToken;
        this.systemConnected = systemConnected;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public boolean isSystemConnected() {
        return systemConnected;
    }
}
