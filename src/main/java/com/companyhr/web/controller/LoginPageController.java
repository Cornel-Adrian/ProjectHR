package com.companyhr.web.controller;

import com.companyhr.model.EmployeeCredentials;
import com.companyhr.service.EmployeeService;
import com.companyhr.service.SecurityService;
import com.companyhr.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new EmployeeCredentials());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("employeeCredentials") EmployeeCredentials employeeCredentials, BindingResult bindingResult, Model model) {
        userValidator.validate(employeeCredentials, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        employeeService.save(employeeCredentials);

        securityService.autologin(employeeCredentials.getUsername(), employeeCredentials.getPassword());

        return "redirect:/restricted/afterlogin";
    }


    /*@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@ModelAttribute("employeeCredentials") EmployeeCredentials employeeCredentials, BindingResult bindingResult, Model model) {
        model.addAttribute("userForm", new EmployeeCredentials());
        if (bindingResult.hasErrors()) {
            return "error";
        }
        securityService.autologin(employeeCredentials.getUsername(), employeeCredentials.getPassword());

        return "redirect:/home";
    }*/
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("userForm", new EmployeeCredentials());

        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute("employeeCredentials") EmployeeCredentials employeeCredentials, BindingResult bindingResult, Model model) {
        userValidator.validate(employeeCredentials, bindingResult);
        if (bindingResult.hasErrors()) {
            return "login";
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(employeeCredentials.getUsername());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, employeeCredentials.getPassword(), userDetails.getAuthorities());
        securityService.autologin(employeeCredentials.getUsername(), employeeCredentials.getPassword());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            return "redirect:/restricted/afterlogin";
        } else {
            return "login";
        }

    }


  /*  public String loginprocess(@ModelAttribute("employeeCredentials") EmployeeCredentials employeeCredentials) {
        /*if (error != null)
            model.addAttribute("message", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");*/

}

   /* public String welcome(Model model) {
        return "welcome";
    }
}*/


