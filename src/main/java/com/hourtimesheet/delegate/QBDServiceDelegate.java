package com.hourtimesheet.delegate;

import com.google.common.annotations.VisibleForTesting;
import com.hourtimesheet.cache.QuickBooksDesktopSessionManager;
import com.hourtimesheet.expert.UserAuthenticationExpert;
import com.hourtimesheet.facade.QBDesktopFacade;
import com.hourtimesheet.validation.InvalidUserException;
import com.ourtimesheet.accounting.service.AccountingService;
import com.ourtimesheet.multitenant.CompanyHolder;
import com.ourtimesheet.qbd.query.*;
import com.ourtimesheet.qbd.query.hourExport.LeaveHourSyncRequest;
import com.ourtimesheet.qbd.query.hourExport.OverTimeHoursSyncRequest;
import com.ourtimesheet.qbd.query.hourExport.RegularHoursSyncRequest;
import com.ourtimesheet.qbd.service.QuickBooksDesktopService;
import com.ourtimesheet.repository.AuthorizeChargeAssociationRepository;
import com.ourtimesheet.service.CompanyService;
import com.ourtimesheet.service.EmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by Abdus Salam on 2/3/2017.
 */
public class QBDServiceDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(QBDServiceDelegate.class);

    private final EmployeeService employeeService;
    private final QuickBooksDesktopService quickBooksDesktopService;
    private final QuickBooksDesktopSessionManager quickBooksDesktopSessionManager;
    private final QBDesktopFacade qbDesktopFacade;
    private final NotificationDelegate notificationDelegate;
    private final UserAuthenticationExpert userAuthenticationExpert;
    private final AuthorizeChargeAssociationRepository authorizeChargeAssociationRepository;
    private final AccountingService accountingService;
    private final CompanyService companyService;

    public QBDServiceDelegate(EmployeeService employeeService, QuickBooksDesktopService quickBooksDesktopService, QuickBooksDesktopSessionManager quickBooksDesktopSessionManager, QBDesktopFacade qbDesktopFacade, NotificationDelegate notificationDelegate, UserAuthenticationExpert userAuthenticationExpert, AuthorizeChargeAssociationRepository authorizeChargeAssociationRepository, AccountingService accountingService, CompanyService companyService) {
        this.employeeService = employeeService;
        this.quickBooksDesktopService = quickBooksDesktopService;
        this.quickBooksDesktopSessionManager = quickBooksDesktopSessionManager;
        this.qbDesktopFacade = qbDesktopFacade;
        this.notificationDelegate = notificationDelegate;
        this.userAuthenticationExpert = userAuthenticationExpert;
        this.authorizeChargeAssociationRepository = authorizeChargeAssociationRepository;
        this.accountingService = accountingService;
        this.companyService = companyService;
    }

    public String authenticate(String strUserName, String strPassword) {
        return authenticateAdminUser(strUserName, strPassword) ? getAccessToken(extractCompanyName(strUserName)) : "nvu";
    }

    private String getAccessToken(String companyName) {
        String accessToken = quickBooksDesktopSessionManager.getTokenAgainstCompanyName(companyName);
        if (accessToken == null) {
            accessToken = UUID.randomUUID().toString();
            quickBooksDesktopSessionManager.updateAccessTokenForCompany(accessToken, companyName, null);
        }
        return accessToken;
    }

    @VisibleForTesting
    boolean authenticateAdminUser(String userDetail, String strPassword) {
        String companyName = extractCompanyName(userDetail);
        String emailAddress = StringUtils.substringBeforeLast(userDetail, "@");
        try {
            userAuthenticationExpert.authenticate(emailAddress, strPassword, companyName);
            return true;
        } catch (InvalidUserException e) {
            LOGGER.error("companyName " + companyName + " Error in authenticating admin " + emailAddress + " user with error {}", e.getMessage());
            return false;
        }
    }

    private String extractCompanyName(String userDetail) {
        return StringUtils.substringAfterLast(userDetail, "@");
    }

    public String getQueryStringForNextRequest(String ticket) {
        if (!ticket.equalsIgnoreCase("nvu")) {
            String companyName = quickBooksDesktopSessionManager.getCompanyNameAgainstToken(ticket);
            if (companyName != null) {
                CompanyHolder.set(companyName);
                try {
                    QBDRequest qbdRequest = quickBooksDesktopService.findPreferenceQuery();
                    if (qbdRequest != null && qbdRequest.isImportRequest()) {
                        quickBooksDesktopSessionManager.setImportProcessRequestedStatus(ticket, true);
                    }
                    return qbdRequest != null ? qbdRequest.generateQuery() : "";
                } catch (Exception ex) {
                    LOGGER.error("Error in getting query from request with error {}", ex.getMessage());
                }
            }
        }
        return "";
    }

    public boolean isAnyQueryToBeProcessed(String ticket) {
        return StringUtils.isNotBlank(getQueryStringForNextRequest(ticket));
    }

    public void endSession(String ticket) {
        quickBooksDesktopSessionManager.expireAccessToken(ticket);
    }

    public void saveEntities(String rawData, String ticket) {
        String companyName = quickBooksDesktopSessionManager.getCompanyNameAgainstToken(ticket);
        CompanyHolder.set(companyName);
        QBDRequest qbdRequest = quickBooksDesktopService.findPreferenceQuery();
        try {
            if (qbdRequest.getClass().isAssignableFrom(EmployeeSyncRequest.class)) {
                LOGGER.info("import process started for " + companyName);
                employeeService.inactiveAllWithEmployeeNumber();
                employeeService.updateAllEmployees(qbDesktopFacade.extractEmployees(rawData));
            } else if (qbdRequest.getClass().isAssignableFrom(VendorSyncRequest.class)) {
                employeeService.updateAllEmployees(qbDesktopFacade.extractVendors(rawData));
            } else if (qbdRequest.getClass().isAssignableFrom(ClassSyncRequest.class)) {
                quickBooksDesktopService.inactiveAllClasses();
                quickBooksDesktopService.saveClasses(qbDesktopFacade.extractClasses(rawData));
            } else if (qbdRequest.getClass().isAssignableFrom(ServiceItemSyncRequest.class)) {
                quickBooksDesktopService.inactiveAllServiceItems();
                quickBooksDesktopService.saveServiceItems(qbDesktopFacade.extractServiceItems(rawData));
            } else if (qbdRequest.getClass().isAssignableFrom(CustomerJobSyncRequest.class)) {
                quickBooksDesktopService.inactiveAllCustomers();
                quickBooksDesktopService.inactiveAllCustomerJobs();
                quickBooksDesktopService.saveCustomers(qbDesktopFacade.extractCustomers(rawData));
            } else if (qbdRequest.getClass().isAssignableFrom(PayrollItemSyncRequest.class)) {
                quickBooksDesktopService.inactiveAllPayRolls();
                quickBooksDesktopService.savePayRollItems(qbDesktopFacade.extractPayRollItems(rawData));
            } else if (qbdRequest.getClass().isAssignableFrom(OverTimeHoursSyncRequest.class) || qbdRequest.getClass().isAssignableFrom(RegularHoursSyncRequest.class) || qbdRequest.getClass().isAssignableFrom(LeaveHourSyncRequest.class)) {
                qbDesktopFacade.extractHoursSyncResponse(rawData);
            }
            quickBooksDesktopService.saveImportRequest(qbdRequest.onSuccess());
        } catch (Exception e) {
            LOGGER.error("{}" + " failed with error message: " + "{}", qbdRequest.getDescription(), e.getMessage());
            quickBooksDesktopService.saveImportRequest(qbdRequest.onError(e.getMessage()));
        }
    }

    public void sendFinishEmail(String ticket) {
        Boolean hasImportProcessRequested = quickBooksDesktopSessionManager.hasImportProcessRequested(ticket);
        if (hasImportProcessRequested != null && hasImportProcessRequested) {
            String companyName = quickBooksDesktopSessionManager.getCompanyNameAgainstToken(ticket);
            if (companyName != null) {
                CompanyHolder.set(companyName);
                employeeService.findAdmins().forEach(notificationDelegate::sendImportCompleteNotification);
                quickBooksDesktopSessionManager.setImportProcessRequestedStatus(ticket, false);
                authorizeChargeAssociationRepository.saveAll(authorizeChargeAssociationRepository.findList(), accountingService.findAllChargeCodesTypes(), companyService.getCompany().getTimeZone());
            }
        }
    }
}