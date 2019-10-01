package com.ourtimesheet.service;

import com.ourtimesheet.employee.Employee;

/**
 * Created by Abdus Salam on 10/4/2017.
 */
public interface UserService {

    Employee findByEmail(String username);
}