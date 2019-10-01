package com.hourtimesheet.callBack;

import com.ourtimesheet.qbd.query.hourExport.HoursSyncRequest;
import com.ourtimesheet.timesheet.Timesheet;

import java.util.List;
import java.util.UUID;

/**
 * Created by Click Chain on 3/6/2017.
 */
public interface ChildJobsCallBack<T> {
    Timesheet updateTimesheetStatus(UUID id, List<HoursSyncRequest> hoursSyncRequests, boolean processTimesheet);
}
