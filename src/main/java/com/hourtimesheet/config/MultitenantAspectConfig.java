package com.hourtimesheet.config;

import com.ourtimesheet.exception.CompanyNotFoundException;
import com.ourtimesheet.multitenant.CompanyHolder;
import com.ourtimesheet.multitenant.MultiTenantMongoDbFactory;
import com.ourtimesheet.util.MasterConfigUtils;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by hassan on 3/17/16.
 */
@Aspect
@EnableAspectJAutoProxy
@Configuration
public class MultitenantAspectConfig {

    @Autowired
    private MasterConfigUtils masterConfigUtils;

    @Pointcut("(execution(* com.ourtimesheet.repositorydo..*(..)) || execution(* com.ourtimesheet.accounting.repositorydo..*(..))" +
            " || execution(* com.ourtimesheet.qbd.repositorydo..*(..)))" +
            " && !execution(* com.ourtimesheet.repositorydo.MasterConfigurationDORepository.*(..))" +
            " && !execution(* com.ourtimesheet.repositorydo.QBDConnectionSessionDORepository.*(..))" +
            " && !execution(* com.ourtimesheet.repositorydo.SuperAdminDORepository.*(..))")
    private void selectAllRepositoryDOMethods() {
    }

    @Before("selectAllRepositoryDOMethods()")
    public void beforeAdvice() {
        try {
            String dbName = masterConfigUtils.getDBForDomain(CompanyHolder.getCompanyName());
            MultiTenantMongoDbFactory.setDatabaseNameForCurrentThread(dbName);
        } catch (CompanyNotFoundException companyNotFoundException) {
            throw new RuntimeException(companyNotFoundException);
        }
    }

    @After("selectAllRepositoryDOMethods()")
    public void afterAdvice() {
        MultiTenantMongoDbFactory.clearDatabaseNameForCurrentThread();
    }
}
