package com.hourtimesheet.transformer;

import com.hourtimesheet.desktop.EmployeeRet;
import com.hourtimesheet.desktop.QBXML;
import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.employee.EmployeeStatus;
import com.ourtimesheet.employee.EmployeeType;
import org.apache.commons.lang.BooleanUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

/**
 * Created by click chain 2 on 9/16/2015.
 */
public class EmployeeTransformer extends BaseTransformer implements Transformer<String, List<Employee>> {

    @Override
    public List<Employee> transform(String s) throws Exception {
        List<Employee> employees = new ArrayList<>();
        QBXML qbxmlType = transformToQBXML(s);
        List<EmployeeRet> employeeRets = qbxmlType.getQBXMLMsgsRs().getEmployeeQueryRs().getEmployeeRet();
        employees.addAll(employeeRets.stream().map(this::createEmployee).collect(Collectors.toList()));
        return employees;
    }

    private Employee createEmployee(EmployeeRet employeeRet) {
        return new Employee.Builder(employeeRet.getFirstName(), employeeRet.getLastName())
                .withDisplayName(employeeRet.getName())
                .withEmployeeNumber(employeeRet.getListID())
                .withStandardAuthentication(employeeRet.getEmail(), null)
                .withChargeCode(null).withEmployeeRoles(null)
                .withEmailStatus(EmployeeStatus.PENDING_INVITE)
                .withEmployeeStatus(BooleanUtils.toBoolean(employeeRet.getIsActive()))
                .withEmployeeTypes(Arrays.asList(EmployeeType.getEmployeeType(employeeRet.getExempt())))
                .withUserId(employeeRet.getAccountNumber())
                .withHireDate(createDateTime(employeeRet.getHiredDate()))
                .build();
    }
    private OurDateTime createDateTime(String date) {
        return(date!=null ? new OurDateTime(new DateTime(date), TimeZone.getDefault()): null );

    }
}