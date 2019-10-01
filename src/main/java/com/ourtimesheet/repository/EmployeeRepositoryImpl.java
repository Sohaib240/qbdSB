package com.ourtimesheet.repository;

import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.exception.CompanyNotFoundException;
import com.ourtimesheet.multitenant.CompanyHolder;
import com.ourtimesheet.multitenant.MultiTenantMongoDbFactory;
import com.ourtimesheet.repositorydo.EmployeeDORepository;
import com.ourtimesheet.security.Role;
import com.ourtimesheet.util.MasterConfigUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * Created by Abdus Salam on 5/24/2016.
 */
public class EmployeeRepositoryImpl extends GenericRepository<Employee> implements EmployeeRepository {

    private static final Logger log = LoggerFactory.getLogger(EmployeeRepositoryImpl.class);

    private final EmployeeDORepository employeeDORepository;
    private final MongoTemplate mongoTemplate;
    private final MasterConfigUtils masterConfigUtils;

    public EmployeeRepositoryImpl(EmployeeDORepository repository, MongoTemplate mongoTemplate, MasterConfigUtils masterConfigUtils) {
        super(repository);
        this.employeeDORepository = repository;
        this.mongoTemplate = mongoTemplate;
        this.masterConfigUtils = masterConfigUtils;
    }

    @Override
    public Employee findByEmployeeNumber(String employeeNumber) {
        Employee employeeFromDb = employeeDORepository.findByEmployeeNumber(employeeNumber);
        if (employeeFromDb == null) {
            throw new RuntimeException("Employee Not Found");
        }
        return employeeFromDb;
    }


    @Override
    public Employee findByEmail(String email) {
        return employeeDORepository.findByEmail(StringUtils.lowerCase(email));
    }


    @Override
    public List<Employee> findAdmins() {
        return employeeDORepository.findAdmins();
    }

    @Override
    public void inactiveAllWithEmployeeNumber() {
        try {
            setDatabase();
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("employeeRoles").nin(Role.ADMIN), Criteria.where("employeeNumber").exists(true)
                    , Criteria.where("employeeNumber").nin("", " ", null), Criteria.where("syncWithAccountingSystem").is(false));
            query.addCriteria(criteria);
            Update update = new Update();
            update.set("active", false);
            mongoTemplate.updateMulti(query, update, Employee.class);
        } catch (Exception ex) {
            throw new RuntimeException("Unable to inactive all Employees");
        } finally {
            MultiTenantMongoDbFactory.clearDatabaseNameForCurrentThread();
        }
    }


    @Override
    public void update(Query query, Update update) {
        try {
            setDatabase();
            mongoTemplate.upsert(query, update, Employee.class);
        } catch (Exception ex) {
            throw new RuntimeException("Unable tp update record");
        } finally {
            MultiTenantMongoDbFactory.clearDatabaseNameForCurrentThread();
        }
    }


    @Override
    public void deleteAllByEmployeeNumber(List<String> employeeNumbers) {
        employeeDORepository.deleteByEmployeeNumberIn(employeeNumbers);
    }


    private void setDatabase() throws CompanyNotFoundException {
        MultiTenantMongoDbFactory.setDatabaseNameForCurrentThread(masterConfigUtils.getDBForDomain(CompanyHolder.getCompanyName()));
    }
}