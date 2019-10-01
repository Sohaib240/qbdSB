package com.ourtimesheet.timesheet.increment;

import java.time.Duration;
import java.util.Map;

/**
 * Created by hassan on 5/4/16.
 */
public interface HoursIncrement {

  Map<String, String> calculateTotalHoursFormatted(Map<String, Duration> totalMap);

}
