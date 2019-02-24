package com.companyhr.web.controller;

import com.companyhr.controller.EmployeeCredentialsApiController;
import com.companyhr.model.DaysOff;
import com.companyhr.repository.DaysOffRepository;
import com.companyhr.repository.EmployeeCredentialsRepository;
import com.companyhr.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Optional;

@Controller
public class RejectController {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeCredentialsRepository employeeCredentialsRepository;
    @Autowired
    EmployeeCredentialsApiController employeeCredentialsApiController;
    @Autowired
    DaysOffRepository daysOffRepository;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(value = "/reject/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model) {

        Optional<DaysOff> daysOffs = daysOffRepository.findById(id);
        if (daysOffRepository.findById(id).isPresent()) {
            DaysOff daysOff = (daysOffRepository.findById(id)).get();
            model.addAttribute("daysOff", daysOff);
            return "reject";
        }

        return "/error";


    }


    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    public String updateuser(@ModelAttribute("dayOff") DaysOff daysOff, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "redirect:/error";
        }
        daysOff.setStatus((long) 1);
        if (daysOffRepository.findById(daysOff.getId()) != null) {
            daysOffRepository.delete(daysOff);
        } else {
            return "redirect:/error";
        }
        return "redirect:/restricted/hrhomepage";
    }
}
