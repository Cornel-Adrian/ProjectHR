package com.companyhr.service;

import com.companyhr.model.Employee;
import com.companyhr.model.EmployeeCredentials;
import com.companyhr.repository.EmployeeCredentialsRepository;
import com.companyhr.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeCredentialsRepository employeeCredentialsRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

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
    public void save(Employee user) {
        employeeRepository.save(user);
    }

    @Override
    public EmployeeCredentials findByUsername(String username) {
        return employeeCredentialsRepository.findByUsername(username);

    }
}

