package com.hourtimesheet.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.net.ssl.SSLSocketFactory;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hassan on 3/5/16.
 */

@Configuration
@EnableMongoRepositories(basePackages = {"com.ourtimesheet.repositorydo", "com.ourtimesheet.accounting.repositorydo", "com.ourtimesheet.qbd.repositorydo"})
@PropertySource("classpath:mongo-${envTarget:local}.properties")
public class MongoConfiguration {

    @Value("${dbhost}")
    private String dbHost;

    @Value("${dbport}")
    private int dbPort;

    @Value("${dbdefault}")
    private String defaultDatabaseName;

    @Value("${dbuser}")
    private String dbUser;

    @Value("${dbpassword}")
    private String dbPassword;

    @Value("${dbauthenticationdb}")
    private String authenticationDb;

    @Value("${dbusessl}")
    private boolean useSSL;

    @Bean
    MongoClient mongoClient() throws UnknownHostException {
        if (useSSL) {
            MongoClientOptions mongoClientOptions = MongoClientOptions.builder().socketFactory(SSLSocketFactory.getDefault()).build();
            return new MongoClient(serverAddressList(), Collections.singletonList(mongoCredential()), mongoClientOptions);
        }
        return new MongoClient(serverAddressList(), Collections.singletonList(mongoCredential()));
    }

    @Bean
    MongoCredential mongoCredential() {
        return MongoCredential.createCredential(dbUser, authenticationDb, dbPassword.toCharArray());
    }

    private List<ServerAddress> serverAddressList() throws UnknownHostException {
        List<ServerAddress> serverAddresses = new ArrayList<>(5);
        for (String host : dbHost.split(",")) {
            serverAddresses.add(new ServerAddress(host, dbPort));
        }
        return serverAddresses;
    }

    @Bean
    String defaultDatabaseName() {
        return defaultDatabaseName;
    }
}