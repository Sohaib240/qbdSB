package com.ourtimesheet.qbd.query.hourExport;

import com.ourtimesheet.paytype.EmployeePayType;
import com.ourtimesheet.qbd.helper.QBDRequestStatus;
import com.ourtimesheet.qbd.query.QBDRequest;
import com.ourtimesheet.timesheet.hoursWorked.HoursWorked;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * Created by Abdus Salam on 4/6/2017.
 */
public abstract class HoursSyncRequest extends QBDRequest {

    protected final HoursWorked hoursWorked;
    protected final String notes;

    public HoursSyncRequest(UUID id, QBDRequestStatus qbdRequestStatus, HoursWorked hoursWorked, String notes, String errorMessage) {
        super(id, qbdRequestStatus, errorMessage);
        this.hoursWorked = hoursWorked;
        this.notes = notes;
    }

    public abstract String generateQuery();

    @Override
    public InputStream getRequestXmlFileInputStream() throws IOException {
        return getClass().getResource("/query/exportQuery.xml").openStream();
    }

    @Override
    public boolean isImportRequest() {
        return false;
    }

    public HoursWorked getHoursWorked() {
        return hoursWorked;
    }

    public abstract List<EmployeePayType> getEffectivePayTypes();

    public abstract QBDRequest onSuccess();

    public abstract QBDRequest onError(String errorMessage);
}