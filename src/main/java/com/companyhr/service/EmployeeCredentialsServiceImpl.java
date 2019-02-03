package com.companyhr.service;


import com.companyhr.model.EmployeeCredentials;
import com.companyhr.repository.EmployeeCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeCredentialsServiceImpl implements EmployeeCredentialsService {
    @Autowired
    private EmployeeCredentialsRepository employeeCredentialsRepository;

    public boolean findByUsername(String username) {
        EmployeeCredentials employee = employeeCredentialsRepository.findByUsername(username);
        if (employee != null) {
            return true;
        }
        return false;
    }

    public EmployeeCredentials updateEmployeeCredentials(EmployeeCredentials employeeDetails) {
        String username = new String(employeeDetails.getUsername());
        EmployeeCredentials employee = employeeCredentialsRepository.findByUsername(username);
        employee.setDays_off_credits(employeeDetails.getDays_off_credits());
        EmployeeCredentials updatedEmployee = employeeCredentialsRepository.save(employee);
        return updatedEmployee;
    }

}

