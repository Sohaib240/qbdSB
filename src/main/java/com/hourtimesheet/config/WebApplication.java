package com.hourtimesheet.config;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by Abdus Salam on 1/23/2017.
 */
public class WebApplication implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext container) throws ServletException {

    AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();

    context.register(MainConfiguration.class, WebServiceConfiguration.class);

    container.addListener(new ContextLoaderListener(context));

    ServletRegistration.Dynamic cxfDispatcher = container.addServlet("CXFServlet", new CXFServlet());

    cxfDispatcher.setLoadOnStartup(1);

    cxfDispatcher.addMapping("/api/*");

    restfulContext(container);
  }

  private void restfulContext(ServletContext container) {
    AnnotationConfigWebApplicationContext restfulWebContext = new AnnotationConfigWebApplicationContext();
    restfulWebContext.register(WebRestfulConfiguration.class);
    ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(restfulWebContext));
    dispatcher.setLoadOnStartup(1);
    dispatcher.addMapping("/rest/*");
  }

}