package com.ourtimesheet.timesheet.punch;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.timesheet.increment.HoursIncrement;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by click chain on 4/26/2016.
 */
public class InOutMatrix {

    private final HoursIncrement hoursIncrement;

    private Map<String, List<InOut>> inOutMap;

    private Map<String, Duration> inOutTotalMap;

    public InOutMatrix(List<Punch> punches, OurDateTime startDate, OurDateTime endDate, HoursIncrement hoursIncrement) {
        this.hoursIncrement = hoursIncrement;
        initializeInOutMap(startDate, endDate);
        if (punches == null) {
            punches = new ArrayList<>();
        }
        populatePunches(punches);
        initializeInOutTotalMap(punches);
    }


    Map<String, Duration> getInOutTotalMap() {
        return inOutTotalMap;
    }

    private void initializeInOutTotalMap(List<Punch> punches) {
        if (punches.size() > 0) {
            Punch tempPunch = punches.get(0);
            Duration duration = Duration.ZERO;
            for (Punch punch : punches.subList(1, punches.size())) {
                if (tempPunch.getType().equals(PunchType.IN) && punch.getType().equals(PunchType.OUT)) {
                    duration = duration.plus(duration.withSeconds(TimeUnit.MILLISECONDS.toSeconds(punch.getPunchDate().getTime() - tempPunch.getPunchDate().getTime())));
                }
                if (punch.getPunchDateWithoutTime().compareTo(tempPunch.getPunchDateWithoutTime()) != 0) {
                    inOutTotalMap.put(tempPunch.getPunchDateWithoutTime(), duration);
                    duration = Duration.ZERO;
                }
                inOutTotalMap.put(punch.getPunchDateWithoutTime(), duration);
                tempPunch = punch;
            }
        }
    }

    private void initializeInOutMap(OurDateTime startDate, OurDateTime endDate) {
        int numberOfDays = startDate.daysBetween(endDate);
        inOutTotalMap = new HashMap<>(numberOfDays);
        inOutMap = new HashMap<>(numberOfDays);
        for (OurDateTime date = startDate; date.dateOnly().isBeforeOrSame(endDate.dateOnly()); date = date.plusDays(1)) {
            String dateOnly = date.dateOnlyStandardFormat();
            inOutMap.put(dateOnly, new ArrayList<>());
            inOutTotalMap.put(dateOnly, Duration.ZERO);
        }
    }

    private void populatePunches(List<Punch> punches) {
        if (punches.size() > 1) {
            punches.sort(Comparator.comparing(Punch::getPunchDate));
        }
        punches.forEach(this::addPunchToList);
    }

    private void addPunchToList(Punch punch) {
        String forDate = punch.getPunchDateWithoutTime();
        List<InOut> inOuts = inOutMap.get(forDate);
        if (inOuts != null && inOuts.isEmpty()) {
            inOuts.add(createInOut(punch));
        } else {
            InOut lastInOut = inOuts.get(inOuts.size() - 1);
            if (lastInOut.getOutPunch() == null) {
                if (punch.getType().equals(PunchType.IN)) {
                    inOuts.add(createInOut(punch));
                } else {
                    InOut inOut = new InOut(lastInOut.getInPunch(), punch);
                    inOuts.remove(inOuts.size() - 1);
                    inOuts.add(inOut);
                }
            } else {
                inOuts.add(createInOut(punch));
            }
        }
    }

    private InOut createInOut(Punch punch) {
        Punch inPunch = punch.getType().equals(PunchType.IN) ? punch : null;
        Punch outPunch = punch.getType().equals(PunchType.OUT) ? punch : null;
        return new InOut(inPunch, outPunch);
    }


    public Map<String, List<InOut>> getInOutMap() {
        return inOutMap;
    }


    //This should not be here, its not this class headache
    //the purpose of this method is for UI, responsibility lies there
    public int getLargestNumberOfInOutListByDate() {
        int size = 0;
        for (String date : inOutMap.keySet()) {
            size = inOutMap.get(date).size() > size ? inOutMap.get(date).size() : size;
        }
        return size;
    }

    public Map<String, String> calculateTotalPerDay() {
        return hoursIncrement.calculateTotalHoursFormatted(inOutTotalMap);
    }
}
