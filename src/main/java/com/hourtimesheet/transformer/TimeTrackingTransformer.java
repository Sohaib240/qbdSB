package com.hourtimesheet.transformer;

import com.hourtimesheet.desktop.*;
import com.ourtimesheet.qbd.helper.TimesheetCarrier;
import com.ourtimesheet.timesheet.chargeCode.ChargeCode;
import com.ourtimesheet.timesheet.hoursWorked.HoursWorked;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdus Salam on 3/2/2017.
 */
public class TimeTrackingTransformer {

    public List<TimeTrackingAddRqType> transform(TimesheetCarrier timesheetCarrier) {
        List<TimeTrackingAddRqType> timeTrackingAddRqTypes = new ArrayList<>();
        for (HoursWorked hoursWorked : timesheetCarrier.getHoursWorked()) {
            timeTrackingAddRqTypes.add(createTimeTrackingAddRqType(hoursWorked));
        }
        return timeTrackingAddRqTypes;
    }

    private TimeTrackingAddRqType createTimeTrackingAddRqType(HoursWorked hoursWorked) {
        TimeTrackingAddRqType timeTrackingAddRqType = new TimeTrackingAddRqType();
        TimeTrackingAdd timeTrackingAdd = new TimeTrackingAdd();
        updateTimeTracking(hoursWorked, timeTrackingAdd);
        timeTrackingAddRqType.setTimeTrackingAdd(timeTrackingAdd);
        return timeTrackingAddRqType;
    }

    private void updateTimeTracking(HoursWorked hoursWorked, TimeTrackingAdd timeTrackingAdd) {
        for (ChargeCode chargeCode : hoursWorked.authorizedCharge().getAuthorizedCharge()) {

            if (chargeCode.getChargeCodeName().equalsIgnoreCase("Customer")) {
                CustomerRef customerRef = new CustomerRef();
                customerRef.setFullName(chargeCode.getName());
                customerRef.setListID(chargeCode.getId().toString());
                timeTrackingAdd.setCustomerRef(customerRef);

            } else if (chargeCode.getChargeCodeName().equalsIgnoreCase("Job")) {
                CustomerRef customerRef = new CustomerRef();
                customerRef.setFullName(chargeCode.getName());
                customerRef.setListID(chargeCode.getId().toString());
                timeTrackingAdd.setCustomerRef(customerRef);

            } else if (chargeCode.getChargeCodeName().equalsIgnoreCase("Class")) {
                ClassRef classRef = new ClassRef();
                classRef.setFullName(chargeCode.getName());
                classRef.setListID(chargeCode.getId().toString());
                timeTrackingAdd.setClassRef(classRef);

            } else if (chargeCode.getChargeCodeName().equalsIgnoreCase("ServiceItem")) {
                ItemServiceRef itemRef = new ItemServiceRef();
                itemRef.setFullName(chargeCode.getName());
                itemRef.setListID(chargeCode.getId().toString());
                timeTrackingAdd.setItemServiceRef(itemRef);
            }
        }
        timeTrackingAdd.setDuration(String.valueOf(hoursWorked.getHours()));
    }
}
