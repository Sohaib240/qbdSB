package com.ourtimesheet.timesheet.punch;

import com.ourtimesheet.common.Entity;
import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.paytype.PayType;
import com.ourtimesheet.timesheet.chargeCode.ChargeCode;
import com.ourtimesheet.timesheet.hoursWorked.Note;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

/**
 * Created by click chain on 4/26/2016.
 */
@Document(collection = "punch")
public abstract class Punch extends Entity {

    private final OurDateTime punchDate;
    @DBRef
    private final Employee employee;
    private final int revisionNumber;
    @DBRef
    private List<ChargeCode> chargeCodes;
    @DBRef
    private PayType payType;
    private Note note;
    private boolean billable;

    @PersistenceConstructor
    public Punch(UUID id, OurDateTime punchDate, Employee employee, int revisionNumber,
                 List<ChargeCode> chargeCodes, PayType payType, Note note, boolean billable) {
        super(id);
        this.punchDate = punchDate;
        this.employee = employee;
        this.revisionNumber = revisionNumber;
        this.chargeCodes = chargeCodes;
        this.payType = payType;
        this.note = note;
        this.billable = billable;
    }

    public Punch(OurDateTime punchDate, Employee employee, int revisionNumber, List<ChargeCode> chargeCodes, PayType payType) {
        super();
        this.punchDate = punchDate;
        this.employee = employee;
        this.revisionNumber = revisionNumber;
        this.chargeCodes = chargeCodes;
        this.payType = payType;
    }

    public Employee getEmployee() {
        return employee;
    }

    public int getRevisionNumber() {
        return revisionNumber;
    }

    public abstract Punch updatePunchTime(Punch punch);

    public abstract PunchType getType();

    public abstract Punch revise();

    public OurDateTime getPunchDate() {
        return punchDate;
    }

    public String getPunchDateWithoutTime() {
        return punchDate.dateOnlyStandardFormat();
    }

    public String punchDateOnlyFormatted() {
        return punchDate.dateOnlyStandardFormat();
    }

    public String punchTimeOnlyFormatted() {
        return punchDate.timeOnlyStandardFormat();
    }

    public List<ChargeCode> getChargeCodes() {
        return chargeCodes;
    }

    public PayType getPayType() {
        return payType;
    }

    public Note getNote() {
        return note;
    }

    public void addChargeCodes(List<ChargeCode> chargeCodes) {
        this.chargeCodes = chargeCodes;
    }

    public void addPayType(PayType payType) {
        this.payType = payType;
    }

    public void addNote(Note note) {
        this.note = note;
    }

    public boolean isBillable() {
        return billable;
    }


    public boolean hasChargeCodes(){
        return !getChargeCodes().isEmpty();
    }
}