package com.ourtimesheet.job;

import java.util.List;

/**
 * Created by Abdus Salam on 3/2/2017.
 */
public abstract class ChildJob<T> {

    protected abstract boolean isAlive();

    public abstract List<T> getJobData();

    public abstract boolean hasAnyRequestFailed();

    public abstract boolean hasAnyRequestInProcessing();
}
