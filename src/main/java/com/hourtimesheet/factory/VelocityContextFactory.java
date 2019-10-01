package com.hourtimesheet.factory;

import com.hourtimesheet.config.ConnectorConfiguration;
import org.apache.velocity.VelocityContext;

import java.text.MessageFormat;

/**
 * Created by Abdus Salam on 1/31/2017.
 */
public class VelocityContextFactory {

    private final ConnectorConfiguration connectorConfiguration;

    public VelocityContextFactory(ConnectorConfiguration connectorConfiguration) {
        this.connectorConfiguration = connectorConfiguration;
    }

    public VelocityContext createWebConnectorContext(String userName, String companyName) {
        VelocityContext ctx = new VelocityContext();
        ctx.internalPut("appID", connectorConfiguration.getAppID());
        ctx.internalPut("appName", MessageFormat.format(connectorConfiguration.getAppName(), companyName));
        ctx.internalPut("appURL", connectorConfiguration.getAppURL());
        ctx.internalPut("certURL", connectorConfiguration.getCertUrl());
        ctx.internalPut("appDescription", connectorConfiguration.getAppDescription());
        ctx.internalPut("schedulerRept", connectorConfiguration.getSchedulerRepition());
        ctx.internalPut("ownerID", connectorConfiguration.getOwnerID());
        ctx.internalPut("fileID", connectorConfiguration.getFileId());
        ctx.internalPut("appSupport", connectorConfiguration.getAppSupport());
        ctx.internalPut("qbType", connectorConfiguration.getQbType());
        ctx.internalPut("dataPrefrence", connectorConfiguration.getPersonalDataPreference());
        ctx.internalPut("readOnlyIndicator", connectorConfiguration.getReadOnly());
        ctx.internalPut("userName", MessageFormat.format(connectorConfiguration.getUserName(), userName + "@" + companyName));
        return ctx;
    }
}
