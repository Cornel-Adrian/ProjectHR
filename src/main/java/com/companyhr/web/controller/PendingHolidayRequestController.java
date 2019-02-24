package com.companyhr.web.controller;

import com.companyhr.model.DaysOff;
import com.companyhr.repository.DaysOffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PendingHolidayRequestController {

    @Autowired
    DaysOffRepository daysOffRepository;

    @RequestMapping("/approvependingvacancies")
    public String viewAllPendingHolidayRequests(Model model) {
        String username;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        List<DaysOff> listOfdays = daysOffRepository.findAll();
        List<DaysOff> listOfApprovedDaysOff = new ArrayList<>();
        for (DaysOff daysOff : listOfdays) {
            if (daysOff.getStatus() == 0) {
                listOfApprovedDaysOff.add(daysOff);
            }
        }
        model.addAttribute("daysoff", listOfApprovedDaysOff);
        return "approvependingvacancies";
    }
}

