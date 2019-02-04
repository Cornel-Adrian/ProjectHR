package com.companyhr.service;

import com.companyhr.model.Employee;
import com.companyhr.model.EmployeeCredentials;

public interface EmployeeService {
    void save(EmployeeCredentials user);
    void save(Employee user);
    EmployeeCredentials findByUsername(String username);
}
