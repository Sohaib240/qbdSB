package com.ourtimesheet.overtimeConfiguration;

import com.ourtimesheet.paytype.PayType;

/**
 * Created by Abdus Salam on 3/7/2018.
 */
public class WeeklyOvertimeConfiguration extends OvertimeConfiguration {

    public WeeklyOvertimeConfiguration(double hours, PayType payType) {
        super(hours, payType);
    }


    @Override
    public int getOrder() {
        return 1;
    }

}