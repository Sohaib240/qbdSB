package com.hourtimesheet.transformer;

import com.hourtimesheet.desktop.PayrollItemWageRet;
import com.hourtimesheet.desktop.QBXML;
import com.ourtimesheet.paytype.PayType;
import com.ourtimesheet.qbd.domain.paytype.OvertimePay;
import com.ourtimesheet.qbd.domain.paytype.RegularPay;
import com.ourtimesheet.qbd.domain.paytype.SickPay;
import com.ourtimesheet.qbd.domain.paytype.VacationPay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Zohaib on 9/16/2015.
 */
public class PayRollTransformer extends BaseTransformer implements Transformer<String, List<PayType>> {

    private static final Logger LOG = LoggerFactory.getLogger(PayRollTransformer.class);

    @Override
    public List<PayType> transform(String s) throws Exception {
        LOG.warn("Entered into pay roll transformation");
        List<PayType> payTypes = new ArrayList<>();
        QBXML qbxmlType = transformToQBXML(s);
        List<PayrollItemWageRet> payrollItemWageRets = qbxmlType.getQBXMLMsgsRs().getPayrollItemWageQueryRs().getPayrollItemWageRet();
        payTypes.addAll(payrollItemWageRets.stream().map(this::createPayTypes).collect(Collectors.toList()));
        LOG.warn("Transformation done");
        return payTypes.stream().filter(payType -> payType != null).collect(Collectors.toList());
    }

    private PayType createPayTypes(PayrollItemWageRet payrollItemWageRet) {
        String wageType = payrollItemWageRet.getWageType();
        if (wageType.equals("SalaryRegular") || wageType.equals("HourlyRegular")) {
            return new RegularPay(UUID.randomUUID(), payrollItemWageRet.getName(), Boolean.valueOf(payrollItemWageRet.getIsActive()), payrollItemWageRet.getListID());
        } else if (wageType.equals("HourlyOvertime")) {
            return new OvertimePay(UUID.randomUUID(), payrollItemWageRet.getName(), Boolean.valueOf(payrollItemWageRet.getIsActive()), payrollItemWageRet.getListID());
        } else if (wageType.equals("SalarySick") || wageType.equals("HourlySick")) {
            return new SickPay(UUID.randomUUID(), payrollItemWageRet.getName(), Boolean.valueOf(payrollItemWageRet.getIsActive()), payrollItemWageRet.getListID());
        } else if (wageType.equals("SalaryVacation") || wageType.equals("HourlyVacation")) {
            return new VacationPay(UUID.randomUUID(), payrollItemWageRet.getName(), Boolean.valueOf(payrollItemWageRet.getIsActive()), payrollItemWageRet.getListID());
        } else {
            return null;
        }
    }
}