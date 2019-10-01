package com.ourtimesheet.expert;

import com.ourtimesheet.association.AuthorizeChargeAssociation;
import com.ourtimesheet.datetime.OurDateTime;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class AssociationFieldExpert {

    public Map<String, String> generateFields(AuthorizeChargeAssociation association, List<String> allChargeCodesTypes, TimeZone timeZone) {
        Map<String, String> chargeCodeTypeNameMap = new HashMap<>();
        final boolean[] hasInactiveChargeCode = {false};
        allChargeCodesTypes.forEach(type -> chargeCodeTypeNameMap.put(type, "zzzzzz"));
        association.getChargeCodes().forEach(chargeCode -> {
            switch (chargeCode.getChargeCodeName()) {
                case "Class":
                    chargeCodeTypeNameMap.put("Class Type", chargeCode.getName().toLowerCase());
                    break;
                case "Job":
                    if (StringUtils.isNotEmpty(chargeCode.getHierarchicalName()))
                        chargeCodeTypeNameMap.put("Customer", chargeCode.getHierarchicalName().toLowerCase());
                    else
                        chargeCodeTypeNameMap.put("Customer", chargeCodeTypeNameMap.get("Customer") + ":" + chargeCode.getName().toLowerCase());
                    break;
                default:
                    chargeCodeTypeNameMap.put(chargeCode.getChargeCodeName(), chargeCode.getName().toLowerCase());
                    break;
            }
            if (!chargeCode.isEffective(OurDateTime.getCurrentDate(timeZone)) || (association.getPayType() != null && !association.getPayType().isEffective(OurDateTime.getCurrentDate(timeZone)))) {
                hasInactiveChargeCode[0] = true;
            }
        });
        if (association.getPayType() != null) {
            chargeCodeTypeNameMap.put("Pay Type", association.getPayType().getName().toLowerCase());
        }
        chargeCodeTypeNameMap.put("searchString", getSearchString(chargeCodeTypeNameMap.values()));
        chargeCodeTypeNameMap.put("hasInactiveChargeCode", String.valueOf(hasInactiveChargeCode[0]));
        return chargeCodeTypeNameMap;
    }

    private String getSearchString(Collection<String> values) {
        final String[] searchString = {""};
        values.forEach(names -> searchString[0] += names + ">");
        return searchString[0];
    }
}