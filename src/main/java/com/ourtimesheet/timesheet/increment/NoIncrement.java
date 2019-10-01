package com.ourtimesheet.timesheet.increment;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hassan on 5/5/16.
 */
public class NoIncrement implements HoursIncrement {
  @Override
  public Map<String, String> calculateTotalHoursFormatted(Map<String, Duration> totalMap) {
    Map<String, String> totalHoursFormattedMap = new HashMap<>();
    for (String date : totalMap.keySet()) {
      Long value = totalMap.get(date).toMinutes();
      totalHoursFormattedMap.put(date, splitToComponentTimes(value));
    }
    return totalHoursFormattedMap;
  }

  public String splitToComponentTimes(Long minutes) {
    long longVal = minutes;
    int hours = (int) longVal / 60;
    int remainder = (int) longVal - hours * 60;
    int mins = remainder;
    return hours + ":" + mins;
  }
}
