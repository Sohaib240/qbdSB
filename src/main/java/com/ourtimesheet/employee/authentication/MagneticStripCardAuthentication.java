package com.ourtimesheet.employee.authentication;

import com.ourtimesheet.security.AuthenticationType;

/**
 * Created by Click Chain on 10/22/2018.
 */
public class MagneticStripCardAuthentication extends Authentication {

    public MagneticStripCardAuthentication(String authenticationId) {
        super(authenticationId);
    }

    @Override
    public AuthenticationType getType() {
        return AuthenticationType.MAGNETIC_STRIP_CARD;
    }
}
