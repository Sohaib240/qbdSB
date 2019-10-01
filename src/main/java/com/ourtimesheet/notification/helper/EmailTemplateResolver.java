package com.ourtimesheet.notification.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;
import java.util.Map;

/**
 * Created by Abdus Salam on 9/16/2016.
 */
public class EmailTemplateResolver {

  private static final Logger log = LoggerFactory.getLogger(EmailTemplateResolver.class);
  private final TemplateEngine templateEngine;

  public EmailTemplateResolver(TemplateEngine templateEngine) {
    this.templateEngine = templateEngine;
  }

    public String resolve(String templateLocation, Map<String, Object> propertyValueMap) {
    log.debug("Resolving Template");
    return templateEngine.process(templateLocation, createContext(propertyValueMap));
  }

    private Context createContext(Map<String, Object> propertyValueMap) {
    Context context = new Context(Locale.ENGLISH);
    if (propertyValueMap != null) {
      propertyValueMap.keySet().forEach(property -> context.setVariable(property, propertyValueMap.get(property)));
    }
    return context;
  }
}
