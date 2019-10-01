package com.hourtimesheet.config;

import com.mongodb.MongoClient;
import com.ourtimesheet.AssociationDocumentCreator;
import com.ourtimesheet.accounting.repository.AccountingConfigurationRepository;
import com.ourtimesheet.accounting.repository.AccountingConfigurationRepositoryImpl;
import com.ourtimesheet.accounting.repositorydo.AccountingConfigurationDORepository;
import com.ourtimesheet.converter.OurDateTimeReadConverter;
import com.ourtimesheet.converter.OurDateTimeWriteConverter;
import com.ourtimesheet.expert.AssociationFieldExpert;
import com.ourtimesheet.multitenant.MultiTenantMongoDbFactory;
import com.ourtimesheet.qbd.repository.*;
import com.ourtimesheet.qbd.repositorydo.*;
import com.ourtimesheet.repository.*;
import com.ourtimesheet.repository.payType.PayTypeRepository;
import com.ourtimesheet.repository.payType.PayTypeRepositoryImpl;
import com.ourtimesheet.repository.superAdmin.SuperAdminRepository;
import com.ourtimesheet.repository.superAdmin.SuperAdminRepositoryImpl;
import com.ourtimesheet.repositorydo.*;
import com.ourtimesheet.util.DefaultMasterConfigUtils;
import com.ourtimesheet.util.MasterConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.mapping.model.PropertyNameFieldNamingStrategy;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@Import({MongoConfiguration.class, MultitenantAspectConfig.class})
public class PersistenceConfiguration {

    @Autowired
    private String defaultDatabaseName;

    @Autowired
    private MongoClient mongoClient;

    @Bean
    MappingMongoConverter mappingMongoConverter() throws Exception {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(multiTenantMongoDbFactory());
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext());
        converter.setCustomConversions(customConversions());
        converter.setMapKeyDotReplacement("_");
        return converter;
    }

    @Bean
    MongoMappingContext mongoMappingContext() throws ClassNotFoundException {
        MongoMappingContext mappingContext = new MongoMappingContext();
        mappingContext.setInitialEntitySet(getInitialEntitySet());
        mappingContext.setSimpleTypeHolder(customConversions().getSimpleTypeHolder());
        mappingContext.setFieldNamingStrategy(fieldNamingStrategy());
        return mappingContext;
    }

    private FieldNamingStrategy fieldNamingStrategy() {
        return PropertyNameFieldNamingStrategy.INSTANCE;
    }

    private Set<Class<?>> getInitialEntitySet() throws ClassNotFoundException {

        String basePackage = getMappingBasePackage();
        Set<Class<?>> initialEntitySet = new HashSet<Class<?>>();

        if (StringUtils.hasText(basePackage)) {
            ClassPathScanningCandidateComponentProvider componentProvider = new ClassPathScanningCandidateComponentProvider(
                    false);
            componentProvider.addIncludeFilter(new AnnotationTypeFilter(Document.class));
            componentProvider.addIncludeFilter(new AnnotationTypeFilter(Persistent.class));

            for (BeanDefinition candidate : componentProvider.findCandidateComponents(basePackage)) {
                initialEntitySet.add(ClassUtils.forName(candidate.getBeanClassName(),
                        AbstractMongoConfiguration.class.getClassLoader()));
            }
        }
        return initialEntitySet;
    }

    private String getMappingBasePackage() {

        Package mappingBasePackage = getClass().getPackage();
        return mappingBasePackage == null ? null : mappingBasePackage.getName();
    }

    @Bean
    CustomConversions customConversions() {
        return new CustomConversions(Arrays.asList(
                new OurDateTimeReadConverter(),
                new OurDateTimeWriteConverter()
        ));
    }

    @Bean
    MultiTenantMongoDbFactory multiTenantMongoDbFactory() {
        try {
            return new MultiTenantMongoDbFactory(mongoClient, defaultDatabaseName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(multiTenantMongoDbFactory(), mappingMongoConverter());
    }

    @Bean
    @Autowired
    public CompanyRepository companyRepository(CompanyDORepository companyDORepository) {
        return new CompanyRepositoryImpl(companyDORepository);
    }

    @Bean
    @Autowired
    public AccountingConfigurationRepository accountingConfigurationRepository(AccountingConfigurationDORepository accountingConfigurationDORepository) {
        return new AccountingConfigurationRepositoryImpl(accountingConfigurationDORepository);
    }

    @Bean
    @Autowired
    public MasterConfigUtils masterConfigUtils(MasterConfigurationDORepository masterConfigurationDORepository, QBDConnectionSessionDORepository qbdConnectionSessionDORepository) {
        return new DefaultMasterConfigUtils(masterConfigurationDORepository, defaultDatabaseName, qbdConnectionSessionDORepository);
    }

    @Bean
    public AssociationDocumentCreator associationDocumentCreator() {
        return new AssociationDocumentCreator(new AssociationFieldExpert());
    }

    @Bean
    @Autowired
    public AuthorizeChargeAssociationRepository authorizeChargeAssociationRepository(AuthorizeChargeAssociationDORepository repository,
                                                                                     MongoTemplate mongoTemplate, MasterConfigUtils masterConfigUtils,
                                                                                     AssociationDocumentCreator associationDocumentCreator) {
        return new AuthorizeChargeAssociationRepositoryImpl(repository, mongoTemplate, masterConfigUtils, associationDocumentCreator);
    }

    @Bean
    @Autowired
    public EmployeeRepository employeeRepository(EmployeeDORepository employeeDORepository, MongoTemplate mongoTemplate, MasterConfigUtils masterConfigUtils) {
        return new EmployeeRepositoryImpl(employeeDORepository, mongoTemplate, masterConfigUtils);
    }

    @Bean
    @Autowired
    public JobRepository jobRepository(JobDORepository jobDORepository, MasterConfigUtils masterConfigUtils, MongoTemplate mongoTemplate) {
        return new JobRepositoryImpl(jobDORepository, mongoTemplate, masterConfigUtils);
    }

    @Bean
    @Autowired
    public TimesheetRepository timesheetRepository(TimesheetDORepository timesheetDORepository, MongoTemplate mongoTemplate, MasterConfigUtils masterConfigUtils) {
        return new TimesheetRepositoryImpl(timesheetDORepository, mongoTemplate, masterConfigUtils);
    }

    @Bean
    @Autowired
    public HoursWorkedRepository hoursWorkedRepository(HoursWorkedDoRepository hoursWorkedDoRepository, MasterConfigUtils masterConfigUtils, AuditEventDORepository auditEventDORepository) {
        return new HoursWorkedRepositoryImpl(hoursWorkedDoRepository, masterConfigUtils, auditEventDORepository);
    }

    @Bean
    @Autowired
    public DesktopCustomerRepository desktopCustomerRepository(DesktopCustomerDoRepository desktopCustomerDoRepository) {
        return new DesktopCustomerRepositoryImpl(desktopCustomerDoRepository);
    }

    @Bean
    @Autowired
    public DesktopCustomerJobRepository desktopCustomerJobRepository(DesktopCustomerJobDORepository desktopCustomerJobDORepository) {
        return new DesktopCustomerJobRepositoryImpl(desktopCustomerJobDORepository);
    }

    @Bean
    @Autowired
    public DesktopClassRepository desktopClassRepository(DesktopClassDoRepository desktopClassDoRepository) {
        return new DesktopClassRepositoryImpl(desktopClassDoRepository);
    }

    @Bean
    @Autowired
    public DesktopServiceItemRepository desktopServiceItemRepository(DesktopServiceItemDORepository desktopServiceItemDORepository) {
        return new DesktopServiceItemRepositoryImpl(desktopServiceItemDORepository);
    }

    @Bean
    @Autowired
    public SuperAdminRepository superAdminRepository(SuperAdminDORepository superAdminDORepository, MongoTemplate mongoTemplate) {
        return new SuperAdminRepositoryImpl(superAdminDORepository, mongoTemplate);
    }

    @Bean
    @Autowired
    public PayTypeRepository payTypeRepository(PayTypeDORepository payTypeDORepository) {
        return new PayTypeRepositoryImpl(payTypeDORepository);
    }

    @Bean
    @Autowired
    public QBDRequestRepository qbdRequestRepository(DesktopQBDRequestDORepository desktopQbdRequestDORepository, MongoTemplate mongoTemplate, MasterConfigUtils masterConfigUtils) {
        return new QBDRequestRepositoryImpl(desktopQbdRequestDORepository, mongoTemplate, masterConfigUtils);
    }
}
