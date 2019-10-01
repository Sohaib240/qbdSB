package com.ourtimesheet.repository;

import com.ourtimesheet.common.Entity;
import com.ourtimesheet.exception.CompanyNotFoundException;
import com.ourtimesheet.multitenant.CompanyHolder;
import com.ourtimesheet.multitenant.MultiTenantMongoDbFactory;
import com.ourtimesheet.repositorydo.AuditEventDORepository;
import com.ourtimesheet.repositorydo.HoursWorkedDoRepository;
import com.ourtimesheet.timesheet.hoursWorked.AuthorizeChargeDetail;
import com.ourtimesheet.timesheet.hoursWorked.HoursWorked;
import com.ourtimesheet.timesheet.search.HoursWorkedCriteria;
import com.ourtimesheet.util.MasterConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Click Chain on 6/7/2016.
 */
public class HoursWorkedRepositoryImpl extends GenericRepository<HoursWorked> implements HoursWorkedRepository {

    private static final Logger log = LoggerFactory.getLogger(HoursWorkedRepositoryImpl.class);

    private final MasterConfigUtils masterConfigUtils;
    private HoursWorkedDoRepository hoursWorkedDoRepository;
    private AuditEventDORepository auditEventDORepository;

    public HoursWorkedRepositoryImpl(HoursWorkedDoRepository hoursWorkedDoRepository, MasterConfigUtils masterConfigUtils, AuditEventDORepository auditEventDORepository) {
        super(hoursWorkedDoRepository);
        this.hoursWorkedDoRepository = hoursWorkedDoRepository;
        this.masterConfigUtils = masterConfigUtils;
        this.auditEventDORepository = auditEventDORepository;
    }

    @Override
    public List<HoursWorked> findHoursWorkedByCriteria(HoursWorkedCriteria hoursWorkedCriteria) {
        List<HoursWorked> hoursWorkedList = hoursWorkedDoRepository.findEmployeeHoursWorkedBetweenTwoDatesInclusive(hoursWorkedCriteria.getEmployeeUUID(), hoursWorkedCriteria.getStartDate(), hoursWorkedCriteria.getEndDate(), hoursWorkedCriteria.getRevisionNumber());
        List<HoursWorked> duplicateHoursWorkedToRemove = getDuplicateHoursToRemove(hoursWorkedList);
        removeDuplicatesFromDb(duplicateHoursWorkedToRemove);
        hoursWorkedList.removeAll(duplicateHoursWorkedToRemove);
        return hoursWorkedList;
    }

    private void removeDuplicatesFromDb(List<HoursWorked> duplicateHoursWorkedToRemove) {
        hoursWorkedDoRepository.deleteAll(duplicateHoursWorkedToRemove);
        auditEventDORepository.deleteByHoursWorkedID(duplicateHoursWorkedToRemove.stream().map(Entity::getId).collect(Collectors.toList()));
    }

    private List<HoursWorked> getDuplicateHoursToRemove(List<HoursWorked> hoursWorkedList) {
        List<HoursWorked> duplicateHoursWorkedToRemove = new ArrayList<>();
        Map<AuthorizeChargeDetail, Map<String, List<HoursWorked>>> duplicateHoursMap = getDuplicateHoursDates(hoursWorkedList);
        duplicateHoursMap.forEach((authorizeChargeDetail, stringListMap) -> stringListMap.forEach((s, hoursWorkeds) -> duplicateHoursWorkedToRemove.addAll(hoursWorkeds.subList(0, hoursWorkeds.size() - 1))));
        return duplicateHoursWorkedToRemove;
    }

    private Map<AuthorizeChargeDetail, Map<String, List<HoursWorked>>> getDuplicateHoursDates(List<HoursWorked> hoursWorkedList) {
        Map<AuthorizeChargeDetail, Map<String, List<HoursWorked>>> duplicateHoursDates = new HashMap<>();
        getHoursWorkedMap(hoursWorkedList).forEach((date, hoursWorkeds) -> {
            if (hoursWorkeds.size() > 1) {
                for (HoursWorked hoursWorked : hoursWorkeds) {
                    if (hoursWorkeds.stream().filter(hoursWorked1 -> hoursWorked1.getAuthorizeChargeDetail().equals(hoursWorked.getAuthorizeChargeDetail())).count() > 1) {
                        if (duplicateHoursDates.containsKey(hoursWorked.getAuthorizeChargeDetail())) {
                            if (duplicateHoursDates.get(hoursWorked.getAuthorizeChargeDetail()).containsKey(date)) {
                                duplicateHoursDates.get(hoursWorked.getAuthorizeChargeDetail()).get(date).add(hoursWorked);
                            } else {
                                List<HoursWorked> hoursWorkList = new ArrayList<>();
                                hoursWorkList.add(hoursWorked);
                                duplicateHoursDates.get(hoursWorked.getAuthorizeChargeDetail()).put(date, hoursWorkList);
                            }
                        } else {
                            List<HoursWorked> hoursWorkList = new ArrayList<>();
                            Map<String, List<HoursWorked>> hoursWorkedMap = new HashMap<>();
                            hoursWorkList.add(hoursWorked);
                            hoursWorkedMap.put(date, hoursWorkList);
                            duplicateHoursDates.put(hoursWorked.getAuthorizeChargeDetail(), hoursWorkedMap);
                        }
                    }
                }
            }
        });
        return duplicateHoursDates;
    }

    private Map<String, List<HoursWorked>> getHoursWorkedMap(List<HoursWorked> hoursWorkedList) {
        Map<String, List<HoursWorked>> hoursWorkedMapByDate = new HashMap<>();
        for (HoursWorked hoursWorked : hoursWorkedList) {
            if (hoursWorkedMapByDate.containsKey(hoursWorked.getWorkedDate().dateOnlyStandardFormat())) {
                hoursWorkedMapByDate.get(hoursWorked.getWorkedDate().dateOnlyStandardFormat()).add(hoursWorked);
            } else {
                List<HoursWorked> hoursWorkList = new ArrayList<>();
                hoursWorkList.add(hoursWorked);
                hoursWorkedMapByDate.put(hoursWorked.getWorkedDate().dateOnlyStandardFormat(), hoursWorkList);
            }
        }
        return hoursWorkedMapByDate;
    }

    private void setDatabase() throws CompanyNotFoundException {
        String dbName = masterConfigUtils.getDBForDomain(CompanyHolder.getCompanyName());
        MultiTenantMongoDbFactory.setDatabaseNameForCurrentThread(dbName);
    }
}