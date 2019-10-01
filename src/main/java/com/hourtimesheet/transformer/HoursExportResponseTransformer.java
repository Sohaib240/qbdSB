package com.hourtimesheet.transformer;

import com.hourtimesheet.desktop.QBXML;
import com.hourtimesheet.desktop.TimeTrackingAddRsType;
import com.hourtimesheet.exception.QuickBooksExportException;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;

/**
 * Created by Zohaib on 9/16/2015.
 */
public class HoursExportResponseTransformer extends BaseTransformer implements Transformer<String, Object> {

    @Override
    public Object transform(String s) throws Exception {
        QBXML qbxmlType = transformToQBXML(s);
        TimeTrackingAddRsType timeTrackingAddRs = qbxmlType.getQBXMLMsgsRs().getTimeTrackingAddRs();
        BigInteger statusCode = timeTrackingAddRs.getStatusCode();
        if (statusCode.equals(BigInteger.ZERO)) {
            return null;
        } else {
            throw new QuickBooksExportException(extractExceptionMessage(timeTrackingAddRs.getStatusMessage()));
        }
    }

    private String extractExceptionMessage(String message) {
        return StringUtils.isNotBlank(message) ? message.replaceAll("\"", "") : "";
    }
}