package com.hourtimesheet.config;

import com.hourtimesheet.delegate.QBDServiceDelegate;
import com.hourtimesheet.services.QuickBooksDesktopServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.http.HttpDestinationFactory;
import org.apache.cxf.transport.servlet.ServletDestinationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.xml.ws.Endpoint;

/**
 * Created by Abdus Salam on 2/12/2016.
 */
@Configuration
@Import(MainConfiguration.class)
public class WebServiceConfiguration {

    @Autowired
    private QBDServiceDelegate QBDServiceDelegate;

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        SpringBus bus = new SpringBus();
        ServletDestinationFactory destinationFactory = new ServletDestinationFactory();
        bus.setExtension(destinationFactory, HttpDestinationFactory.class);
        return bus;
    }

    @Bean
    public Endpoint endpoint() {
        QuickBooksDesktopServiceImpl quickBooksDesktopService = new QuickBooksDesktopServiceImpl();
        quickBooksDesktopService.setQBDServiceDelegate(QBDServiceDelegate);
        EndpointImpl endpoint = new EndpointImpl(springBus(), quickBooksDesktopService);
        endpoint.publish("/qbd");
        return endpoint;
    }
}
