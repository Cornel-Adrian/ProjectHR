package com.companyhr.web.controller;

import com.companyhr.controller.EmployeeCredentialsApiController;
import com.companyhr.model.Employee;
import com.companyhr.model.EmployeeCredentials;
import com.companyhr.repository.EmployeeCredentialsRepository;
import com.companyhr.service.EmployeeService;
import com.companyhr.service.SecurityService;
import com.companyhr.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class LoginPageController {
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

    @Autowired
    EmployeeCredentialsRepository employeeCredentialsRepository;

    @Autowired
    EmployeeCredentialsApiController employeeCredentialsApiController;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new EmployeeCredentials());

        return "registration";

    }

    @RequestMapping(value = "/registrationdetails", method = RequestMethod.GET)
    public String registrationdetails(Model model) {
        model.addAttribute("userForm", new Employee());

        return "registrationdetails";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("employeeCredentials") EmployeeCredentials employeeCredentials, BindingResult bindingResult, Model model) {
        userValidator.validate(employeeCredentials, bindingResult);


        if (bindingResult.hasErrors()) {
            return "registration";
        }

        employeeService.save(employeeCredentials);

        securityService.autologin(employeeCredentials.getUsername(), employeeCredentials.getPassword());

        return "redirect:/registrationdetails";
    }

    @RequestMapping(value = "/setcredits", method = RequestMethod.GET)
    public String setCredits(Model model) {
        model.addAttribute("userForm", new EmployeeCredentials());

        return "setcredits";
    }

    @RequestMapping(value = "/setcredits", method = RequestMethod.POST)
    public String setCredits(@ModelAttribute("employeeCredentials") EmployeeCredentials employeeCredentials, BindingResult bindingResult, Model model) {

        String username;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        employeeCredentials.setUsername(username);
        employeeCredentialsApiController.updateEmployeeCredentials(employeeCredentials);
        EmployeeCredentials employeeCredentials1 = employeeCredentialsRepository.findByUsername(username);

        if (employeeCredentials1.getJobId() == 2) {
            return "redirect:/restricted/hrhomepage";
        }
        if (employeeCredentials1.getJobId() == 1) {
            return "redirect:/restricted/userhomepage";
        }

        return "redirect:/restricted/userhomepage";
    }

    @RequestMapping(value = "/registrationdetails", method = RequestMethod.POST)
    public String registration(@ModelAttribute("employee") Employee employee, BindingResult bindingResult, Model model) {

        /*if (bindingResult.hasErrors()) {
            return "registrationdetails";
        }*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        employee.setJobId(employeeService.findByUsername(currentPrincipalName).getJobId());
        employee.setId(employeeService.findByUsername(currentPrincipalName).getId());
        EmployeeCredentials employeeCredentials = employeeCredentialsRepository.findByUsername(currentPrincipalName);
        employeeCredentials.setDaysOffCredits(employee.getDaysOffCredits());

        employeeService.save(employee);

        return "redirect:/setcredits";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("userForm", new EmployeeCredentials());

        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String dologin(@ModelAttribute("employeeCredentials") EmployeeCredentials employeeCredentials, BindingResult bindingResult, Model model) {
//        userValidator.validate(employeeCredentials, bindingResult);
        if (bindingResult.hasErrors()) {
            return "login";
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(employeeCredentials.getUsername());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, employeeCredentials.getPassword(), userDetails.getAuthorities());
        securityService.autologin(employeeCredentials.getUsername(), employeeCredentials.getPassword());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeCredentials employeeCredentials1 = employeeCredentialsRepository.findByUsername(((UserDetails) principal).getUsername());
        if ((employeeCredentials1.getJobId() == 2)) {
            return "redirect:/restricted/hrhomepage";
        }
        if (employeeCredentials1.getJobId() == 1) {
            return "redirect:/restricted/adminhomepage";
        }
        return "redirect:/restricted/userhomepage";
    }
}

