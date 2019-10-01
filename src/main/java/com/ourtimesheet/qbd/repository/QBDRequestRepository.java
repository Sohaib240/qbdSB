package com.ourtimesheet.qbd.repository;


import com.ourtimesheet.qbd.query.QBDRequest;
import com.ourtimesheet.repository.Repository;
import com.ourtimesheet.timesheet.hoursWorked.HoursWorked;

import java.util.List;
import java.util.UUID;

/**
 * Created by Abdus Salam on 5/31/2016.
 */
public interface QBDRequestRepository extends Repository<QBDRequest> {

    QBDRequest findOneUnfinishedQuery();

    List<QBDRequest> findPreferenceQueries();

    QBDRequest findPreferenceQuery();

    List<HoursWorked> findFailedHoursWorked(List<UUID> hoursWorkedIds);
}
