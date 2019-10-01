package com.ourtimesheet.employee.authentication;

import com.ourtimesheet.security.AuthenticationType;

public class BioMetricAuthentication extends Authentication {

    public BioMetricAuthentication(String authenticationId) {
        super(authenticationId);
    }

    @Override
    public AuthenticationType getType() {
        return AuthenticationType.BIO_METRIC;
    }
}
