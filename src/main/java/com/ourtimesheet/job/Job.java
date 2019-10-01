package com.ourtimesheet.job;

import com.ourtimesheet.common.Entity;
import com.ourtimesheet.employee.Employee;
import org.joda.time.DateTime;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

/**
 * Created by Abdus Salam on 3/2/2017.
 */
@Document(collection = "Job")
public abstract class Job<T> extends Entity {

    protected final DateTime timestamp;
    private final JobStatus jobStatus;
    private final Employee employee;
    private final boolean processTimesheet;

    @PersistenceConstructor
    protected Job(UUID uuid, DateTime timestamp, JobStatus jobStatus, Employee employee, boolean processTimesheet) {
        super(uuid);
        this.timestamp = timestamp;
        this.jobStatus = jobStatus;
        this.employee = employee;
        this.processTimesheet = processTimesheet;
    }

    public Job(DateTime timestamp, JobStatus jobStatus, Employee employee, boolean processTimesheet) {
        super();
        this.timestamp = timestamp;
        this.jobStatus = jobStatus;
        this.employee = employee;
        this.processTimesheet = processTimesheet;
    }

    public Employee getEmployee() {
        return employee;
    }

    public boolean isProcessTimesheet() {
        return processTimesheet;
    }

    public abstract JobStatus run();

    protected abstract boolean isAlive();

    public abstract List<T> getJobData();

    public abstract Job onError();

    public abstract Job onSuccess();
}
