package com.companyhr.service;

import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class EmployeeDetails {
    public String username;
    private String name;
    private String role;
    private String employeeid;
    private String deparmentid;
    private String salary;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public String getDeparmentid() {
        return deparmentid;
    }

    public void setDeparmentid(String deparmentid) {
        this.deparmentid = deparmentid;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeDetails)) return false;
        EmployeeDetails that = (EmployeeDetails) o;
        return Objects.equals(getUsername(), that.getUsername()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getRole(), that.getRole()) &&
                Objects.equals(getEmployeeid(), that.getEmployeeid()) &&
                Objects.equals(getDeparmentid(), that.getDeparmentid()) &&
                Objects.equals(getSalary(), that.getSalary());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUsername(), getName(), getRole(), getEmployeeid(), getDeparmentid(), getSalary());
    }

    @Override
    public String toString() {
        return "EmployeeDetails{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", employeeid='" + employeeid + '\'' +
                ", deparmentid='" + deparmentid + '\'' +
                ", salary='" + salary + '\'' +
                '}';
    }
}
