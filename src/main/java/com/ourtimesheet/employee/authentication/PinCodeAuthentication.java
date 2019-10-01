package com.ourtimesheet.employee.authentication;

import com.ourtimesheet.security.AuthenticationType;

public class PinCodeAuthentication extends Authentication {

    public PinCodeAuthentication(String authenticationId) {
        super(authenticationId);
    }

    @Override
    public AuthenticationType getType() {
        return AuthenticationType.PIN_CODE;
    }
}
