package com.hourtimesheet.config;

import com.hourtimesheet.callBack.*;
import com.hourtimesheet.handler.HoursExportExpert;
import com.hourtimesheet.handler.RequestHandler;
import com.ourtimesheet.notification.builder.ExportProcessCompleteNotificationBuilder;
import com.ourtimesheet.notification.builder.ExportProcessFailureNotificationBuilder;
import com.ourtimesheet.notification.service.NotificationService;
import com.ourtimesheet.qbd.repository.QBDRequestRepository;
import com.ourtimesheet.repository.CompanyRepository;
import com.ourtimesheet.repository.JobRepository;
import com.ourtimesheet.repository.TimesheetRepository;
import com.ourtimesheet.service.EmployeeService;
import com.ourtimesheet.service.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by hassan on 2/5/17.
 */
@Configuration
@Import({ServiceConfiguration.class})
public class JobScheduleConfiguration {

    @Bean
    @Autowired
    public RequestHandler requestHandler(JobRepository jobRepository, HoursExportExpert hoursExportExpert) {
        return new RequestHandler(jobRepository, hoursExportExpert);
    }

    @Bean
    @Autowired
    public HoursExportExpert hoursExportExpert(HoursExportSuccessJobCallBack hoursExportSuccessJobCallBack, HoursExportFailureJobCallBack hoursExportFailureJobCallBack, HourExportProcessingJobCallBack hourExportProcessingJobCallBack, NotificationService notificationService) {
        return new HoursExportExpert(hoursExportSuccessJobCallBack, hoursExportFailureJobCallBack, hourExportProcessingJobCallBack, notificationService);
    }


    @Bean
    @Autowired
    public TimesheetExportSuccessJobCallBack timesheetExportSuccessJobCallBack(TimesheetRepository timesheetRepository, CompanyRepository companyRepository) {
        return new TimesheetExportSuccessJobCallBack(timesheetRepository, companyRepository);
    }

    @Bean
    @Autowired
    public TimesheetExportFailureJobCallBack timesheetExportFailureJobCallBack(QBDRequestRepository qbdRequestRepository, TimesheetService timesheetService) {
        return new TimesheetExportFailureJobCallBack(qbdRequestRepository, timesheetService);
    }

    @Bean
    @Autowired
    public HoursExportSuccessJobCallBack hoursExportSuccessJobCallBack(JobRepository jobRepository, TimesheetExportSuccessJobCallBack timesheetExportSuccessJobCallBack, TimesheetExportFailureJobCallBack timesheetExportFailureJobCallBack, ExportProcessCompleteNotificationBuilder exportProcessCompleteNotificationBuilder) {
        return new HoursExportSuccessJobCallBack(jobRepository, timesheetExportSuccessJobCallBack, timesheetExportFailureJobCallBack, exportProcessCompleteNotificationBuilder);
    }

    @Bean
    @Autowired
    public HoursExportFailureJobCallBack hoursExportFailureJobCallBack(JobRepository jobRepository, TimesheetExportFailureJobCallBack timesheetExportFailureJobCallBack, ExportProcessFailureNotificationBuilder exportProcessFailureNotificationBuilder, EmployeeService employeeService) {
        return new HoursExportFailureJobCallBack(jobRepository, timesheetExportFailureJobCallBack, exportProcessFailureNotificationBuilder, employeeService);
    }

    @Bean
    @Autowired
    public HourExportProcessingJobCallBack hourExportProcessingJobCallBack(JobRepository jobRepository, TimesheetExportFailureJobCallBack timesheetExportFailureJobCallBack, TimesheetExportSuccessJobCallBack timesheetExportSuccessJobCallBack) {
        return new HourExportProcessingJobCallBack(timesheetExportFailureJobCallBack, timesheetExportSuccessJobCallBack, jobRepository);
    }

}
