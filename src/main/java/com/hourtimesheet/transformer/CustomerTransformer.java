package com.hourtimesheet.transformer;

import com.hourtimesheet.desktop.CustomerQueryRsType;
import com.hourtimesheet.desktop.CustomerRet;
import com.hourtimesheet.desktop.QBXML;
import com.hourtimesheet.desktop.QBXMLMsgsRs;
import com.ourtimesheet.qbd.domain.Customer;
import com.ourtimesheet.qbd.domain.CustomerJob;
import com.ourtimesheet.timesheet.chargeCode.ChargeCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by click chain 2 on 9/16/2015.
 */
public class CustomerTransformer extends BaseTransformer implements Transformer<String, List<Customer>> {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerTransformer.class);

    @Override
    public List<Customer> transform(String s) throws Exception {
        LOG.warn("Entered in customer transformer");
        List<Customer> customers = new ArrayList<>();
        List<CustomerRet> customerRets = getCustomerRets(s);
        customerRets.forEach(customerRet -> {
            if (customerRet.getParentRef() == null) {
                customers.add(createCustomer(customerRet));
            } else if (isSingleJob(customerRet)) {
                getCustomerByParentRef(customers, customerRet).addChild(createCustomerJob(customerRet));
            } else if (isHierarchicalJob(customerRet)) {
                Set<ChargeCode> customerJobs = new HashSet<>();
                extractCustomerJobsRecursive(getCustomerJobs(customers), customerJobs).stream().filter(customerJob1 -> ((CustomerJob) customerJob1).getQuickBooksId().equals(customerRet.getParentRef().getListID())).findAny().get();
                CustomerJob customerJob = (CustomerJob) customerJobs.stream().filter(customerJob1 -> ((CustomerJob) customerJob1).getQuickBooksId().equals(customerRet.getParentRef().getListID())).findAny().get();
                customerJob.addChild(createCustomerJob(customerRet));
            }
        });
        LOG.warn("Transformed customers");
        return customers;
    }

    private Set<ChargeCode> extractCustomerJobsRecursive(Set<ChargeCode> customerJobs, Set<ChargeCode> updatedCustomerJobs) {
        if (customerJobs.size() == 0) {
            return updatedCustomerJobs;
        } else {
            updatedCustomerJobs.addAll(customerJobs);
            Set<ChargeCode> childJobs = new HashSet<>();
            customerJobs.stream().forEach(customerJob -> childJobs.addAll(customerJob.children()));
            return extractCustomerJobsRecursive(childJobs, updatedCustomerJobs);
        }
    }

    private List<CustomerRet> getCustomerRets(String s) throws Exception {
        QBXMLMsgsRs qbxmlMsgsRs;
        CustomerQueryRsType customerQueryRsType;
        List<CustomerRet> customerRets = new ArrayList<>();
        QBXML qbxml = transformToQBXML(s);
        if (qbxml != null) {
            qbxmlMsgsRs = qbxml.getQBXMLMsgsRs();
            if (qbxmlMsgsRs != null) {
                customerQueryRsType = qbxmlMsgsRs.getCustomerQueryRs();
                if (customerQueryRsType != null) {
                    customerRets = customerQueryRsType.getCustomerRet();
                } else {
                    LOG.warn("customerQueryRsType is null");
                }
            } else {
                LOG.warn("qbxmlMsgsRs is null");
            }
        } else {
            LOG.warn("qbxml is null");
        }
        return customerRets;
    }

    private Set<ChargeCode> getCustomerJobs(List<Customer> customersToUpdate) {
        Set<ChargeCode> customerJobs = new HashSet<>();
        customersToUpdate.stream().forEach(customer -> customerJobs.addAll(customer.children()));
        return customerJobs;
    }

    private Customer getCustomerByParentRef(List<Customer> customers, CustomerRet customerRet) {
        return customers.stream().filter(customer -> org.apache.commons.lang.StringUtils.equals(customer.getQuickBooksId(), customerRet.getParentRef().getListID())).findAny().get();
    }

    private boolean isHierarchicalJob(CustomerRet customerRet) {
        return StringUtils.countOccurrencesOf(customerRet.getFullName(), ":") > 1;
    }

    private boolean isSingleJob(CustomerRet customerRet) {
        return StringUtils.countOccurrencesOf(customerRet.getFullName(), ":") == 1;
    }

    private Customer createCustomer(CustomerRet customerRet) {
        return new Customer(UUID.randomUUID(), customerRet.getName(), Boolean.valueOf(customerRet.getIsActive()), customerRet.getListID(), new HashSet<>());
    }

    private CustomerJob createCustomerJob(CustomerRet customerRet) {
        return new CustomerJob(UUID.randomUUID(), customerRet.getName(), customerRet.getListID(), Boolean.valueOf(customerRet.getIsActive()), new HashSet<>(), customerRet.getFullName(), customerRet.getParentRef().getListID());
    }
}