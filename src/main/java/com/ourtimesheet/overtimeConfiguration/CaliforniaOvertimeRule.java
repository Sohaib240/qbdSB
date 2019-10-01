package com.ourtimesheet.overtimeConfiguration;

import com.ourtimesheet.paytype.PayType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Abdus Salam on 3/14/2018.
 */
public class CaliforniaOvertimeRule extends OvertimeRule {

    public CaliforniaOvertimeRule(UUID id, String name, List<OvertimeConfiguration> overtimeConfigurations) {
        super(id, name, overtimeConfigurations);
    }


    @Override
    public boolean canValidateTimesheet() {
        return true;
    }

    @Override
    public boolean hasPayType() {
        return false;
    }

    @Override
    public List<PayType> getRegularPayTypes() {
        return new ArrayList<>();
    }


}