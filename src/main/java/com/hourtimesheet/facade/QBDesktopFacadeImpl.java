package com.hourtimesheet.facade;

import com.hourtimesheet.factory.TransformerFactory;
import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.paytype.PayType;
import com.ourtimesheet.qbd.domain.Customer;
import com.ourtimesheet.qbd.domain.QuickBooksClass;
import com.ourtimesheet.qbd.domain.ServiceItem;
import com.ourtimesheet.security.Role;
import com.ourtimesheet.service.CompanyService;
import com.ourtimesheet.service.EmployeeService;
import com.ourtimesheet.transformer.EmployeeTransformer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Abdus Salam on 2/14/2017.
 */
public class QBDesktopFacadeImpl implements QBDesktopFacade {

    private static final Logger LOG = LoggerFactory.getLogger(QBDesktopFacadeImpl.class);
    private final TransformerFactory transformerFactory;
    private final EmployeeTransformer employeeTransformer;
    private final EmployeeService employeeService;
    private final CompanyService companyService;

    public QBDesktopFacadeImpl(TransformerFactory transformerFactory, EmployeeTransformer employeeTransformer, EmployeeService employeeService, CompanyService companyService) {
        this.transformerFactory = transformerFactory;
        this.employeeTransformer = employeeTransformer;
        this.employeeService = employeeService;
        this.companyService = companyService;
    }

    @Override
    public List<Employee> extractEmployees(String rawData) {
        List<Employee> employees = new ArrayList<>();
        try {
            List<Employee> importedEmployees = (List<Employee>) transformerFactory.create().transform(rawData);
            importedEmployees.forEach(employee -> employees.add(employeeTransformer.transform(employee, companyService.getCompany().getTimeZone())));
            List<Employee> filteredEmployees = employees.stream().filter(Objects::nonNull).collect(Collectors.toList());
            filteredEmployees.forEach(employee -> {
                if (employee.getRoles() == null) {
                    employee.assignRoles(Arrays.asList(Role.EMPLOYEE));
                }
            });
            return filteredEmployees;
        } catch (Exception e) {
            throw new RuntimeException("Unable to transform employees"+ e.getMessage());
        }
    }

    @Override
    public List<Employee> extractVendors(String rawData) {
        List<Employee> employees = new ArrayList<>();
        try {
            List<Employee> vendors = (List<Employee>) transformerFactory.create().transform(rawData);

            List<String> filteredVendors = new ArrayList<>();
            vendors.stream().filter(vendor -> StringUtils.isBlank(vendor.getLastName()) && StringUtils.isBlank(vendor.getFirstName())).forEach(filteredVendor -> filteredVendors.add(filteredVendor.getEmployeeNumber()));
            employeeService.deleteAllByEmployeeNumber(filteredVendors);
            List<Employee> vendorsToImport = vendors.stream().filter(vendor -> StringUtils.isNotBlank(vendor.getLastName()) && StringUtils.isNotBlank(vendor.getFirstName())).collect(Collectors.toList());

            vendorsToImport.forEach(employee -> employees.add(employeeTransformer.transform(employee, companyService.getCompany().getTimeZone())));
            List<Employee> filteredEmployees = employees.stream().filter(Objects::nonNull).collect(Collectors.toList());
            filteredEmployees.forEach(employee -> {
                if (employee.getRoles() == null) {
                    employee.assignRoles(Arrays.asList(Role.EMPLOYEE));
                }
            });
            return filteredEmployees;
        } catch (Exception e) {
            throw new RuntimeException("Unable to transform employees"+ e.getMessage());
        }
    }

    @Override
    public Set<QuickBooksClass> extractClasses(String rawData) {
        Set<QuickBooksClass> classes = new HashSet<>();
        try {
            classes.addAll((List<QuickBooksClass>) transformerFactory.create().transform(rawData));
        } catch (Exception e) {
            LOG.error("Error transforming classes with error "+ e.getMessage(), e);
            throw new RuntimeException("Unable to transform classes with error"+ e.getMessage());
        }
        return classes;
    }

    @Override
    public Set<ServiceItem> extractServiceItems(String rawData) {
        Set<ServiceItem> serviceItems = new HashSet<>();
        try {
            serviceItems.addAll((List<ServiceItem>) transformerFactory.create().transform(rawData));
        } catch (Exception e) {
            LOG.error("Error transforming service items with error "+ e.getMessage(), e);
            throw new RuntimeException("Unable to transform service items" + e.getMessage());
        }
        return serviceItems;
    }

    @Override
    public Set<Customer> extractCustomers(String rawData) {
        Set<Customer> customers = new HashSet<>();
        try {
            customers.addAll((List<Customer>) transformerFactory.create().transform(rawData));
        } catch (Exception e) {
            LOG.error("Error transforming customers with error "+ e.getMessage(), e);
            throw new RuntimeException("Unable to transform customers" + e.getMessage());
        }
        return customers;
    }

    @Override
    public Set<PayType> extractPayRollItems(String rawData) {
        Set<PayType> payTypes = new HashSet<>();
        try {
            payTypes.addAll((List<PayType>) transformerFactory.create().transform(rawData));
        } catch (Exception e) {
            LOG.error("Error transforming payrolls with error "+ e.getMessage(), e);
            throw new RuntimeException("Unable to transform payrolls" + e.getMessage());
        }
        return payTypes;
    }

    @Override
    public void extractHoursSyncResponse(String rawData) throws Exception {
        transformerFactory.create().transform(rawData);
    }
}
