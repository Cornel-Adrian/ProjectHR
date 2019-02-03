package com.companyhr.controller;


import com.companyhr.exception.ResourceNotFoundException;
import com.companyhr.model.Employee;
import com.companyhr.repository.EmployeeCredentialsRepository;
import com.companyhr.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Service
@RestController
@RequestMapping("/employee")
public class EmployeeApiController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeCredentialsRepository employeeCredentialsRepository;

    @GetMapping("/getAllEmployee")
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @PostMapping("/addEmplyee")
    public Employee addEmployee(@Valid @RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
        employeeRepository.delete(employee);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getEmployee/{id}")
    public Employee getEmployee(@PathVariable(value = "id") Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
        return employee;
    }

    @PutMapping("/employee/{id}")
    public Employee updateEmployee(@PathVariable(value = "id") Long employeeId, @Valid @RequestBody Employee employeeDetails) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
        employee.setFirst_name(employeeDetails.getFirst_name());
        employee.setLast_name(employeeDetails.getLast_name());
        employee.setAddress(employeeDetails.getAddress());
        employee.setExperience(employeeDetails.getExperience());
        employee.setJob_id(employeeDetails.getJob_id());
        employee.setSalary(employeeDetails.getSalary());
        employee.setEmployment_date(employeeDetails.getEmployment_date());
        employee.setExperience(employeeDetails.getExperience());
        employee.setDepartment_id(employeeDetails.getDepartment_id());

        Employee updatedEmployee = employeeRepository.save(employee);
        return updatedEmployee;
    }
}