package com.companyhr.web.controller;

import com.companyhr.model.Employee;
import com.companyhr.model.EmployeeCredentials;
import com.companyhr.repository.EmployeeCredentialsRepository;
import com.companyhr.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Controller

public class UserHomePageControler {

    @Autowired
    EmployeeCredentialsRepository employeeCredentialsRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @RequestMapping(value = "/viewpersonaldetails", method = RequestMethod.GET)

    public String viewpersonaldetails(Model model) {
        EmployeeDetails employeeDetails = new EmployeeDetails();
        String username1;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username1 = ((UserDetails) principal).getUsername();
        } else {
            username1 = principal.toString();
        }

        EmployeeCredentials employeeCredentials = employeeCredentialsRepository.findByUsername(username1);
        employeeDetails.setUsername(employeeCredentials.getUsername());

        Optional<Employee> employee = employeeRepository.findById(employeeCredentials.getId());
        if (employee.isPresent()) {
            Employee newEmployee = employee.get();
            employeeDetails.setName(newEmployee.getFirst_name() + " " + newEmployee.getLast_name());

//            model.addAttribute("username", employeeCredentials.getUsername());
//            model.addAttribute("name", newEmployee.getFirst_name() + " " + newEmployee.getLast_name());
            if (employeeCredentials.getJob_id() == 1) {
                employeeDetails.setRole("admin");
//                model.addAttribute("role", "admin");
            } else {
                if (employeeCredentials.getJob_id() == 2) {
                    employeeDetails.setRole("HR");
//                    model.addAttribute("role", "hr");

                } else {
                    employeeDetails.setRole("user");
//                    model.addAttribute("role", "user");
                }
            }
            employeeDetails.setEmployeeid(employeeCredentials.getEmployee_id().toString());
            employeeDetails.setDeparmentid(newEmployee.getDepartment_id().toString());
            employeeDetails.setSalary(newEmployee.getSalary().toString());
//            model.addAttribute("employeeid", employeeCredentials.getEmployee_id().toString());
//            model.addAttribute("departmentid", newEmployee.getDepartment_id().toString());
//            model.addAttribute("salary", newEmployee.getSalary().toString());
        }
        model.addAttribute("employeeDetails", employeeDetails);
        return "restricted/viewpersonaldetails";
    }
}
