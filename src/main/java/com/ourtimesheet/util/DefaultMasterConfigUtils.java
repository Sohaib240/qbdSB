package com.ourtimesheet.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.ourtimesheet.data.MasterConfigurationDO;
import com.ourtimesheet.data.QBDConnectionSessionDO;
import com.ourtimesheet.exception.CompanyNotFoundException;
import com.ourtimesheet.multitenant.MultiTenantMongoDbFactory;
import com.ourtimesheet.repositorydo.MasterConfigurationDORepository;
import com.ourtimesheet.repositorydo.QBDConnectionSessionDORepository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Created by hassan on 3/25/16.
 */
public class DefaultMasterConfigUtils implements MasterConfigUtils {

    private final LoadingCache<String, String> domainDBCache;

    private final String masterDBName;

    private final MasterConfigurationDORepository masterConfigurationDORepository;

    private final QBDConnectionSessionDORepository qbdConnectionSessionDORepository;


    public DefaultMasterConfigUtils(final MasterConfigurationDORepository masterConfigurationDORepository, final String masterDBName, QBDConnectionSessionDORepository qbdConnectionSessionDORepository) {
        this.masterDBName = masterDBName;
        this.masterConfigurationDORepository = masterConfigurationDORepository;
        this.qbdConnectionSessionDORepository = qbdConnectionSessionDORepository;

        domainDBCache = CacheBuilder.newBuilder().maximumSize(1000).build(new CacheLoader<String, String>() {
            @Override
            public String load(String key) throws CompanyNotFoundException {
                MultiTenantMongoDbFactory.setDatabaseNameForCurrentThread(masterDBName);
                MasterConfigurationDO masterConfigurationDO = masterConfigurationDORepository.findById(key).orElse(null);
                MultiTenantMongoDbFactory.clearDatabaseNameForCurrentThread();
                if (masterConfigurationDO == null) {
                    throw new CompanyNotFoundException("Invalid Company Domain Name");
                }
                return masterConfigurationDO.getDatabaseName();
            }
        });
    }


    @Override
    public String getDBForDomain(String subdomain) throws CompanyNotFoundException {
        try {
            return domainDBCache.get(subdomain);
        } catch (ExecutionException executionException) {
            throw new CompanyNotFoundException("Invalid Company Domain Name");
        }
    }


    @Override
    public List<String> getAllCompanies() {
        return getAllAccounts().stream().map(MasterConfigurationDO::getSubDomainName).collect(Collectors.toList());
    }

    private List<MasterConfigurationDO> getAllAccounts() {
        MultiTenantMongoDbFactory.setDatabaseNameForCurrentThread(masterDBName);
        List<MasterConfigurationDO> configurationDOs = masterConfigurationDORepository.findAll();
        MultiTenantMongoDbFactory.clearDatabaseNameForCurrentThread();
        return configurationDOs;
    }


    @Override
    public void saveQBDConnectionSessionDOForCompany(QBDConnectionSessionDO qbdConnectionSessionDO) {
        MultiTenantMongoDbFactory.setDatabaseNameForCurrentThread(masterDBName);
        qbdConnectionSessionDORepository.save(qbdConnectionSessionDO);
    }

    @Override
    public void removeQBDConnectionSessionDOForCompany(String sessionId) {
        MultiTenantMongoDbFactory.setDatabaseNameForCurrentThread(masterDBName);
        QBDConnectionSessionDO qbdConnectionSessionDO = qbdConnectionSessionDORepository.findById(UUID.fromString(sessionId));
        qbdConnectionSessionDORepository.delete(qbdConnectionSessionDO);
    }

    @Override
    public QBDConnectionSessionDO getQBDConnectionSessionDOByCompanyName(String companyName) {
        MultiTenantMongoDbFactory.setDatabaseNameForCurrentThread(masterDBName);
        return qbdConnectionSessionDORepository.findByCompanyName(companyName);
    }

    @Override
    public QBDConnectionSessionDO getQBDConnectionSessionDOById(UUID sessionId) {
        MultiTenantMongoDbFactory.setDatabaseNameForCurrentThread(masterDBName);
        return qbdConnectionSessionDORepository.findById(sessionId);
    }

}
