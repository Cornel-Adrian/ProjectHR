package com.companyhr.domain;

public class DepartmentModel extends AbstractModel {

    private int departamentId;
    private String name;

    public DepartmentModel(){
    }

    public int getDepartamentId() {
        return departamentId;
    }

    public void setDepartamentId(int departamentId) {
        this.departamentId = departamentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
