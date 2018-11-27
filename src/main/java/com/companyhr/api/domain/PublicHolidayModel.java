package com.companyhr.api.domain;

import java.util.Date;

public class PublicHolidayModel extends AbstractModel {

    private int publicHolidayId;
    private Date startDate;
    private Date endDate;
    private String name;

    public PublicHolidayModel(){
        
    }

    public int getPublicHolidayId() {
        return publicHolidayId;
    }

    public void setPublicHolidayId(int publicHolidayId) {
        this.publicHolidayId = publicHolidayId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
