package com.ourtimesheet;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBRef;
import com.ourtimesheet.association.AuthorizeChargeAssociation;
import com.ourtimesheet.expert.AssociationFieldExpert;
import com.ourtimesheet.paytype.PayType;
import com.ourtimesheet.timesheet.chargeCode.ChargeCode;

import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class AssociationDocumentCreator {

    private final AssociationFieldExpert associationFieldExpert;

    public AssociationDocumentCreator(AssociationFieldExpert associationFieldExpert) {
        this.associationFieldExpert = associationFieldExpert;
    }

    public DBObject create(AuthorizeChargeAssociation association, List<String> allChargeCodesTypes, TimeZone timeZone) {
        BasicDBObject associationDocument = new BasicDBObject();
        associationDocument.put("_id", association.getId());
        associationDocument.put("chargeCodes", getDBRef(association.getChargeCodes()));
        associationDocument.put("_class", "com.ourtimesheet.association.AuthorizeChargeAssociation");
        associationDocument.put("payType", getPayTypeRef(association.getPayType()));
        associationDocument.put("active", association.isActive());
        associationDocument.put("billable", association.isBillable());
        Map<String, String> customFields = associationFieldExpert.generateFields(association, allChargeCodesTypes,timeZone);
        customFields.forEach(associationDocument::putIfAbsent);
        return associationDocument;
    }

    private Object getPayTypeRef(PayType payType) {
        return payType != null ? new DBRef("paytype", payType.getId()) : null;
    }

    private Object getDBRef(List<ChargeCode> chargeCodes) {
        return chargeCodes.stream().map(chargeCode -> new DBRef(chargeCode.getCollectionName(), chargeCode.getId())).collect(Collectors.toList());
    }
}
