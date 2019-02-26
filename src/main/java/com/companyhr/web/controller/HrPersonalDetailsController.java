package com.companyhr.web.controller;

import com.companyhr.controller.EmployeeCredentialsApiController;
import com.companyhr.model.Employee;
import com.companyhr.model.EmployeeCredentials;
import com.companyhr.repository.EmployeeCredentialsRepository;
import com.companyhr.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;


/**
 * Contains methods that allow HR user to register his/her's personal details
 */
@Controller

public class HrPersonalDetailsController {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeCredentialsRepository employeeCredentialsRepository;
    @Autowired
    EmployeeCredentialsApiController employeeCredentialsApiController;


    /**
     * GET method for HR personal details editing
     *
     * @param model the model Employee
     * @return mapping for for HR personal details editing page
     */
    @RequestMapping(value = "/registrationpersonaldetailshr", method = RequestMethod.GET)
    public String edithrdetails(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        EmployeeCredentials employeeCredentials = employeeCredentialsRepository.findByUsername(username);

        Optional<Employee> employee = null;
        if (employeeRepository.findById(employeeCredentials.getId()).isPresent()) {
            employee = employeeRepository.findById(employeeCredentials.getId());
        }

        model.addAttribute("employee", employee);
        return "registrationdetailshr";
    }

    /**
     *  POST method for HR personal details editing
     * @param employee The current HR user
     * @param bindingResult allows for  Validator to be applied, and adds binding-specific analysis and model building.
     * @param model the model
     * @return mapping for HR homepage
     */
    @RequestMapping(value = "/registrationpersonaldetailshr", method = RequestMethod.POST)
    public String updateuser(@ModelAttribute("employee") Employee employee, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "registrationdetailshr";
        }

        employeeRepository.save(employee);

        if (employeeCredentialsRepository.findById(employee.getId()).isPresent()) {
            EmployeeCredentials employeeCredentials = (employeeCredentialsRepository.findById(employee.getId()).get());
            employeeCredentials.setDaysOffCredits(employee.getDaysOffCredits());
            employeeCredentialsApiController.updateEmployeeCredentials(employeeCredentials);
        }


        return "redirect:/restricted/hrhomepage";
    }
}
