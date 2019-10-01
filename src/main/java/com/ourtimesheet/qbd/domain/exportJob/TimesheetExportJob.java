package com.ourtimesheet.qbd.domain.exportJob;

import com.ourtimesheet.job.ChildJob;
import com.ourtimesheet.qbd.query.hourExport.HoursSyncRequest;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;
import java.util.UUID;

/**
 * Created by Abdus Salam on 3/2/2017.
 */
public class TimesheetExportJob extends ChildJob<HoursSyncRequest> {

    private final UUID timesheetID;

    @DBRef
    private final List<HoursSyncRequest> hoursSyncRequests;

    public TimesheetExportJob(UUID timesheetID, List<HoursSyncRequest> hoursSyncRequests) {
        this.timesheetID = timesheetID;
        this.hoursSyncRequests = hoursSyncRequests;
    }

    @Override
    public boolean isAlive() {
        return hoursSyncRequests
                .stream()
                .anyMatch(request -> request != null && request.isProcessing());
    }

    @Override
    public List<HoursSyncRequest> getJobData() {
        return hoursSyncRequests;
    }

    @Override
    public boolean hasAnyRequestFailed() {
        return hoursSyncRequests
                .stream()
                .anyMatch(request -> request != null && request.isFailed());
    }

    @Override
    public boolean hasAnyRequestInProcessing() {
        return hoursSyncRequests
                .stream()
                .anyMatch(request -> request != null && request.isProcessing());
    }

    public UUID getTimesheetId() {
        return timesheetID;
    }

    public boolean hasNoHoursRequest() {
        return hoursSyncRequests.size() == 0;
    }
}
