package com.hourtimesheet.expert;

import com.hourtimesheet.factory.VelocityContextFactory;
import org.apache.velocity.Template;

import java.io.StringWriter;

/**
 * Created by hassan on 2/7/17.
 */
public class WebConnectorFileExpert {

    private final Template template;
    private final VelocityContextFactory velocityContextFactory;
    public WebConnectorFileExpert(Template template, VelocityContextFactory velocityContextFactory) {
        this.template = template;
        this.velocityContextFactory = velocityContextFactory;
    }

    public String getQBWCFileAsString(String userName, String companyDomain) {
            StringWriter sw = new StringWriter();
            template.process();
            template.merge(velocityContextFactory.createWebConnectorContext(userName, companyDomain), sw);
            return sw.toString();
    }
}
