package com.hourtimesheet.config;

import com.ourtimesheet.accounting.repository.AccountingConfigurationRepository;
import com.ourtimesheet.accounting.service.AccountingService;
import com.ourtimesheet.accounting.service.AccountingServiceFactory;
import com.ourtimesheet.accounting.service.AccountingServiceImpl;
import com.ourtimesheet.config.ApplicationConfig;
import com.ourtimesheet.qbd.expert.*;
import com.ourtimesheet.qbd.repository.*;
import com.ourtimesheet.qbd.service.QuickBooksDesktopService;
import com.ourtimesheet.qbd.service.QuickBooksDesktopServiceImpl;
import com.ourtimesheet.repository.CompanyRepository;
import com.ourtimesheet.repository.EmployeeRepository;
import com.ourtimesheet.repository.JobRepository;
import com.ourtimesheet.repository.TimesheetRepository;
import com.ourtimesheet.repository.payType.PayTypeRepository;
import com.ourtimesheet.repository.superAdmin.SuperAdminRepository;
import com.ourtimesheet.service.*;
import com.ourtimesheet.service.superAdmin.SuperAdminService;
import com.ourtimesheet.service.superAdmin.SuperAdminServiceImpl;
import com.ourtimesheet.util.MasterConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@Import({PersistenceConfiguration.class, ApplicationConfig.class})
public class ServiceConfiguration {


    @Bean
    @Autowired
    public CompanyService companyService(CompanyRepository companyRepository) {
        return new DefaultCompanyServiceImpl(companyRepository);
    }

    @Bean
    @Autowired
    public EmployeeService employeeService(EmployeeRepository employeeRepository) {
        return new DefaultEmployeeServiceImpl(employeeRepository);
    }

    @Bean
    @Autowired
    public TimesheetService timesheetService(TimesheetRepository timesheetRepository) {
        return new DefaultTimesheetServiceImpl(timesheetRepository);
    }

    @Bean
    @Autowired
    public QuickBooksDesktopExpert quickBooksDesktopExpert(MongoTemplate mongoTemplate, MasterConfigUtils masterConfigUtils) {
        return new QuickBooksDesktopExpert(mongoTemplate, masterConfigUtils);
    }

    @Bean
    @Autowired
    public SuperAdminService superAdminService(SuperAdminRepository superAdminRepository) {
        return new SuperAdminServiceImpl(superAdminRepository);
    }

    @Bean
    @Autowired
    public UserService userService(EmployeeService employeeService, SuperAdminService superAdminService) {
        return new UserServiceImpl(employeeService, superAdminService);
    }

    @Bean
    @Autowired
    public QuickBooksDesktopService quickBooksDesktopService(QBDRequestRepository qbdRequestRepository, QbdCustomerExpert qbdCustomerExpert, QbdCustomerJobExpert qbdCustomerJobExpert, QbdClassExpert qbdClassExpert, QbdServiceItemExpert qbdServiceItemExpert, QbdPayTypeExpert qbdPayTypeExpert) {
        return new QuickBooksDesktopServiceImpl(qbdRequestRepository, qbdCustomerExpert, qbdCustomerJobExpert, qbdClassExpert, qbdServiceItemExpert, qbdPayTypeExpert);
    }

    @Bean
    @Autowired
    public DesktopTimeActivityExpert desktopTimeActivityExpert(QBDRequestRepository qbdRequestRepository, JobRepository jobRepository) {
        return new DesktopTimeActivityExpert(qbdRequestRepository, jobRepository);
    }

    @Bean
    @Autowired
    public QbdCustomerExpert qbdCustomerExpert(DesktopCustomerRepository customerRepository, QbdCustomerJobExpert qbdCustomerJobExpert) {
        return new QbdCustomerExpert(customerRepository, qbdCustomerJobExpert);
    }

    @Bean
    @Autowired
    public QbdCustomerJobExpert qbdCustomerJobExpert(DesktopCustomerJobRepository customerJobRepository) {
        return new QbdCustomerJobExpert(customerJobRepository);
    }

    @Bean
    @Autowired
    public QbdServiceItemExpert qbdServiceItemExpert(DesktopServiceItemRepository desktopServiceItemRepository) {
        return new QbdServiceItemExpert(desktopServiceItemRepository);
    }

    @Bean
    @Autowired
    public AccountingService accountingService(QuickBooksDesktopExpert quickBooksDesktopExpert, AccountingConfigurationRepository accountingConfigurationRepository) {
        return new AccountingServiceImpl(new AccountingServiceFactory(quickBooksDesktopExpert), accountingConfigurationRepository);
    }

    @Bean
    @Autowired
    public QbdClassExpert qbdClassExpert(DesktopClassRepository desktopClassRepository) {
        return new QbdClassExpert(desktopClassRepository);
    }

    @Bean
    @Autowired
    public QbdPayTypeExpert qbdPayTypeExpert(PayTypeRepository payTypeRepository) {
        return new QbdPayTypeExpert(payTypeRepository);
    }
}
