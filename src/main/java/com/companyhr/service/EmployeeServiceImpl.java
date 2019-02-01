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

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public void save(EmployeeCredentials employeeCredentials) {
        EmployeeCredentials newEmployeeCredentials = new EmployeeCredentials();
        newEmployeeCredentials.setUsername(employeeCredentials.getUsername());
        CharSequence password = employeeCredentials.getPassword();
        newEmployeeCredentials.setPassword(bCryptPasswordEncoder.encode(password));
        newEmployeeCredentials.setPassword_confirm(bCryptPasswordEncoder.encode(password));
        newEmployeeCredentials.setEmployee_id(employeeCredentials.getEmployee_id());
        newEmployeeCredentials.setJob_id(employeeCredentials.getJob_id());
        employeeCredentialsRepository.save(newEmployeeCredentials);
    }

    @Override
    public EmployeeCredentials findByUsername(String username) {
        return employeeCredentialsRepository.findByUsername(username);

    }
}

