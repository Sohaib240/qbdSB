package com.ourtimesheet.repository;

import com.ourtimesheet.employee.Employee;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * Created by Abdus Salam on 5/24/2016.
 */
public interface EmployeeRepository extends Repository<Employee> {

    Employee findByEmployeeNumber(String employeeNumber);

    List<Employee> findAdmins();

    void inactiveAllWithEmployeeNumber();

    void update(Query query, Update update);

    void deleteAllByEmployeeNumber(List<String> employeeNumbers);

    Employee findByEmail(String emailAddress);


}