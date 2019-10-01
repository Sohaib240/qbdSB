package com.ourtimesheet.overtimeConfiguration;

import com.ourtimesheet.paytype.PayType;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Abdus Salam on 3/14/2018.
 */
public class DefaultOvertimeRule extends OvertimeRule {

    @DBRef
    private final List<PayType> paytypes;
    private final boolean validate;

    public DefaultOvertimeRule(UUID id, String name, List<PayType> paytypes, List<OvertimeConfiguration> overtimeConfigurations, boolean validate) {
        super(id, name, overtimeConfigurations);
        this.paytypes = paytypes;
        this.validate = validate;
    }

    @Override
    public boolean canValidateTimesheet() {
        return validate;
    }

    @Override
    public boolean hasPayType() {
        return !getRegularPayTypes().isEmpty() || overtimeConfigurations.stream().filter(config -> !config.hasPayType()).count() == 0;
    }

    @Override
    public List<PayType> getRegularPayTypes() {
        return paytypes != null ? paytypes : new ArrayList<>();
    }

}