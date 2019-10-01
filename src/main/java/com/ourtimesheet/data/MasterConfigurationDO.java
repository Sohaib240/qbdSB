package com.ourtimesheet.data;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.profile.ClockTerminal;
import com.ourtimesheet.profile.RawSignUpData;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by hassan on 3/14/16.
 */
@Document(collection = "masterConfiguration")
public class MasterConfigurationDO {

    @Id
    private final String subDomainName;

    private final String databaseName;

    private final String adminEmail;

    private final OurDateTime companyCreationDate;

    private final RawSignUpData rawSignUpData;

    private final OpenIdInfo openIdInfo;

    private String authToken;

    private List<ClockTerminal> clockTerminals;


    @PersistenceConstructor
    public MasterConfigurationDO(String subDomainName, String databaseName, String adminEmail, OurDateTime companyCreationDate, RawSignUpData rawSignUpData, OpenIdInfo openIdInfo, String authToken) {
        this.subDomainName = subDomainName;
        this.databaseName = databaseName;
        this.adminEmail = adminEmail;
        this.companyCreationDate = companyCreationDate;
        this.rawSignUpData = rawSignUpData;
        this.openIdInfo = openIdInfo;
        this.authToken = authToken;
    }

    public String getSubDomainName() {
        return subDomainName;
    }

    public String getDatabaseName() {
        return databaseName;
    }
}
