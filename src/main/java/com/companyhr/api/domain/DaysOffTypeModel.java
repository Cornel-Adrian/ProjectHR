package com.companyhr.api.domain;

public class DaysOffTypeModel extends AbstractModel {

    private int daysOffTypeId;
    private String name;

    public DaysOffTypeModel(){

    }
    public int getDaysOffTypeId() {
        return daysOffTypeId;
    }

    public void setDaysOffTypeId(int daysOffTypeId) {
        this.daysOffTypeId = daysOffTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
