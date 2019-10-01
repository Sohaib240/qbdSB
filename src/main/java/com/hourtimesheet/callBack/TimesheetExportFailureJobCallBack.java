package com.hourtimesheet.callBack;

import com.ourtimesheet.qbd.helper.QBDRequestStatus;
import com.ourtimesheet.qbd.query.QBDRequest;
import com.ourtimesheet.qbd.query.hourExport.HoursSyncRequest;
import com.ourtimesheet.qbd.repository.QBDRequestRepository;
import com.ourtimesheet.service.TimesheetService;
import com.ourtimesheet.timesheet.Timesheet;
import com.ourtimesheet.timesheet.TimesheetStatus;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by Click Chain on 3/6/2017.
 */
public class TimesheetExportFailureJobCallBack implements ChildJobsCallBack<UUID> {

    private static final String jobExpired = "5 hour passed and job did not run";
    private final QBDRequestRepository qbdRequestRepository;
    private final TimesheetService timesheetService;

    public TimesheetExportFailureJobCallBack(QBDRequestRepository qbdRequestRepository, TimesheetService timesheetService) {
        this.qbdRequestRepository = qbdRequestRepository;
        this.timesheetService = timesheetService;
    }

    @Override
    public Timesheet updateTimesheetStatus(UUID id, List<HoursSyncRequest> hoursSyncRequests, boolean processTimesheet) {
        hoursSyncRequests.stream()
                .filter(hoursWorkedSyncRequest -> hoursWorkedSyncRequest.getQbdRequestStatus().equals(QBDRequestStatus.PROCESSING))
                .forEach(hoursWorkedSyncRequest -> {
                    QBDRequest qbdRequest = hoursWorkedSyncRequest.onError(jobExpired);
                    qbdRequestRepository.save(qbdRequest);
                });
        return revertTimesheet(id);
    }

    public Timesheet revertTimesheet(UUID id) {
        Timesheet timesheet = timesheetService.findById(id);
        if (isTimesheetInProcess(timesheet)) {
            timesheet = timesheet.processFailed();
            timesheetService.processTimesheetWithoutAudit(Collections.singletonList(timesheet));
        }
        return timesheet;
    }

    private boolean isTimesheetInProcess(Timesheet timesheet) {
        return timesheet.getStatus().equals(TimesheetStatus.PROCESSING_TIMESHEET_WITHOUT_SIGNATURE)
                || timesheet.getStatus().equals(TimesheetStatus.PROCESSING_TIMESHEET);
    }

    public Timesheet getTimesheet(UUID timesheetId) {
        return timesheetService.findById(timesheetId);
    }
}
