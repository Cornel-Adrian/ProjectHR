package com.companyhr.api.domain;

import java.util.Date;

public class DaysOffModel extends AbstractModel {

    private int daysOffId;
    private int employeeId;
    private Date startDate;
    private Date endDate;
    private String reasonLeave;
    private int status;
    private int daysOffType;


    public DaysOffModel(){

    }
    public int getDaysOffId() {
        return daysOffId;
    }

    public void setDaysOffId(int daysOffId) {
        this.daysOffId = daysOffId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getReasonLeave() {
        return reasonLeave;
    }

    public void setReasonLeave(String reasonLeave) {
        this.reasonLeave = reasonLeave;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDaysOffType() {
        return daysOffType;
    }

    public void setDaysOffType(int daysOffType) {
        this.daysOffType = daysOffType;
    }


}
