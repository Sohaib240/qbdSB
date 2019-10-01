package com.ourtimesheet.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.PersistenceConstructor;

/**
 * Created by Talha Zahid on 2/18/2016.
 */
public class StandardAuthentication {

    private String emailAddress;
    private String password;

    @PersistenceConstructor
    public StandardAuthentication(String emailAddress, String password) {
        this.emailAddress =emailAddress != null ? StringUtils.lowerCase(emailAddress.replace(" ","")): StringUtils.EMPTY;
        this.password = password;
    }

    public String getEmailAddress() {
        return StringUtils.isNoneBlank(emailAddress) ? emailAddress : "";
    }

    public String getPassword() {
        return password;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateEmailAddress(String email) {
        this.emailAddress = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StandardAuthentication that = (StandardAuthentication) o;

        if (emailAddress != null ? !emailAddress.equals(that.emailAddress) : that.emailAddress != null) return false;
        return password != null ? password.equals(that.password) : that.password == null;
    }

    @Override
    public int hashCode() {
        int result = emailAddress != null ? emailAddress.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
