package com.companyhr.web.controller;

import com.companyhr.model.Employee;
import com.companyhr.model.EmployeeCredentials;
import com.companyhr.repository.EmployeeCredentialsRepository;
import com.companyhr.repository.EmployeeRepository;
import com.companyhr.service.EmployeeDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller


public class UserHomePageControler {

    @Autowired
    EmployeeCredentialsRepository employeeCredentialsRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeDetails employeeDetails;


    @RequestMapping(value = "/viewpersonaldetails")
    public String viewpersonaldetails(Model model) {



        String username1;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username1 = ((UserDetails) principal).getUsername();
        } else {
            username1 = principal.toString();
        }

        EmployeeCredentials employeeCredentials = employeeCredentialsRepository.findByUsername(username1);
        employeeDetails.setUsername(employeeCredentials.getUsername());

        Optional<Employee> employee1 = employeeRepository.findById(employeeCredentials.getId());
        if (employee1.isPresent()) {
            Employee newEmployee = employee1.get();

            employeeDetails.setName(newEmployee.getFirstName() + " " + newEmployee.getLastName());

            if (employeeCredentials.getJobId() == 1) {
                employeeDetails.setRole("admin");
            } else {
                if (employeeCredentials.getJobId() == 2) {
                    employeeDetails.setRole("HR");
                } else {
                    employeeDetails.setRole("user");
                }
            }
            employeeDetails.setEmployeeid(employeeCredentials.getEmployeeId().toString());
            employeeDetails.setDeparmentid(newEmployee.getDepartmentId().toString());
            employeeDetails.setSalary(newEmployee.getSalary().toString());
            List<EmployeeDetails> employeeDetailsList = Collections.singletonList(employeeDetails);

            model.addAttribute("employeeDetails", employeeDetailsList);


        }
        return "restricted/viewpersonaldetails";
    }
}
/*@RequestMapping("/restricted/viewcredits")
    public String listcredits(Model model) {

        String username;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        List<EmployeeCredentials> employeeId = Collections.singletonList(employeeCredentialsRepository.findByUsername(username));

        model.addAttribute("daysoff", employeeId);
        return "/restricted/viewcredits";
    }*/