package com.ourtimesheet.profile;

import com.ourtimesheet.CompanyDataCategory;
import com.ourtimesheet.common.Entity;
import com.ourtimesheet.security.AuthenticationType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

/**
 * Created by Talha Zahid on 2/26/2016.
 */

@Document(collection = "company")
public class Company extends Entity {

    private final String name;

    private final String domainName;

    private final TimeZone timeZone;

    private final Address address;

    private final ContactPerson contactPerson;

    private WizardInfo wizardInfo;

    private List<AuthenticationType> clockAuthenticationTypes;

    private CompanyDataCategory companyDataCategory;

    private boolean scheduleIndicator;

    @PersistenceConstructor
    private Company(UUID id, String name, String domainName, TimeZone timeZone, Address address, ContactPerson contactPerson, WizardInfo wizardInfo,boolean scheduleIndicator) {
        super(id);
        this.name = name;
        this.domainName = domainName;
        this.timeZone = timeZone;
        this.address = address;
        this.contactPerson = contactPerson;
        this.wizardInfo = wizardInfo;
        this.scheduleIndicator = scheduleIndicator;
    }

    public void setWizardInfo(WizardInfo wizardInfo) {
        this.wizardInfo = wizardInfo;
    }

    public void updateDataCategory(CompanyDataCategory companyDataCategory) {
        this.companyDataCategory = companyDataCategory;
    }

    public CompanyDataCategory getCompanyDataCategory() {
        return companyDataCategory;
    }

    public String getName() {
        return name;
    }

    public String getDomainName() {
        return domainName;
    }

    public String extractCompanyDatabaseName() throws MalformedURLException {
        return StringUtils.substringBetween(domainName, "//", ".");
    }



    public TimeZone getTimeZone() {
        return timeZone;
    }

    public Address getAddress() {
        return address;
    }

    public ContactPerson getContactPerson() {
        return contactPerson;
    }

    public WizardStatus getWizardStatus() {
        return wizardInfo.getWizardStatus();
    }

    public boolean isWizardFinish() {
        return wizardInfo.getWizardStatus().equals(WizardStatus.FINISHED);
    }


    public void updateWizardInfo(WizardStatus wizardStatus) {
        wizardInfo.updateWizardStatus(wizardStatus);
    }

    public boolean isWizardFinalStep() {
        return wizardInfo.getWizardStatus() != null && wizardInfo.getWizardStatus().equals(WizardStatus.CHOOSE_ADMIN);
    }

    public void setClockAuthenticationTypes(List<AuthenticationType> clockAuthenticationTypes) {
        this.clockAuthenticationTypes = clockAuthenticationTypes;
    }

    public void addClockAuthenticationType(AuthenticationType clockAuthenticationType) {
        if (this.clockAuthenticationTypes == null) {
            this.clockAuthenticationTypes = new ArrayList<>();
        }
        this.clockAuthenticationTypes.add(clockAuthenticationType);
    }

    public void removeClockAuthenticationType(String clockAuthenticationType) {
        AuthenticationType clockAuthenticationToRemove = AuthenticationType.getClockAuthenticationType(clockAuthenticationType);
        if (this.clockAuthenticationTypes != null && !this.clockAuthenticationTypes.isEmpty()) {
            this.clockAuthenticationTypes.remove(clockAuthenticationToRemove);
        }
    }

    public boolean getScheduleIndicator() {
        return scheduleIndicator;
    }

    public void setScheduleIndicator(boolean scheduleIndicator) {
        this.scheduleIndicator = scheduleIndicator;
    }


    public List<AuthenticationType> getAuthenticationTypes() {
        return this.clockAuthenticationTypes != null ? this.clockAuthenticationTypes : new ArrayList<>();
    }

    public static class Builder {

        private UUID id;

        private String name;

        private String domainName;

        private TimeZone timeZone;

        private Address address;

        private ContactPerson contactPerson;

        private WizardInfo wizardInfo;

        private boolean scheduleIndicator;

        public Builder(UUID id, String name, WizardStatus wizardStatus) {
            Assert.isTrue(StringUtils.isNotBlank(name));
            Assert.isTrue(id != null);
            this.id = id;
            this.name = name;
            this.wizardInfo = new WizardInfo(wizardStatus);
        }

        public Builder(String name) {
            Assert.isTrue(StringUtils.isNotBlank(name));
            this.name = name;
        }

        public Builder withDomainName(String domainName) {
            this.domainName = domainName;
            return this;
        }

        public Builder withTimeZone(final TimeZone timeZone) {
            this.timeZone = timeZone;
            return this;
        }

        public Builder withAddress(final Address address) {
            this.address = address;
            return this;
        }

        public Builder withContactPerson(final ContactPerson contactPerson) {
            this.contactPerson = contactPerson;
            return this;
        }
        public Builder withScheduleIndicator(final boolean scheduleIndicator) {
            this.scheduleIndicator = scheduleIndicator;
            return this;
        }

        public Company createCompany() {
            return new Company(id, name, domainName, timeZone, address, contactPerson, wizardInfo,scheduleIndicator);
        }
    }
}