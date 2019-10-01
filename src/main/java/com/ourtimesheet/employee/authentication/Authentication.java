package com.ourtimesheet.employee.authentication;

import com.ourtimesheet.common.Entity;
import com.ourtimesheet.security.AuthenticationType;

/**
 * Created by Click Chain on 10/22/2018.
 */
public abstract class Authentication extends Entity {

    private final String authenticationId;

    protected Authentication(String authenticationId) {
        this.authenticationId = authenticationId;
    }

    public abstract AuthenticationType getType();

    public String getAuthenticationId() {
        return authenticationId;
    }
}
