package com.companyhr.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorPageController {

        @Value("${spring.application.name}")
        String appName;

        @GetMapping("/error")
        public String loginPage(Model model) {
            model.addAttribute("appName", appName);
            return "error";
        }
    }
