package com.ourtimesheet.converter;

import com.mongodb.DBObject;
import com.ourtimesheet.datetime.OurDateTime;
import org.joda.time.DateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.TimeZone;

/**
 * Created by hassan on 7/24/16.
 */
@ReadingConverter
public class OurDateTimeReadConverter implements Converter<DBObject, OurDateTime> {
  @Override
  public OurDateTime convert(DBObject source) {
    String dateTimeString = (String) source.get("dateTime");
    DateTime dateTime = DateTime.parse(dateTimeString);
    String timeZoneId = (String) source.get("effectiveTimezone");
    TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);
    return new OurDateTime(dateTime, timeZone);
  }
}
