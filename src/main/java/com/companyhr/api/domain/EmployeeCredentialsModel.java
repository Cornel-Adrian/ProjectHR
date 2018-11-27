package com.companyhr.api.domain;

public class EmployeeCredentialsModel extends AbstractModel {

    private int employeeCredetialsId;
    private int employeeId;
    private String username;
    private String password;
    private int jobId;

    public EmployeeCredentialsModel() {

    }

    public int getEmployeeCredetialsId() {
        return employeeCredetialsId;
    }

    public void setEmployeeCredetialsId(int employeeCredetialsId) {
        this.employeeCredetialsId = employeeCredetialsId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }
}
