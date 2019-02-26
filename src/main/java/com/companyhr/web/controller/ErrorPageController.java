package com.companyhr.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Class contains controller for error page
 */
@Controller
public class ErrorPageController {

    @Value("${spring.application.name}")
    String appName;

    /**
     * @param model the model
     * @return mapping for error page
     */
    @GetMapping("/error")
    public String loginPage(Model model) {
        model.addAttribute("appName", appName);
        return "error";
    }
}
