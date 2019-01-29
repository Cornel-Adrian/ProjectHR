package com.companyhr.service;

import com.companyhr.model.EmployeeCredentials;
import com.companyhr.repository.EmployeeCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeCredentialsRepository employeeCredentialsRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(EmployeeCredentials employeeCredentials) {
        employeeCredentials.setPassword(bCryptPasswordEncoder.encode(employeeCredentials.getPassword()));
        employeeCredentialsRepository.save(employeeCredentials);
    }

    @Override
    public EmployeeCredentials findByUsername(String username) {
        return employeeCredentialsRepository.findByUsername(username);

    }
}

