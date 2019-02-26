package com.companyhr.web.controller;


import com.companyhr.service.PersonService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Contains controller for home page
 * Deprecated
 * Replaced by Welcome page
 */
@Controller
public class HomePageController {

    @Value("${spring.application.name}")
    String appName;

    private PersonService personService;

    /**
     * Mapping for the initial homepage(start of project)- now restricted to ADMIN ROLE users- now DEPRECATED
     * Used to represent common endpoint in site map for authenticated users
     *
     * @param model the model
     * @return Mapping for the initial homepage(start of project)- now DEPRECATED
     */
    @GetMapping("/home")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        return "home";
    }
}
