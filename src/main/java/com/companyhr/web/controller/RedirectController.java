package com.companyhr.web.controller;

import com.companyhr.model.EmployeeCredentials;
import com.companyhr.repository.EmployeeCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectController {
    @Autowired
    EmployeeCredentialsRepository employeeCredentialsRepository;

    @RequestMapping(value = "/redirect")
    public String returnhome(Model model) {


        String username1;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username1 = ((UserDetails) principal).getUsername();
        } else {
            username1 = principal.toString();
        }

        EmployeeCredentials employeeCredentials = employeeCredentialsRepository.findByUsername(username1);
        if ((employeeCredentials.getJobId() == 2)) {
            return "redirect:/restricted/hrhomepage";
        }
        if (employeeCredentials.getJobId() == 1) {
            return "redirect:/restricted/adminhomepage";
        }
        return "redirect:/restricted/userhomepage";
    }

}

