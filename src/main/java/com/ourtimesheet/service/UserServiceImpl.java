package com.ourtimesheet.service;

import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.employee.SuperAdmin;
import com.ourtimesheet.service.superAdmin.SuperAdminService;

/**
 * Created by Abdus Salam on 10/4/2017.
 */
public class UserServiceImpl implements UserService {

    private final EmployeeService employeeService;
    private final SuperAdminService superAdminService;

    public UserServiceImpl(EmployeeService employeeService, SuperAdminService superAdminService) {
        this.employeeService = employeeService;
        this.superAdminService = superAdminService;
    }



    @Override
    public Employee findByEmail(String emailAddress) {
        Employee employee = employeeService.findByEmail(emailAddress);
        if (employee == null) {
            return superAdminService.findByEmail(emailAddress);
        }
        return employee;
    }
}