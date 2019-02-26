package com.companyhr.web.controller;

import com.companyhr.model.Employee;
import com.companyhr.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Class holds method for viewing all the users that have incomplete details after registartion
 */
@Controller
public class AllUsersDetailsController {
    @Autowired
    EmployeeRepository employeeRepository;

    /**
     * @param model List of all employees with incomplete registration details
     * @return mapping for the HR accesible page that allows user selection for further details completion
     */
    @RequestMapping("/allusersdetails")
    public String viewAllUserDetails(Model model) {
        List<Employee> allEmployees = employeeRepository.findAll();

        model.addAttribute("employees", allEmployees);
        return "pendingusers";
    }
}
