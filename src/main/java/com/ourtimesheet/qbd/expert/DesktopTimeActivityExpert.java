package com.ourtimesheet.qbd.expert;

import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.job.JobStatus;
import com.ourtimesheet.qbd.domain.exportJob.HoursExportJob;
import com.ourtimesheet.qbd.domain.exportJob.TimesheetExportJob;
import com.ourtimesheet.qbd.factory.HoursSyncRequestFactory;
import com.ourtimesheet.qbd.helper.TimesheetCarrier;
import com.ourtimesheet.qbd.query.hourExport.HoursSyncRequest;
import com.ourtimesheet.qbd.repository.QBDRequestRepository;
import com.ourtimesheet.repository.JobRepository;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdus Salam on 3/1/2017.
 */
public class DesktopTimeActivityExpert {
    private final QBDRequestRepository qbdRequestRepository;
    private final JobRepository jobRepository;

    public DesktopTimeActivityExpert(QBDRequestRepository qbdRequestRepository, JobRepository jobRepository) {
        this.qbdRequestRepository = qbdRequestRepository;
        this.jobRepository = jobRepository;
    }

    public void saveExportRequests(List<TimesheetCarrier> timesheetCarrierList, Employee currentEmployee, boolean processTimesheet) {
        List<TimesheetExportJob> timesheetExportJobs = new ArrayList<>();
        for (TimesheetCarrier timesheetCarrier : timesheetCarrierList) {
            if (jobRepository.isAlreadyProcessed(timesheetCarrier.getId()))
                timesheetCarrier.getHoursWorked().removeAll(qbdRequestRepository.findFailedHoursWorked(timesheetCarrier.getHoursWorkedIds()));
            List<HoursSyncRequest> exportQueryRequests = new HoursSyncRequestFactory().create(timesheetCarrier);
            qbdRequestRepository.saveAll(exportQueryRequests);
            timesheetExportJobs.add(new TimesheetExportJob(timesheetCarrier.getId(), exportQueryRequests));
        }
        jobRepository.save(new HoursExportJob(DateTime.now(), JobStatus.PROCESSING, timesheetExportJobs, currentEmployee, processTimesheet));
    }
}
