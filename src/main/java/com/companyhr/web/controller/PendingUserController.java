package com.companyhr.web.controller;

import com.companyhr.model.Employee;
import com.companyhr.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * The Pending user controller.
 */
@Controller
public class PendingUserController {
	/**
	 * The Employee repository.
	 */
	@Autowired
    EmployeeRepository employeeRepository;

	/**
	 * View all pending users.
	 *
	 * @param model the model
	 * @return the template
	 */
	@RequestMapping("/pendingusers")
    public String viewAllPendingUsers(Model model) {
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
