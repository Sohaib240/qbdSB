package com.hourtimesheet.transformer;

import com.hourtimesheet.desktop.QBXML;
import com.hourtimesheet.desktop.VendorRet;
import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.employee.EmployeeStatus;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by click chain 2 on 9/16/2015.
 */
public class VendorTransformer extends BaseTransformer implements Transformer<String, List<Employee>> {

    @Override
    public List<Employee> transform(String s) throws Exception {
        List<Employee> employees = new ArrayList<>();
        QBXML qbxmlType = transformToQBXML(s);
        List<VendorRet> vendorRets = qbxmlType.getQBXMLMsgsRs().getVendorQueryRs().getVendorRet();
        employees.addAll(vendorRets.stream().map(this::createEmployee).collect(Collectors.toList()));
        return employees;
    }

    private Employee createEmployee(VendorRet vendorRet) {
        return new Employee.Builder(vendorRet.getFirstName(), vendorRet.getLastName()).withDisplayName(vendorRet.getName()).withEmployeeNumber(vendorRet.getListID()).withStandardAuthentication(vendorRet.getEmail(), null)
                .withChargeCode(null).withEmployeeRoles(null).withEmailStatus(EmployeeStatus.PENDING_INVITE).withEmployeeStatus(BooleanUtils.toBoolean(vendorRet.getIsActive()))
                .build();
    }
}
