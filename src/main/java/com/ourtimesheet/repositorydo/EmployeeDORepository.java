package com.ourtimesheet.repositorydo;

import com.ourtimesheet.employee.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * Created by Abdus Salam on 5/24/2016.
 */
public interface EmployeeDORepository extends MongoRepository<Employee, UUID> {

    Employee findByEmployeeNumber(String employeeNumber);

    @Query(value = "{'standardAuthentication.emailAddress' : ?0}")
    Employee findByEmail(String emailAddress);

    @Query(value = "{'employeeRoles' : {'$in' : ['ADMIN']}}}")
    List<Employee> findAdmins();

    void deleteByEmployeeNumberIn(List<String> employeeNumbers);

}
