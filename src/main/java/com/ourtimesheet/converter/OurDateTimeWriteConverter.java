package com.ourtimesheet.converter;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.ourtimesheet.datetime.OurDateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

/**
 * Created by hassan on 7/24/16.
 */
@WritingConverter
public class OurDateTimeWriteConverter implements Converter<OurDateTime, DBObject> {
  @Override
  public DBObject convert(OurDateTime source) {
    DBObject dbo = new BasicDBObject();
    dbo.put("dateTime", source.getDateTime().toString());
    dbo.put("effectiveTimezone", source.getEffectiveTimezone().getID());
    return dbo;
  }
}
