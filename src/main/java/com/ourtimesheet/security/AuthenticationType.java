package com.ourtimesheet.security;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by Click Chain on 10/22/2018.
 */
public enum AuthenticationType {

    PROXIMITY_CARD("Proximity Card"),
    MAGNETIC_STRIP_CARD("Magnetic Strip Card"),
    PIN_CODE("Pin Code"),
    BIO_METRIC("Bio Metric");

    private final String description;

    AuthenticationType(String description) {
        this.description = description;
    }

    public static AuthenticationType getClockAuthenticationType(String value) {
        Optional<AuthenticationType> authenticationType = Arrays.stream(AuthenticationType.values()).filter(clockAuthenticationType -> StringUtils.equalsIgnoreCase(clockAuthenticationType.description, value)).findFirst();
        return authenticationType.orElse(null);
    }

    public static List<String> getClockAuthenticationTypes() {
        return Arrays.asList(PROXIMITY_CARD.description, MAGNETIC_STRIP_CARD.description, PIN_CODE.description);
    }

    public String getDescription() {
        return description;
    }
}
