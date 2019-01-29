package com.companyhr.service;

import com.companyhr.model.EmployeeCredentials;

public interface EmployeeService {
    void save(EmployeeCredentials user);

    EmployeeCredentials findByUsername(String username);
}
