package com.ourtimesheet.multitenant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.springframework.util.Assert.notNull;


/**
 * Created by Talha Zahid on 3/1/2016.
 */
public class CompanyHolder {

  private static final Logger log = LoggerFactory.getLogger(CompanyHolder.class);

  private static final ThreadLocal<String> HOLDER = new ThreadLocal<>();

  public static String getCompanyName() {
    return HOLDER.get();
  }

  public static void set(String companyName) {
    notNull(companyName);
    logAndSet(companyName);
  }

  private static void logAndSet(String companyName) {
    log.debug("Setting companyName to {}", companyName);
    HOLDER.set(companyName);
  }

  public static void clear() {
    log.debug("Clearing companyName");
    HOLDER.remove();
  }
}
