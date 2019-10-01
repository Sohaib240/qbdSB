package com.ourtimesheet.service;

import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.query.EmployeeUpdateCreator;
import com.ourtimesheet.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * Created by Abdus Salam on 5/24/2016.
 */
public class DefaultEmployeeServiceImpl implements EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(DefaultEmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncryptor;


    public DefaultEmployeeServiceImpl(EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;
        this.passwordEncryptor = new BCryptPasswordEncoder();

    }

    @Override
    public void updateAllEmployees(List<Employee> employees) {
        employees.forEach(employee -> employeeRepository.update(new Query(Criteria.where("_id").is(employee.getId())), EmployeeUpdateCreator.createEmployeeUpdate(employee)));
    }


    @Override
    public List<Employee> findAdmins() {
        return employeeRepository.findAdmins();
    }

    @Override
    public boolean authenticateAdmin(String emailAddress, String password) {
        Employee employee = findByEmail(emailAddress);
        return passwordEncryptor.matches(password, employee.getPassword()) && (employee.isAdmin() || employee.isAccountant());
    }

    public Employee findByEmail(String emailAddress) {
        return employeeRepository.findByEmail(emailAddress);
    }

    @Override
    public void inactiveAllWithEmployeeNumber() {
        employeeRepository.inactiveAllWithEmployeeNumber();
    }

    @Override
    public void deleteAllByEmployeeNumber(List<String> employeeNumbers) {
        employeeRepository.deleteAllByEmployeeNumber(employeeNumbers);
    }

}

