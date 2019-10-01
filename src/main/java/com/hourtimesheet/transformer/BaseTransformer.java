package com.hourtimesheet.transformer;


import com.hourtimesheet.desktop.QBXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import java.io.StringReader;

/**
 * Created by Zohaib on 2/14/2017.
 */
public class BaseTransformer {
    private static final Logger LOG = LoggerFactory.getLogger(BaseTransformer.class);

    protected QBXML transformToQBXML(String qbxml) throws Exception {
        if (qbxml.isEmpty()) {
            LOG.warn("Customer String is empty");
        } else {
            LOG.warn(qbxml.substring(0, 100));
        }
        JAXBContext jaxbContext;
        jaxbContext = JAXBContext.newInstance(QBXML.class);
        return (QBXML) jaxbContext.createUnmarshaller().unmarshal(new StringReader(qbxml));
    }

}
