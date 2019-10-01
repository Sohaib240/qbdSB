package com.ourtimesheet.timesheet.hoursWorked;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.overtimeConfiguration.DefaultOvertimeRule;
import com.ourtimesheet.overtimeConfiguration.OvertimeRule;
import com.ourtimesheet.paytype.PayType;
import com.ourtimesheet.timesheet.chargeCode.AuthorizedCharge;
import com.ourtimesheet.timesheet.chargeCode.LeaveAuthorizedCharge;
import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Talha on 6/13/2016.
 */
public class HoursWorkedMatrix {
    private Map<AuthorizedCharge, List<HoursWorked>> hoursWorkedMap = new LinkedHashMap<>();
    private Map<AuthorizeChargeDetail, List<HoursWorked>> hoursWorkedMapDetail = new LinkedHashMap<>();
    private Map<AuthorizedCharge, Double> hoursWorkedTotalMapByAuthorizeCharge = new LinkedHashMap<>();
    private Map<String, Double> hoursWorkedTotalMapByDate = new LinkedHashMap<>();
    private Map<String, Double> leaveHoursTotalMapByDate = new LinkedHashMap<>();
    private Map<String, Double> regularHoursTotalMapByDate = new LinkedHashMap<>();
    private Map<String, Double> miscHoursTotalMapByDate = new LinkedHashMap<>();
    private Map<String, Double> regularHoursWorkedTotalMapByDate = new LinkedHashMap<>();
    private Map<String, Double> overtimeHoursWorkedTotalMapByDate = new LinkedHashMap<>();
    private Map<String, Double> dailyOvertimeHoursWorkedTotalMapByDate = new LinkedHashMap<>();
    private Map<String, Double> doubleOvertimeHoursWorkedTotalMapByDate = new LinkedHashMap<>();

    public HoursWorkedMatrix(List<HoursWorked> hoursWorkedList, OvertimeRule overtimeRule) {
        if (hoursWorkedList == null) {
            hoursWorkedList = new ArrayList<>();
        }
        populateHoursWorkedList(hoursWorkedList, overtimeRule);
    }

    public Map<String, Double> getAllHoursWorkedDateMap() {
        Map<String, Double> allHoursWorkedDate = new LinkedHashMap<>(hoursWorkedTotalMapByDate);
        leaveHoursTotalMapByDate.forEach((date, hour) -> allHoursWorkedDate.replace(date, allHoursWorkedDate.get(date) + hour));
        return allHoursWorkedDate;
    }

    public Map<String, Double> getGrandTotalHoursWorkedDateByPayTypeMap() {
        Map<String, Double> allHoursWorkedDate = new LinkedHashMap<>(getAllHoursWorkedDateByPayTypeMap());
        miscHoursTotalMapByDate.forEach((date, hour) -> {
            if (allHoursWorkedDate.get(date) != null) {
                allHoursWorkedDate.replace(date, allHoursWorkedDate.get(date) == null ? 0 + hour : allHoursWorkedDate.get(date) + hour);
            } else {
                allHoursWorkedDate.put(date, hour);
            }
        });
        return allHoursWorkedDate;
    }

    public Map<String, Double> getAllHoursWorkedDateByPayTypeMap() {
        Map<String, Double> allHoursWorkedDate = new LinkedHashMap<>(regularHoursTotalMapByDate);
        overtimeHoursWorkedTotalMapByDate.forEach((date, hour) -> {
            if (allHoursWorkedDate.get(date) != null) {
                allHoursWorkedDate.replace(date, allHoursWorkedDate.get(date) == null ? 0 + hour : allHoursWorkedDate.get(date) + hour);
            } else {
                allHoursWorkedDate.put(date, hour);
            }
        });
        return allHoursWorkedDate;
    }

    public Double getHoursWorkedGrandTotal(OvertimeRule overtimeRule) {
        Double total = 0.0;
        if (overtimeRule != null && overtimeRule.hasPayType()) {
            total += getGrandTotalHoursWorkedDateByPayTypeMap().values().stream().mapToDouble(i -> i.doubleValue()).sum();
        } else {
            total += getLeaveHoursTotalMapByDate().values().stream().mapToDouble(i -> i.doubleValue()).sum();
            total += getRegularHoursWorkedTotalMapByDate().values().stream().mapToDouble(i -> i.doubleValue()).sum();
        }
        return total;
    }

    public OurDateTime getFirstWeekDate() {
        String date = getAllHoursWorkedDateMap().keySet().stream().sorted(Comparator.comparing(this::createDate)).findFirst().get();
        return createDate(date);
    }

    public OurDateTime getEndDate() {
        List<String> dates = new ArrayList<>(getAllHoursWorkedDateMap().keySet());
        String date = dates.get(dates.size() - 1);
        return createDate(date);
    }

    public boolean hasHours() {
        return hoursWorkedMap.size() > 0;
    }

    public Map<String, Double> getOverWorkedMapByDate() {
        return overtimeHoursWorkedTotalMapByDate;
    }

    public Map<String, Double> getDailyOvertimeHoursWorkedTotalMapByDate() {
        return dailyOvertimeHoursWorkedTotalMapByDate;
    }

    public Map<String, Double> getDoubleOvertimeHoursWorkedTotalMapByDate() {
        return doubleOvertimeHoursWorkedTotalMapByDate;
    }

    public Map<AuthorizedCharge, List<HoursWorked>> getHoursWorkedMap() {
        return hoursWorkedMap;
    }

    public Map<AuthorizedCharge, Double> getHoursWorkedTotalMapByAuthorizeCharge() {
        return hoursWorkedTotalMapByAuthorizeCharge;
    }

    public Map<String, Double> getHoursWorkedMapByDate() {
        return hoursWorkedTotalMapByDate;
    }

    public Map<String, Double> getLeaveHoursTotalMapByDate() {
        return leaveHoursTotalMapByDate;
    }

    public Map<String, Double> getRegularHoursWorkedTotalMapByDate() {
        return regularHoursWorkedTotalMapByDate;
    }

    public Map<AuthorizeChargeDetail, List<HoursWorked>> getHoursWorkedMapDetail() {
        return hoursWorkedMapDetail;
    }

    public Map<String, Double> getRegularHoursTotalMapByDate() {
        return regularHoursTotalMapByDate;
    }

    private void populateHoursWorkedList(List<HoursWorked> hoursWorkedList, OvertimeRule overtimeRule) {
        Map<String, Double> hoursWorkedMapByDate = new LinkedHashMap<>();
        for (HoursWorked hoursWorked : hoursWorkedList) {
            if (hoursWorkedMap.containsKey(hoursWorked.authorizedCharge())) {
                hoursWorkedMap.get(hoursWorked.authorizedCharge()).add(hoursWorked);
            } else {
                List<HoursWorked> hoursWorkList = new ArrayList<>();
                hoursWorkList.add(hoursWorked);
                hoursWorkedMap.put(hoursWorked.authorizedCharge(), hoursWorkList);
            }

            if (hoursWorkedMapDetail.containsKey(hoursWorked.getAuthorizeChargeDetail())) {
                hoursWorkedMapDetail.get(hoursWorked.getAuthorizeChargeDetail()).add(hoursWorked);
            } else {
                List<HoursWorked> hoursWorkList = new ArrayList<>();
                hoursWorkList.add(hoursWorked);
                hoursWorkedMapDetail.put(hoursWorked.getAuthorizeChargeDetail(), hoursWorkList);
            }

            if (hoursWorkedTotalMapByAuthorizeCharge.containsKey(hoursWorked.authorizedCharge())) {
                Double hours = hoursWorkedTotalMapByAuthorizeCharge.get(hoursWorked.authorizedCharge());
                hoursWorkedTotalMapByAuthorizeCharge.replace(hoursWorked.authorizedCharge(), hoursWorked.getHours() + hours);
            } else {
                hoursWorkedTotalMapByAuthorizeCharge.put(hoursWorked.authorizedCharge(), hoursWorked.getHours());
            }


            String hoursWorkedDate = hoursWorked.hourWorkedDateOnlyFormatted();
            if (overtimeRule != null && overtimeRule.hasPayType()) {
                if (overtimeRule.isRegularPayType(hoursWorked.getAuthorizeChargeDetail().getPayType())) {
                    if (regularHoursTotalMapByDate.containsKey(hoursWorkedDate)) {
                        Double hours = regularHoursTotalMapByDate.get(hoursWorkedDate);
                        regularHoursTotalMapByDate.replace(hoursWorkedDate, hoursWorked.getHours() + hours);
                    } else {
                        regularHoursTotalMapByDate.put(hoursWorkedDate, hoursWorked.getHours());
                    }
                } else if (hoursWorked.authorizedCharge().getClass().isAssignableFrom(LeaveAuthorizedCharge.class)) {
                    if (leaveHoursTotalMapByDate.containsKey(hoursWorkedDate)) {
                        Double hours = leaveHoursTotalMapByDate.get(hoursWorkedDate);
                        leaveHoursTotalMapByDate.replace(hoursWorkedDate, hoursWorked.getHours() + hours);
                    } else {
                        leaveHoursTotalMapByDate.put(hoursWorkedDate, hoursWorked.getHours());
                    }
                }
                if (!overtimeRule.isRegularPayType(hoursWorked.getAuthorizeChargeDetail().getPayType()) && !overtimeRule.isOvertimePayType(hoursWorked.getAuthorizeChargeDetail().getPayType())) {
                    if (miscHoursTotalMapByDate.containsKey(hoursWorkedDate)) {
                        miscHoursTotalMapByDate.replace(hoursWorkedDate, hoursWorked.getHours() + miscHoursTotalMapByDate.get(hoursWorkedDate));
                    } else {
                        miscHoursTotalMapByDate.put(hoursWorkedDate, hoursWorked.getHours());
                    }
                }
            } else {
                if (hoursWorked.authorizedCharge().getClass().isAssignableFrom(LeaveAuthorizedCharge.class)) {
                    hoursWorkedMapByDate = leaveHoursTotalMapByDate;
                } else {
                    hoursWorkedMapByDate = regularHoursWorkedTotalMapByDate;
                }
            }

            if (hoursWorkedMapByDate.containsKey(hoursWorkedDate)) {
                Double hours = hoursWorkedMapByDate.get(hoursWorkedDate);
                hoursWorkedMapByDate.replace(hoursWorkedDate, hoursWorked.getHours() + hours);
            } else {
                hoursWorkedMapByDate.put(hoursWorkedDate, hoursWorked.getHours());
            }

            if (this.hoursWorkedTotalMapByDate.get(hoursWorkedDate) != null) {
                Double hours = this.hoursWorkedTotalMapByDate.get(hoursWorkedDate);
                this.hoursWorkedTotalMapByDate.replace(hoursWorkedDate, hoursWorked.getHours() + hours);
            } else {
                this.hoursWorkedTotalMapByDate.put(hoursWorkedDate, hoursWorked.getHours());
            }

            if (isOvertime(hoursWorked, overtimeRule)) {
                if (overtimeHoursWorkedTotalMapByDate.containsKey(hoursWorkedDate)) {
                    Double hours = overtimeHoursWorkedTotalMapByDate.get(hoursWorkedDate);
                    overtimeHoursWorkedTotalMapByDate.replace(hoursWorkedDate, hoursWorked.getHours() + hours);
                } else {
                    overtimeHoursWorkedTotalMapByDate.put(hoursWorkedDate, hoursWorked.getHours());
                }
            }
            if (isDailyOvertime(hoursWorked, overtimeRule)) {
                if (dailyOvertimeHoursWorkedTotalMapByDate.containsKey(hoursWorkedDate)) {
                    Double hours = dailyOvertimeHoursWorkedTotalMapByDate.get(hoursWorkedDate);
                    dailyOvertimeHoursWorkedTotalMapByDate.replace(hoursWorkedDate, hoursWorked.getHours() + hours);
                } else {
                    dailyOvertimeHoursWorkedTotalMapByDate.put(hoursWorkedDate, hoursWorked.getHours());
                }
            }
            if (isDoubleOvertimePayType(hoursWorked.getPayType())) {
                if (doubleOvertimeHoursWorkedTotalMapByDate.containsKey(hoursWorkedDate)) {
                    Double hours = doubleOvertimeHoursWorkedTotalMapByDate.get(hoursWorkedDate);
                    doubleOvertimeHoursWorkedTotalMapByDate.replace(hoursWorkedDate, hoursWorked.getHours() + hours);
                } else {
                    doubleOvertimeHoursWorkedTotalMapByDate.put(hoursWorkedDate, hoursWorked.getHours());
                }
            }

        }

        for (AuthorizedCharge authorizeCharge : hoursWorkedMap.keySet()) {
            List<HoursWorked> hourWorkedList = hoursWorkedMap.get(authorizeCharge);
            if (hourWorkedList.size() > 1) {
                hourWorkedList.sort(Comparator.comparing(HoursWorked::getWorkedDate));
            }
            hoursWorkedMap.put(authorizeCharge, hourWorkedList);
        }
    }

    private boolean isDailyOvertime(HoursWorked hoursWorked, OvertimeRule overtimeRule) {
        if (overtimeRule != null && overtimeRule.getClass().isAssignableFrom(DefaultOvertimeRule.class)) {
            return overtimeRule.hasPayType() ? overtimeRule.isDailyOvertimePayType(hoursWorked.getAuthorizeChargeDetail().getPayType()) : hoursWorked.getOvertime() != 0D;
        }
        return false;
    }

    private boolean isDoubleOvertimePayType(PayType payType) {
        return payType != null && payType.isDoubleOvertime();
    }

    private boolean isOvertime(HoursWorked hoursWorked, OvertimeRule overtimeRule) {
        if (overtimeRule != null) {
            return overtimeRule.hasPayType() ? overtimeRule.isOvertimePayType(hoursWorked.getAuthorizeChargeDetail().getPayType()) : hoursWorked.getOvertime() != 0D;
        }
        return false;
    }

    private OurDateTime createDate(String date) {
        try {
            DateTime dateTime = new DateTime(new SimpleDateFormat("MM/dd/yyyy").parse(date));
            DateTime currentDateTime = OurDateTime.getCurrentDate(getTimeZone()).getDateTime();
            return new OurDateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), currentDateTime.getHourOfDay(), currentDateTime.getMinuteOfHour(), getTimeZone()).plusDays(1);
        } catch (Exception ex) {
            return null;
        }
    }

    private TimeZone getTimeZone() {
        return new ArrayList<>(hoursWorkedMap.values()).get(0).get(0).getWorkedDate().getEffectiveTimezone();
    }
}