package com.ourtimesheet.timesheet.punch;

import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.paytype.PayType;
import com.ourtimesheet.timesheet.chargeCode.ChargeCode;
import com.ourtimesheet.timesheet.hoursWorked.Note;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Created by click chain on 4/27/2016.
 */
public class PunchOut extends Punch {

    @PersistenceConstructor
    public PunchOut(UUID id, OurDateTime punchDate, Employee employee, int revisionNumber, List<ChargeCode> chargeCodes, PayType payType, Note note, boolean billable) {
        super(id, punchDate, employee, revisionNumber, chargeCodes, payType, note, billable);
    }

    public static PunchOut punchOut(OurDateTime punchDate, Employee employee, int revisionNumber, List<ChargeCode> chargeCodes, PayType payType, Note note, boolean billable) {
        return new PunchOut(UUID.randomUUID(), punchDate, employee, revisionNumber, chargeCodes, payType, note, billable);
    }

    @Override
    public Punch updatePunchTime(Punch punch) {
        return new PunchOut(getId(), punch.getPunchDate(), getEmployee(), punch.getRevisionNumber(), punch.getChargeCodes(), punch.getPayType(), punch.getNote(), punch.isBillable());
    }

    @Override
    public PunchType getType() {
        return PunchType.OUT;
    }

    @Override
    public Punch revise() {
        return new PunchOut(getId(), getPunchDate(), getEmployee(), getRevisionNumber() + 1, getChargeCodes(), getPayType(), getNote(), isBillable());
    }
}
