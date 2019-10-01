package com.ourtimesheet.qbd.factory;

import com.ourtimesheet.qbd.helper.QBDRequestStatus;
import com.ourtimesheet.qbd.helper.TimesheetCarrier;
import com.ourtimesheet.qbd.query.hourExport.HoursSyncRequest;
import com.ourtimesheet.qbd.query.hourExport.LeaveHourSyncRequest;
import com.ourtimesheet.qbd.query.hourExport.OverTimeHoursSyncRequest;
import com.ourtimesheet.qbd.query.hourExport.RegularHoursSyncRequest;
import com.ourtimesheet.timesheet.hoursWorked.HoursWorked;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Abdus Salam on 4/5/2017.
 */
public class HoursSyncRequestFactory {

    public List<HoursSyncRequest> create(TimesheetCarrier timesheetCarrier) {
        List<HoursSyncRequest> exportQueryRetquests = new ArrayList<>();
        timesheetCarrier.getHoursWorked().forEach(hoursWorked -> {
            if (hoursWorked.isLeave()) {
                exportQueryRetquests.add(createLeaveHourQuery(hoursWorked, timesheetCarrier));
            } else {
                if (hoursWorked.hasOvertime()) {
                    exportQueryRetquests.add(createOvertimeHourQuery(hoursWorked, timesheetCarrier));
                }
                if (hoursWorked.isRegular()) {
                    exportQueryRetquests.add(createRegularHourQuery(hoursWorked, timesheetCarrier));
                }
            }
        });
        return exportQueryRetquests;
    }

    private RegularHoursSyncRequest createRegularHourQuery(HoursWorked hoursWorked, TimesheetCarrier timesheetCarrier) {
        return new RegularHoursSyncRequest(UUID.randomUUID(), QBDRequestStatus.PROCESSING, hoursWorked, timesheetCarrier.getNotes(), null);
    }

    private LeaveHourSyncRequest createLeaveHourQuery(HoursWorked hoursWorked, TimesheetCarrier timesheetCarrier) {
        return new LeaveHourSyncRequest(UUID.randomUUID(), QBDRequestStatus.PROCESSING, hoursWorked, timesheetCarrier.getNotes(), null);
    }

    private OverTimeHoursSyncRequest createOvertimeHourQuery(HoursWorked hoursWorked, TimesheetCarrier timesheetCarrier) {
        return new OverTimeHoursSyncRequest(UUID.randomUUID(), QBDRequestStatus.PROCESSING, hoursWorked, timesheetCarrier.getNotes(), null);

    }
}