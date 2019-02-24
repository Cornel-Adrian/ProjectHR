package com.companyhr.web.controller;

import com.companyhr.controller.EmployeeCredentialsApiController;
import com.companyhr.model.Employee;
import com.companyhr.model.EmployeeCredentials;
import com.companyhr.repository.EmployeeCredentialsRepository;
import com.companyhr.repository.EmployeeRepository;
import com.companyhr.service.EmployeeService;
import com.companyhr.service.SecurityService;
import com.companyhr.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Calendar;
import java.util.Random;

/**
 * The Initialisation controller for the first run of the application.
 * @author HrManagerTeam
 */
@Controller

public class InitController {
    /**
     * The Employee credentials repository.
     */
    @Autowired
    EmployeeCredentialsRepository employeeCredentialsRepository;
    /**
     * The Employee repository.
     */
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserDetailsService userDetailsService;


    /**
     * The initialisation method GET mapping
     *
     * @param model the model
     * @return the template
     *
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String intialise(Model model) {
        model.addAttribute("userForm", new EmployeeCredentials());
        return "/init";
    }

    /**
     * The initialisation method POST mapping
     *
     * @param employeeCredentials the employee credentials model
     * @param bindingResult       the binding result validation
     * @param model               the model
     * @return the template
     */
    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public String intialise(@ModelAttribute("employeeCredentials") EmployeeCredentials employeeCredentials, BindingResult bindingResult, Model model) {
        userValidator.validate(employeeCredentials, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/error";
        }
        Random random = new Random();
        long employeeID = Math.abs(random.nextLong());
        while (employeeCredentialsRepository.findByEmployeeId(employeeID) != null) {
            employeeID = Math.abs(random.nextLong());
        }

        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();

        } else {
            username = principal.toString();
        }
        EmployeeCredentials tempuserremoval = employeeCredentialsRepository.findByUsername(username);
        employeeCredentialsRepository.delete(tempuserremoval);

        employeeCredentials.setEmployeeId(employeeID);
        employeeCredentials.setJobId((long) 1);
        employeeService.save(employeeCredentials);
        securityService.autologin(employeeCredentials.getUsername(), employeeCredentials.getPassword());
        employeeCredentials = employeeCredentialsRepository.findByEmployeeId(employeeID);
        Employee employee = new Employee();
        employee.setFirstName("Superuser");
        employee.setLastName("Admin");
        employee.setId(employeeCredentials.getId());
        employee.setJobId(employeeCredentials.getJobId());
        employee.setSalary((double) -1);
        employee.setExperience(-1);
        employee.setDaysOffCredits((long) -1);
        employee.setAddress("Proxima Centauri, No 1 Asteroid on the right");
        employee.setDepartmentId(0);
        employee.setEmploymentDate(Calendar.getInstance().getTime());
        employeeRepository.save(employee);

        return "/restricted/adminhomepage";
    }
}
