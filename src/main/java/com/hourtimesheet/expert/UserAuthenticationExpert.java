package com.hourtimesheet.expert;

import com.hourtimesheet.encryption.Encryptor;
import com.hourtimesheet.validation.InvalidUserException;
import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.multitenant.CompanyHolder;
import com.ourtimesheet.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by Noor's on 4/26/2017.
 */
public class UserAuthenticationExpert {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthenticationExpert.class);

    private final UserService userService;
    private final Encryptor encryptor;
    private final PasswordEncoder passwordEncoder;

    public UserAuthenticationExpert(UserService userService, Encryptor encryptor, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.encryptor = encryptor;
        this.passwordEncoder = passwordEncoder;
    }

    public void authenticateEncrypted(String email, String password, String companyName) throws InvalidUserException {
        CompanyHolder.set(companyName);
        Employee employee = userService.findByEmail(email);
        if (employee == null || !(passwordEncoder.matches(encryptor.decrypt(password), employee.getPassword()) && (employee.isAdmin() || employee.isAccountant()))) {
            throw new InvalidUserException("Invalid email/password");
        }
    }

    public void authenticate(String email, String password, String companyName) throws InvalidUserException {
        CompanyHolder.set(companyName);
        Employee employee = null;
        try {
            employee = userService.findByEmail(email);
        } catch (Exception ex) {
            LOGGER.error("Error while getting employee with error {}",ex.getMessage());
        }
        if (employee == null || !(passwordEncoder.matches(password, employee.getPassword()) && (employee.isAdmin() || employee.isAccountant()))) {
            throw new InvalidUserException("Invalid email/password");
        }
    }
}