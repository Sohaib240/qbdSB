package com.hourtimesheet.config;

import com.hourtimesheet.cache.QuickBooksDesktopSessionManager;
import com.hourtimesheet.callBack.*;
import com.hourtimesheet.delegate.NotificationDelegate;
import com.hourtimesheet.delegate.QBDServiceDelegate;
import com.hourtimesheet.delegate.QuickbooksDesktopDelegate;
import com.hourtimesheet.encryption.Encryptor;
import com.hourtimesheet.expert.CompanyAuthenticationExpert;
import com.hourtimesheet.expert.UserAuthenticationExpert;
import com.hourtimesheet.expert.WebConnectorFileExpert;
import com.hourtimesheet.facade.QBDesktopFacade;
import com.hourtimesheet.facade.QBDesktopFacadeImpl;
import com.hourtimesheet.factory.TransformerFactory;
import com.hourtimesheet.factory.VelocityContextFactory;
import com.hourtimesheet.handler.HoursExportExpert;
import com.hourtimesheet.handler.RequestHandler;
import com.ourtimesheet.accounting.service.AccountingService;
import com.ourtimesheet.notification.builder.ExportProcessCompleteNotificationBuilder;
import com.ourtimesheet.notification.builder.ExportProcessFailureNotificationBuilder;
import com.ourtimesheet.notification.builder.ImportProcessCompleteNotificationBuilder;
import com.ourtimesheet.notification.service.NotificationService;
import com.ourtimesheet.qbd.repository.QBDRequestRepository;
import com.ourtimesheet.qbd.service.QuickBooksDesktopService;
import com.ourtimesheet.repository.*;
import com.ourtimesheet.service.CompanyService;
import com.ourtimesheet.service.EmployeeService;
import com.ourtimesheet.service.TimesheetService;
import com.ourtimesheet.service.UserService;
import com.ourtimesheet.transformer.EmployeeTransformer;
import com.ourtimesheet.util.MasterConfigUtils;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Created by hassan on 2/5/17.
 */
@Configuration
@Import({JobScheduleConfiguration.class, ConnectorConfiguration.class, NotificationConfiguration.class, CronJobConfiguration.class, EncryptionConfig.class, RestTemplate.class})
public class MainConfiguration {

    @Autowired
    private EmployeeService employeeService;

    @Bean
    @Autowired
    public QBDServiceDelegate qbdServiceDelegate(QuickBooksDesktopService quickBooksDesktopService, QuickBooksDesktopSessionManager quickBooksDesktopSessionManager, QBDesktopFacade qbDesktopFacade, NotificationDelegate notificationDelegate, UserAuthenticationExpert userAuthenticationExpert, AuthorizeChargeAssociationRepository authorizeChargeAssociationRepository, AccountingService accountingService, CompanyService companyService) {
        return new QBDServiceDelegate(employeeService, quickBooksDesktopService, quickBooksDesktopSessionManager, qbDesktopFacade, notificationDelegate, userAuthenticationExpert, authorizeChargeAssociationRepository, accountingService, companyService);
    }

    @Bean
    @Autowired
    public QuickBooksDesktopSessionManager quickBooksDesktopAccessTokenCache(AccountingService accountingService, MasterConfigUtils masterConfigUtils) {
        return new QuickBooksDesktopSessionManager(accountingService, masterConfigUtils);
    }

    @Bean
    @Autowired
    public QBDesktopFacade qbDesktopFacade(QuickBooksDesktopService quickBooksDesktopService, EmployeeRepository employeeRepository, CompanyService companyService) {
        return new QBDesktopFacadeImpl(new TransformerFactory(quickBooksDesktopService), new EmployeeTransformer(employeeRepository), employeeService, companyService);
    }

    @Bean
    @Autowired
    public WebConnectorFileExpert webConnectorFileExpert(VelocityContextFactory velocityContextFactory) throws IOException {
        VelocityEngine velocity = new VelocityEngine();
        velocity.init();
        Template template = velocity.getTemplate("quickbooksConnector.vm");
        template.process();
        return new WebConnectorFileExpert(template, velocityContextFactory);
    }

    @Bean
    @Autowired
    public VelocityContextFactory velocityContextFactory(ConnectorConfiguration connectorConfiguration) {
        return new VelocityContextFactory(connectorConfiguration);
    }

    @Bean
    @Autowired
    public NotificationDelegate importProcessNotificationDelegate(NotificationService notificationService, ImportProcessCompleteNotificationBuilder ImportProcessCompleteNotificationBuilder) {
        return new NotificationDelegate(notificationService, ImportProcessCompleteNotificationBuilder);

    }

    @Bean
    @Autowired
    public RequestHandler requestHandler(JobRepository jobRepository, HoursExportExpert hoursExportExpert) {
        return new RequestHandler(jobRepository, hoursExportExpert);
    }

    @Bean
    @Autowired
    public HoursExportExpert hoursExportExpert(HoursExportSuccessJobCallBack hoursExportSuccessJobCallBack, HoursExportFailureJobCallBack hoursExportFailureJobCallBack, HourExportProcessingJobCallBack hourExportProcessingJobCallBack, NotificationService notificationService) {
        return new HoursExportExpert(hoursExportSuccessJobCallBack, hoursExportFailureJobCallBack, hourExportProcessingJobCallBack, notificationService);
    }


    @Bean
    @Autowired
    public TimesheetExportSuccessJobCallBack timesheetExportSuccessJobCallBack(TimesheetRepository timesheetRepository, CompanyRepository companyRepository) {
        return new TimesheetExportSuccessJobCallBack(timesheetRepository, companyRepository);
    }

    @Bean
    @Autowired
    public TimesheetExportFailureJobCallBack timesheetExportFailureJobCallBack(QBDRequestRepository qbdRequestRepository, TimesheetService timesheetService) {
        return new TimesheetExportFailureJobCallBack(qbdRequestRepository, timesheetService);
    }

    @Bean
    @Autowired
    public HoursExportSuccessJobCallBack hoursExportSuccessJobCallBack(JobRepository jobRepository, TimesheetExportSuccessJobCallBack timesheetExportSuccessJobCallBack, TimesheetExportFailureJobCallBack timesheetExportFailureJobCallBack, ExportProcessCompleteNotificationBuilder exportProcessCompleteNotificationBuilder) {
        return new HoursExportSuccessJobCallBack(jobRepository, timesheetExportSuccessJobCallBack, timesheetExportFailureJobCallBack, exportProcessCompleteNotificationBuilder);
    }

    @Bean
    @Autowired
    public HoursExportFailureJobCallBack hoursExportFailureJobCallBack(JobRepository jobRepository, TimesheetExportFailureJobCallBack timesheetExportFailureJobCallBack, ExportProcessFailureNotificationBuilder exportProcessFailureNotificationBuilder, EmployeeService employeeService) {
        return new HoursExportFailureJobCallBack(jobRepository, timesheetExportFailureJobCallBack, exportProcessFailureNotificationBuilder, employeeService);
    }

    @Bean
    @Autowired
    public UserAuthenticationExpert authenticationExpert(UserService userService, Encryptor encryptor) {
        return new UserAuthenticationExpert(userService, encryptor, new BCryptPasswordEncoder());
    }

    @Bean
    @Autowired
    public CompanyAuthenticationExpert companyAuthenticationExpert(MasterConfigUtils masterConfigUtils) {
        return new CompanyAuthenticationExpert(masterConfigUtils);
    }

    @Bean
    @Autowired
    public QuickbooksDesktopDelegate quickbooksDesktopDelegate(WebConnectorFileExpert webConnectorFileExpert, UserAuthenticationExpert userAuthenticationExpert, CompanyAuthenticationExpert companyAuthenticationExpert) {
        return new QuickbooksDesktopDelegate(webConnectorFileExpert, userAuthenticationExpert, companyAuthenticationExpert);
    }
}