package com.ourtimesheet.service;

import com.ourtimesheet.employee.Employee;

import java.util.List;

/**
 * Created by Abdus Salam on 5/24/2016.
 */
public interface EmployeeService {

    void updateAllEmployees(List<Employee> employees);

    List<Employee> findAdmins();

    boolean authenticateAdmin(String emailAddress, String password);

    void inactiveAllWithEmployeeNumber();

    void deleteAllByEmployeeNumber(List<String> employeeNumbers);


    Employee findByEmail(String emailAddress);
}