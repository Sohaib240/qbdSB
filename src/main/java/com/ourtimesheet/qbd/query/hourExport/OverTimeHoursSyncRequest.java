package com.ourtimesheet.qbd.query.hourExport;

/**
 * Created by UMAR BHATTI on 3/27/2017.
 */

import com.ourtimesheet.paytype.EmployeePayType;
import com.ourtimesheet.qbd.factory.ExportQueryFactory;
import com.ourtimesheet.qbd.helper.QBDRequestStatus;
import com.ourtimesheet.qbd.query.QBDRequest;
import com.ourtimesheet.timesheet.hoursWorked.HoursWorked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;


public class OverTimeHoursSyncRequest extends HoursSyncRequest {

    private static final Logger log = LoggerFactory.getLogger(OverTimeHoursSyncRequest.class);

    public OverTimeHoursSyncRequest(UUID id, QBDRequestStatus qbdRequestStatus, HoursWorked hoursWorked, String notes, String errorMessage) {
        super(id, qbdRequestStatus, hoursWorked, notes, errorMessage);
    }

    @Override
    public String generateQuery() {
        try {
            return new ExportQueryFactory().create(getRequestXmlFileInputStream(), hoursWorked, notes, this);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public QBDRequest onSuccess() {
        return new OverTimeHoursSyncRequest.Builder(getId(), QBDRequestStatus.COMPLETE)
                .withHoursWorked(hoursWorked)
                .withNotes(notes)
                .build();
    }

    @Override
    public QBDRequest onError(String errorMessage) {
        return new OverTimeHoursSyncRequest.Builder(getId(), QBDRequestStatus.FAILURE)
                .withHoursWorked(hoursWorked)
                .withNotes(notes)
                .withErrorMessage(errorMessage)
                .build();
    }

    @Override
    public List<EmployeePayType> getEffectivePayTypes() {
        return hoursWorked.getEmployee().getOvertimePayTypes();
    }

    @Override
    public String getDescription() {
        return "Overtime Hours " + Double.toString(hoursWorked.getHours()) + " for the time period " + hoursWorked.hourWorkedDateOnlyFormatted() + " of " + hoursWorked.getEmployee().getDisplayName();
    }

    public static class Builder {
        private UUID id;
        private QBDRequestStatus qbdRequestStatus;
        private HoursWorked hoursWorked;
        private String notes;
        private String errorMessage;

        public Builder(UUID id, QBDRequestStatus qbdRequestStatus) {
            this.id = id;
            this.qbdRequestStatus = qbdRequestStatus;
        }

        public Builder withErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public Builder withHoursWorked(HoursWorked hoursWorked) {
            this.hoursWorked = hoursWorked;
            return this;
        }

        public Builder withNotes(String notes) {
            this.notes = notes;
            return this;
        }

        public OverTimeHoursSyncRequest build() {
            return new OverTimeHoursSyncRequest(id, qbdRequestStatus, hoursWorked, notes, errorMessage);
        }
    }
}