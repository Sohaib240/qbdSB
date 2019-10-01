package com.ourtimesheet.timesheet.hoursWorked;

import com.ourtimesheet.datetime.OurDateTime;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by zohaib on 13/12/2016.
 */
public class Note {
    private String message;
    private OurDateTime timestamp;

    public Note(String message, OurDateTime timestamp) {
        if (StringUtils.isNoneBlank(message)) {
            this.message = message;
            this.timestamp = timestamp;
        }
    }

    public Note() {
        message = null;
        timestamp = null;
    }

    public String getMessage() {
        return message;
    }

    public OurDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isEmpty() {
        return StringUtils.isBlank(message) && timestamp == null;
    }
}
