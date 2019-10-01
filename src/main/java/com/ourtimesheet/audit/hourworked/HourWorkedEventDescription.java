package com.ourtimesheet.audit.hourworked;

import com.ourtimesheet.timesheet.hoursWorked.HoursWorked;
import org.apache.commons.lang3.text.StrSubstitutor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abdus Salam on 8/22/2016.
 */
public class HourWorkedEventDescription {

  static String getEventDescription(HoursWorked hoursWorked, String message) {
    Map<String, String> valuesMap = new HashMap<>();
    valuesMap.put("dateValue", hoursWorked.hourWorkedDateOnlyFormatted());
    valuesMap.put("timeValue", hoursWorked.hourWorkedTimeOnly());
    valuesMap.put("chargeCodes", hoursWorked.authorizedCharges());
    StrSubstitutor sub = new StrSubstitutor(valuesMap);
    return sub.replace(message);
  }


  static String getNotesEventDescription(HoursWorked hoursWorked, String message) {
    Map<String, String> valuesMap = new HashMap<>();
    valuesMap.put("day", hoursWorked.hourWorkedWeekDay());
    valuesMap.put("dateValue", hoursWorked.hourWorkedDateOnlyFormatted());
    valuesMap.put("chargeCodes", hoursWorked.authorizedCharges());
    StrSubstitutor sub = new StrSubstitutor(valuesMap);
    return sub.replace(message);
  }
}
