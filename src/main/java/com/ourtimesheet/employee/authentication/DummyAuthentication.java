package com.ourtimesheet.employee.authentication;

import com.ourtimesheet.security.AuthenticationType;

public class DummyAuthentication extends Authentication {

    public DummyAuthentication() {
        super(null);
    }

    @Override
    public AuthenticationType getType() {
        return null;
    }
}
