package com.hourtimesheet.callBack;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.export.ExportConfigurationType;
import com.ourtimesheet.qbd.query.hourExport.HoursSyncRequest;
import com.ourtimesheet.repository.CompanyRepository;
import com.ourtimesheet.repository.TimesheetRepository;
import com.ourtimesheet.timesheet.Timesheet;
import com.ourtimesheet.timesheet.TimesheetStatus;
import org.joda.time.DateTime;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by Click Chain on 3/6/2017.
 */
public class TimesheetExportSuccessJobCallBack implements ChildJobsCallBack<UUID> {

    private final TimesheetRepository timesheetRepository;
    private final CompanyRepository companyRepository;

    public TimesheetExportSuccessJobCallBack(TimesheetRepository timesheetRepository, CompanyRepository companyRepository) {
        this.timesheetRepository = timesheetRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public Timesheet updateTimesheetStatus(UUID id, List<HoursSyncRequest> hoursSyncRequests, boolean processTimesheet) {
        Timesheet timesheet = timesheetRepository.findById(id).get();
        timesheet = processTimesheet(timesheet, processTimesheet);
        if (timesheet != null)
            timesheetRepository.saveWithoutAudit(Collections.singletonList(timesheet));
        return timesheet;
    }

    private Timesheet processTimesheet(Timesheet timesheet, boolean processTimesheet) {
        OurDateTime now = new OurDateTime(DateTime.now(), companyRepository.find().getTimeZone());
        return timesheet.getStatus().equals(TimesheetStatus.PROCESSING_TIMESHEET_WITHOUT_SIGNATURE)
                || timesheet.getStatus().equals(TimesheetStatus.PROCESSING_TIMESHEET) ? timesheet.processSuccess(now, ExportConfigurationType.QBD, processTimesheet) : timesheet;
    }
}
