package com.ourtimesheet.overtimeConfiguration;

import com.ourtimesheet.common.Entity;
import com.ourtimesheet.paytype.PayType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Grace on 07-Mar-18.
 */
@Document(collection = "overtimeRule")
public abstract class OvertimeRule extends Entity {

    protected final List<OvertimeConfiguration> overtimeConfigurations;
    private final String name;

    public OvertimeRule(UUID id, String name, List<OvertimeConfiguration> overtimeConfigurations) {
        super(id);
        Assert.isTrue(StringUtils.isNotBlank(name), "Name cannot be null");
        this.name = name;
        Assert.isTrue(overtimeConfigurations.stream().filter(config -> config != null).count() > 0, "Overtime configuration cannot be empty");
        this.overtimeConfigurations = overtimeConfigurations;
    }


    public abstract boolean canValidateTimesheet();

    public abstract boolean hasPayType();

    public abstract List<PayType> getRegularPayTypes();


    public String getName() {
        return name;
    }

    public List<OvertimeConfiguration> getOvertimeConfigurations() {
        overtimeConfigurations.forEach(config -> config.updateRegularPayTypes(this.getRegularPayTypes()));
        return overtimeConfigurations.stream().sorted(Comparator.comparing(OvertimeConfiguration::getOrder)).collect(Collectors.toList());
    }

    public boolean isRegularPayType(PayType payType) {
        return payType != null && this.getRegularPayTypes().stream().anyMatch(regularPayType -> regularPayType.equals(payType));
    }

    public boolean isOvertimePayType(PayType payType) {
        return payType != null && getOvertimeConfigurations().stream().anyMatch(overtimeConfiguration -> overtimeConfiguration.getPayType().equals(payType));
    }

    public boolean isDailyOvertimePayType(PayType payType) {
        return payType != null && getOvertimeConfigurations().stream().filter(overtimeConfiguration -> (overtimeConfiguration.getOrder() == 0 && overtimeConfiguration.getPayType().equals(payType))).count() > 0;
    }


    private List<OvertimeConfiguration> getOvertimeConfiguration() {
        return overtimeConfigurations != null ? overtimeConfigurations : new ArrayList<>();
    }
}