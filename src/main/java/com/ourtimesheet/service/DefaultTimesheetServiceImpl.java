package com.ourtimesheet.service;

import com.ourtimesheet.repository.TimesheetRepository;
import com.ourtimesheet.timesheet.Timesheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class DefaultTimesheetServiceImpl implements TimesheetService {

    private static final Logger log = LoggerFactory.getLogger(DefaultTimesheetServiceImpl.class);

    private final TimesheetRepository timesheetRepository;

    public DefaultTimesheetServiceImpl(TimesheetRepository timesheetRepository) {
        this.timesheetRepository = timesheetRepository;

    }

    @Override
    public void processTimesheetWithoutAudit(List<Timesheet> timesheets) {
        timesheetRepository.saveWithoutAudit(timesheets);
    }

    @Override
    public Timesheet findById(UUID uuid) {
        return timesheetRepository.findById(uuid).get();
    }
}
