package com.ourtimesheet.timesheet.increment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hassan on 5/5/16.
 */
public class FifteenMinuteIncrement implements HoursIncrement {
  @Override
  public Map<String, String> calculateTotalHoursFormatted(Map<String, Duration> totalMap) {
    Map<String, String> totalHoursFormattedMap = new HashMap<>();
    String sixMinutesIncrementHours = null;
    for (String date : totalMap.keySet()) {
      Long value = totalMap.get(date).toMinutes();
      double result = (value - ((double) value % 15)) / 15 * 0.25;
      sixMinutesIncrementHours = String.valueOf(round(result, 2));
      totalHoursFormattedMap.put(date, sixMinutesIncrementHours);
    }
    return totalHoursFormattedMap;
  }

  private double round(double value, int precision) {
    BigDecimal bd = BigDecimal.valueOf(value);
    bd = bd.setScale(precision, RoundingMode.FLOOR);
    return bd.doubleValue();
  }
}
