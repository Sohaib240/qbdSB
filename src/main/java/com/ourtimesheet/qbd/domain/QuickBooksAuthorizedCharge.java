package com.ourtimesheet.qbd.domain;

import com.ourtimesheet.timesheet.chargeCode.AuthorizedCharge;
import com.ourtimesheet.timesheet.chargeCode.ChargeCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hassan on 6/7/16.
 */
public class QuickBooksAuthorizedCharge implements AuthorizedCharge {

    private final Customer customer;

    private final CustomerJob customerJob;

    private final ServiceItem serviceItem;

    private final QuickBooksClass quickBooksClass;

    @PersistenceConstructor
    public QuickBooksAuthorizedCharge(QuickBooksClass quickBooksClass, ServiceItem serviceItem, CustomerJob customerJob, Customer customer) {
        this.quickBooksClass = quickBooksClass;
        this.serviceItem = serviceItem;
        this.customerJob = customerJob;
        this.customer = customer;
    }

    @Override
    public List<ChargeCode> getAuthorizedCharge() {
        ArrayList<ChargeCode> result = new ArrayList<>();
        result.add(customer);
        result.add(customerJob);
        result.add(serviceItem);
        result.add(quickBooksClass);
        return result;
    }

    @Override
    public boolean isLeave() {
        return false;
    }

    @Override
    public int hashCode() {
        int result = customer != null ? customer.hashCode() : 0;
        result = customerJob != null ? (31 * result + customerJob.hashCode()) : 0;
        result = serviceItem != null ? (31 * result + serviceItem.hashCode()) : 0;
        result = quickBooksClass != null ? (31 * result + quickBooksClass.hashCode()) : 0;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuickBooksAuthorizedCharge quickBooksAuthorizedCharge = (QuickBooksAuthorizedCharge) o;

        return new EqualsBuilder()
            .append(customer, quickBooksAuthorizedCharge.customer)
            .append(customerJob, quickBooksAuthorizedCharge.customerJob)
            .append(serviceItem, quickBooksAuthorizedCharge.serviceItem)
            .append(quickBooksClass, quickBooksAuthorizedCharge.quickBooksClass)
            .isEquals();
    }

    @Override
    public String toString() {
        return StringUtils.substringBetween(getAuthorizedCharge().stream().filter(chargeCode ->  chargeCode != null).map(chargeCode -> chargeCode.getName()).collect(Collectors.toList()).toString(), "[", "]");
    }

    public static class Builder {

        private Customer customer;

        private CustomerJob customerJob;

        private ServiceItem serviceItem;

        private QuickBooksClass quickBooksClass;

        public Builder withChargeCodes(List<ChargeCode> chargeCodes) {
            for (ChargeCode chargeCode : chargeCodes) {
                if (chargeCode.getChargeCodeName().equals("Customer")) {
                    this.customer = Customer.class.cast(chargeCode);
                }
                if (chargeCode.getChargeCodeName().equals("Job")) {
                    this.customerJob = CustomerJob.class.cast(chargeCode);
                }
                if (chargeCode.getChargeCodeName().equals("Service Item")) {
                    this.serviceItem = ServiceItem.class.cast(chargeCode);
                }
                if (chargeCode.getChargeCodeName().equals("Class")) {
                    this.quickBooksClass = QuickBooksClass.class.cast(chargeCode);
                }
            }
            return this;
        }

        public QuickBooksAuthorizedCharge createQuickBooksAuthorizedCharge() {
            return new QuickBooksAuthorizedCharge(quickBooksClass, serviceItem, customerJob, customer);
        }
    }
}
