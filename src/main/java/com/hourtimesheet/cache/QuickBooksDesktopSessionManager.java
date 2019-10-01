package com.hourtimesheet.cache;

import com.ourtimesheet.accounting.domain.AccountingConfiguration;
import com.ourtimesheet.accounting.service.AccountingService;
import com.ourtimesheet.data.QBDConnectionSessionDO;
import com.ourtimesheet.multitenant.CompanyHolder;
import com.ourtimesheet.qbd.domain.QuickBooksDesktopConfiguration;
import com.ourtimesheet.qbd.domain.QuickBooksDesktopConnection;
import com.ourtimesheet.util.MasterConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Abdus Salam on 2/7/2017.
 */
public class QuickBooksDesktopSessionManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuickBooksDesktopSessionManager.class);
    private final AccountingService accountingService;
    private final MasterConfigUtils masterConfigUtils;

    private final Map<String, Boolean> perSessionImportRequestTrackerMap = new HashMap<>();

    public QuickBooksDesktopSessionManager(AccountingService accountingService, MasterConfigUtils masterConfigUtils) {
        this.accountingService = accountingService;
        this.masterConfigUtils = masterConfigUtils;
    }

    public void updateAccessTokenForCompany(String accessTokenForCompany, String companyName, String error) {
        CompanyHolder.set(companyName);
        AccountingConfiguration accountingConfiguration = accountingService.findAccountingConfiguration();
        QuickBooksDesktopConfiguration configuration = (QuickBooksDesktopConfiguration) accountingConfiguration;
        try {
            accountingService.addAccountingConfiguration(new QuickBooksDesktopConfiguration(configuration.getId(), configuration.getQuickBooksImportPreferences(),
                    new QuickBooksDesktopConnection(accessTokenForCompany, true), configuration.getPayTypeConfiguration()));
            masterConfigUtils.saveQBDConnectionSessionDOForCompany(createQbdConnectionSession(accessTokenForCompany, companyName, error));

        } catch (Exception ex) {
            LOGGER.error("Error while updating token for company with error {}", ex.getMessage());
        }
    }

    public void expireAccessToken(String accessToken) {
        if (!accessToken.equalsIgnoreCase("nvu")) {
            try {
                masterConfigUtils.removeQBDConnectionSessionDOForCompany(accessToken);
            } catch (Exception ex) {
                LOGGER.error("Error in removing qbd connection session for company with error {}", ex.getMessage());
            }
        }
    }

    public String getCompanyNameAgainstToken(String accessToken) {
        try {
            QBDConnectionSessionDO session = masterConfigUtils.getQBDConnectionSessionDOById(UUID.fromString(accessToken));
            return session != null ? session.getCompanyName() : null;
        } catch (Exception e) {
            LOGGER.error("Error in getting company name against token {}", e.getMessage());
            return null;
        }
    }

    public String getTokenAgainstCompanyName(String companyName) {
        try {
            QBDConnectionSessionDO session = masterConfigUtils.getQBDConnectionSessionDOByCompanyName(companyName);
            return session != null ? session.getId().toString() : null;
        } catch (Exception ex) {
            LOGGER.error("Error in getting token with error {}", ex.getMessage());
            return null;
        }
    }


    private QBDConnectionSessionDO createQbdConnectionSession(String accessTokenForCompany, String companyName, String error) {
        return new QBDConnectionSessionDO(UUID.fromString(accessTokenForCompany), companyName, error);
    }

    public void setImportProcessRequestedStatus(String ticket, boolean hasImportProcessInitiated) {
        try {
            perSessionImportRequestTrackerMap.put(ticket, hasImportProcessInitiated);
        } catch (Exception ex) {
            LOGGER.error("Error while setting import request with error {}", ex.getMessage());
        }
    }

    public Boolean hasImportProcessRequested(String ticket) {
        try {
            return perSessionImportRequestTrackerMap.get(ticket);
        } catch (Exception ex) {
            LOGGER.error("Error in checking per session import with error {}", ex.getMessage());
            return false;
        }
    }
}
