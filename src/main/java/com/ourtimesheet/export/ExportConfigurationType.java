package com.ourtimesheet.export;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdus Salam on 8/10/2017.
 */
public enum ExportConfigurationType {
    QBO("QBO"),
    QBD("QBD"),
    CUSTOM("CUSTOM"),
    ADP_RUN("ADP RUN"),
    DELTEK("Deltek"),
    PAYCHEX("Paychex"),
    ADP_TOTAL_SOURCE("ADP Total Source"),
    INSPERITY("Insperity"),
    DEFAULT("default");

    private final String description;

    ExportConfigurationType(String description) {
        this.description = description;
    }

    public static List<String> getFileConfigurationTypes() {
        List<String> exportConfigurationTypes = new ArrayList<>();
        exportConfigurationTypes.add("ADP.TOTAL.SOURCE");
        exportConfigurationTypes.add("ADP.RUN");
        exportConfigurationTypes.add("INSPERITY");
        return exportConfigurationTypes;
    }

    public String getDescription() {
        return description;
    }
}