package com.companyhr.controller;


import com.companyhr.model.EmployeeCredentials;
import com.companyhr.repository.EmployeeCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
public class EmployeeCredentialsApiController {
    @Autowired
    private EmployeeCredentialsRepository employeeCredentialsRepository;

    public EmployeeCredentials updateEmployeeCredentials(EmployeeCredentials employeeDetails) {
        String username = new String(employeeDetails.getUsername());
        EmployeeCredentials employee = employeeCredentialsRepository.findByUsername(username);
        employee.setDays_off_credits(employeeDetails.getDays_off_credits());
        EmployeeCredentials updatedEmployee = employeeCredentialsRepository.save(employee);
        return updatedEmployee;
    }

}