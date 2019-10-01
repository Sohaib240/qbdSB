package com.ourtimesheet.data;

/**
 * Created by hassan on 11/23/16.
 */
public class OpenIdInfo {

    private final String openId;

    private final String realmId;

    public OpenIdInfo(String openId, String realmId) {
        this.openId = openId;
        this.realmId = realmId;
    }
}
