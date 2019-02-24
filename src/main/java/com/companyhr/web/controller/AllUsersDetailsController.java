package com.companyhr.web.controller;

import com.companyhr.model.Employee;
import com.companyhr.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AllUsersDetailsController {
    @Autowired
    EmployeeRepository employeeRepository;

    @RequestMapping("/allusersdetails")
    public String viewAllUserDetails(Model model) {
        List<Employee> allEmployees = employeeRepository.findAll();

        model.addAttribute("employees", allEmployees);
        return "pendingusers";
    }
}
