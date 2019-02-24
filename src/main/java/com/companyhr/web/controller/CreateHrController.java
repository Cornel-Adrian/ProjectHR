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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

@Controller
public class CreateHrController {
    @Autowired
    EmployeeCredentialsRepository employeeCredentialsRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeCredentialsApiController employeeCredentialsApiController;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/createhr", method = RequestMethod.GET)
    public String createhr(Model model) {
        List<EmployeeCredentials> employeeCredentialsList = employeeCredentialsRepository.findAll();

        model.addAttribute("userForm", new EmployeeCredentials());

        return "/createhr";
    }

    @RequestMapping(value = "/createhr", method = RequestMethod.POST)
    public String createhr(@ModelAttribute("employeeCredentials") EmployeeCredentials employeeCredentials, BindingResult bindingResult, Model model) {

        userValidator.validate(employeeCredentials, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/createhr";
        }
        employeeCredentials.setJobId((long) 2);
        Random random = new Random();
        long employeeID = Math.abs(random.nextLong());
        while (employeeCredentialsRepository.findByEmployeeId(employeeID) != null) {
            employeeID = Math.abs(random.nextLong());
        }
        employeeCredentials.setEmployeeId(employeeID);
        employeeService.save(employeeCredentials);
        employeeCredentials = employeeCredentialsRepository.findByEmployeeId(employeeID);
        Employee employee = new Employee();
        employee.setFirstName("Superuser");
        employee.setLastName("HR");
        employee.setId(employeeCredentials.getId());
        employee.setJobId(employeeCredentials.getJobId());
        employee.setSalary((double) -1);
        employee.setExperience(-1);
        employee.setDaysOffCredits((long) -1);
        employee.setAddress("Proxima Centauri, No 1 Asteroid on the right");
        employee.setDepartmentId(0);
        employee.setEmploymentDate(Calendar.getInstance().getTime());
        employeeRepository.save(employee);
        return "redirect:/restricted/adminhomepage";
    }
}
