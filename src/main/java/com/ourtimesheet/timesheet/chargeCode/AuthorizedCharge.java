package com.ourtimesheet.timesheet.chargeCode;

import java.util.List;

/**
 * Created by hassan on 6/7/16.
 */
public interface AuthorizedCharge {

  List<ChargeCode> getAuthorizedCharge();

  boolean isLeave();

}