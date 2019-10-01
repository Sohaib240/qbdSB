package com.ourtimesheet.accounting.domain;

/**
 * Created by Adeel on 5/31/2016.
 */
public interface AccountingSystemAware {

  AccountingSystem resolve();
}
