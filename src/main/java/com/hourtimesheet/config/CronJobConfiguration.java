package com.hourtimesheet.config;

import com.hourtimesheet.handler.RequestHandler;
import com.ourtimesheet.multitenant.CompanyHolder;
import com.ourtimesheet.util.MasterConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by Jazib on 06/03/2017.
 */

@Configuration
@EnableScheduling
@Import({JobScheduleConfiguration.class})
public class CronJobConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(CronJobConfiguration.class);

    private final MasterConfigUtils masterConfigUtils;

    private final RequestHandler requestHandler;

    @Autowired
    public CronJobConfiguration(MasterConfigUtils masterConfigUtils, RequestHandler requestHandler) {
        this.masterConfigUtils = masterConfigUtils;
        this.requestHandler = requestHandler;
    }

    @Scheduled(cron = "* */1 * * * ?")
    public void updateTokenTask() {
        masterConfigUtils.getAllCompanies().forEach(dbName -> {
            CompanyHolder.set(dbName);
            try {
                requestHandler.executeAllProcessingJobs();
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                LOGGER.warn("error occured in company : " + dbName);
            } finally {
                CompanyHolder.clear();
            }
        });
    }

}
