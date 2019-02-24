package com.companyhr.web.controller;

import com.companyhr.model.Employee;
import com.companyhr.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PendingUserController {
    @Autowired
    EmployeeRepository employeeRepository;

    @RequestMapping("/pendingusers")
    public String viewAllVacancies(Model model) {
        List<Employee> allEmployees = employeeRepository.findAll();
        List<Employee> pendingEmployees = new ArrayList<>();
        for (Employee e : allEmployees) {
            if (e.getSalary() == -1) {
                pendingEmployees.add(e);
            }
        }
        model.addAttribute("employees", pendingEmployees);
        return "pendingusers";
    }
}
