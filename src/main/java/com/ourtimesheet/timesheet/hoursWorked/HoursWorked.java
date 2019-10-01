package com.ourtimesheet.timesheet.hoursWorked;

import com.ourtimesheet.common.Entity;
import com.ourtimesheet.datetime.OurDateTime;
import com.ourtimesheet.employee.Employee;
import com.ourtimesheet.paidTimeOff.Leave;
import com.ourtimesheet.paytype.PayType;
import com.ourtimesheet.timesheet.chargeCode.AuthorizedCharge;
import com.ourtimesheet.timesheet.chargeCode.ChargeCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hassan on 6/7/16.
 */
@Document(collection = "hourWorked")
public class HoursWorked extends Entity {

    private final OurDateTime workedDate;
    private final AuthorizedCharge authorizedCharge;
    @DBRef
    private final Employee employee;
    private List<Note> notesList;
    private double hours;
    private int revisionNumber;
    private boolean billable;
    private double overtime;
    private PayType payType;
    private double doubleOvertime;
    private double miscellaneousHours;

    @PersistenceConstructor
    protected HoursWorked(UUID id, OurDateTime workedDate, double hours, List<Note> notesList, AuthorizedCharge authorizedCharge,
                          Employee employee, boolean billable, int revisionNumber, double overtime, PayType payType) {
        super(id);
        this.workedDate = workedDate;
        this.hours = hours;
        this.notesList = notesList;
        this.authorizedCharge = authorizedCharge;
        this.employee = employee;
        this.billable = billable;
        this.revisionNumber = revisionNumber;
        this.overtime = overtime;
        this.payType = payType;
    }

    public AuthorizeChargeDetail getAuthorizeChargeDetail() {
        return new AuthorizeChargeDetail(authorizedCharge, payType);
    }

    public double getMiscellaneousHours() {
        return miscellaneousHours;
    }

    public void addMiscellaneousHours(double miscellaneousHours) {
        this.miscellaneousHours = miscellaneousHours;
    }

    public OurDateTime getWorkedDate() {
        return workedDate;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public List<Note> getNotesList() {
        return notesList == null ? new ArrayList<>() : notesList.stream().sorted(Comparator.comparing(Note::getTimestamp)).collect(Collectors.toList());
    }

    public void setNotesList(List<Note> notesList) {
        this.notesList = notesList;
    }

    public AuthorizedCharge authorizedCharge() {
        return authorizedCharge;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void addHours(double hours) {
        this.hours += hours;
    }

    public String hourWorkedDateOnlyFormatted() {
        return workedDate.dateOnlyStandardFormat();
    }

    public String hourWorkedWeekDay() {
        return workedDate.format("EEEE");
    }

    public String hourWorkedTimeOnly() {
        return String.valueOf(hours);
    }

    public int getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(int revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public int revise() {
        return revisionNumber + 1;
    }

    public boolean isBillable() {
        return billable;
    }

    public double getOvertime() {
        return overtime;
    }

    public double getActualHours() {
        return hours - overtime;
    }

    public String getEmployeeId() {
        return employee.getId().toString();
    }

    public void updateBillable(boolean billable) {
        this.billable = billable;
    }

    public PayType getPayType() {
        return payType;
    }

    public PayType getLeavePayType() {
        return isLeave() ? Objects.requireNonNull(getLeave()).getPayType() : null;
    }

    public String getPayTypeName() {
        return payType != null ? payType.getName() : StringUtils.EMPTY;
    }

    public double getDoubleOvertime() {
        return doubleOvertime;
    }

    public void addPayType(PayType payType) {
        this.payType = payType;
    }

    private void updateDoubleOvertime(double doubleOvertime) {
        this.doubleOvertime = doubleOvertime;
    }

    public void updateOverTime(double overtime) {
        if (overtime != 0) {
            Assert.isTrue(BigDecimal.valueOf(hours).setScale(2, RoundingMode.HALF_UP).doubleValue() >= overtime, "Overtime cannot be greater then added hours");
            this.overtime = overtime;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HoursWorked hoursWorked = (HoursWorked) o;

        return new EqualsBuilder()
                .append(getId(), hoursWorked.getId())
                .append(workedDate, hoursWorked.workedDate)
                .append(hours, hoursWorked.hours)
                .append(authorizedCharge, hoursWorked.authorizedCharge)
                .append(employee, hoursWorked.employee)
                .append(revisionNumber, hoursWorked.revisionNumber)
                .append(billable, hoursWorked.billable)
                .isEquals();
    }

    public String authorizedCharges() {
        List<String> chargeCodes = new ArrayList<>();
        authorizedCharge.getAuthorizedCharge().stream().filter(Objects::nonNull).forEach(chargeCode1 -> chargeCodes.add(chargeCode1.getName()));
        return chargeCodes.toString();
    }

    public boolean hasOvertime() {
        return getOvertime() > 0;
    }

    public boolean hasDoubleOvertime() {
        return getDoubleOvertime() > 0;
    }

    public boolean hasRegular() {
        return getHours() > 0;
    }

    public boolean isLeave() {
        boolean isLeave = false;
        for (ChargeCode chargeCode : authorizedCharge.getAuthorizedCharge()) {
            if (chargeCode != null && chargeCode.getChargeCodeName().equals("Leave")) {
                isLeave = true;
            }
        }
        return isLeave;
    }

    public boolean isRegular() {
        return hours != overtime;
    }


    public PayType getEffectiveRegularPayType() {
        try {
            return employee.getRegularPayType(workedDate).getPayType();
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

    public PayType getEffectiveOvertimePayType() {
        try {
            return employee.getOvertimePayType(workedDate).getPayType();
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

    private Leave getLeave() {
        Optional<ChargeCode> leave = authorizedCharge.getAuthorizedCharge().stream().filter(chargeCode -> chargeCode != null && chargeCode.getChargeCodeName().equalsIgnoreCase("Leave")).findFirst();
        return (Leave) leave.orElse(null);
    }

    public String getLatestComment() {
        return getNotesList().isEmpty() ? StringUtils.EMPTY : getNotesList().get(getNotesList().size() - 1).getMessage();
    }

    public static class Builder {

        private UUID id;
        private OurDateTime workedDate;
        private double hours;
        private List<Note> notesList;
        private AuthorizedCharge authorizedCharge;
        private Employee employee;
        private boolean billable;
        private int revisionNumber;
        private double overtime;
        private PayType payType;
        private double doubleOvertime;
        private double miscellaneousHours;

        public Builder(UUID id, OurDateTime workedDate, double hours) {
            this.id = id;
            this.workedDate = workedDate;
            if (hours != 0) {
                Assert.isTrue(hours <= 24, "HourWorked cannot be greater then 24 hours");
                this.hours = hours;
            }
        }

        public Builder withEmployee(Employee employee) {
            this.employee = employee;
            return this;
        }

        public Builder withNotes(List<Note> notes) {
            this.notesList = notes;
            return this;
        }

        public Builder withAuthorizeCharge(AuthorizedCharge authorizeCharge) {
            this.authorizedCharge = authorizeCharge;
            return this;
        }

        public Builder withBillable(boolean billable) {
            this.billable = billable;
            return this;
        }

        public Builder withOverTime(double overtime) {
            if (overtime != 0) {
                Assert.isTrue(hours >= overtime, "Overtime cannot be greater then added hours");
                this.overtime = overtime;
            }
            return this;
        }

        public Builder withDoubleOverTime(double doubleOvertime) {
            if (doubleOvertime != 0) {
                Assert.isTrue(hours >= doubleOvertime, "Double Overtime cannot be greater then added hours");
                this.doubleOvertime = doubleOvertime;
            }
            return this;
        }

        public Builder withRevisionNumber(int revisionNumber) {
            this.revisionNumber = revisionNumber;
            return this;
        }

        public Builder withPayType(PayType payType) {
            if (payType != null) {
                this.payType = payType;
            }
            return this;
        }

        public Builder withMiscellaneousHours(double miscellaneousHours) {
            this.miscellaneousHours = miscellaneousHours;
            return this;
        }

        public HoursWorked buildHoursWorked() {
            HoursWorked hoursWorked = new HoursWorked(id, workedDate, hours, notesList, authorizedCharge, employee, billable, revisionNumber, overtime, payType);
            hoursWorked.updateDoubleOvertime(doubleOvertime);
            hoursWorked.addMiscellaneousHours(miscellaneousHours);
            return hoursWorked;
        }
    }
}