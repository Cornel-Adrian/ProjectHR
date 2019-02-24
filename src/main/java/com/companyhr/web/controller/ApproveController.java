package com.companyhr.web.controller;

import com.companyhr.controller.EmployeeCredentialsApiController;
import com.companyhr.model.DaysOff;
import com.companyhr.model.Employee;
import com.companyhr.model.EmployeeCredentials;
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
public class ApproveController {
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

    @RequestMapping(value = "/approve/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model) {

        Optional<DaysOff> daysOffs = daysOffRepository.findById(id);
        if (daysOffRepository.findById(id).isPresent()) {
            DaysOff daysOff = (daysOffRepository.findById(id)).get();
            model.addAttribute("daysOff", daysOff);
            return "approve";
        }

        return "/error";


    }


    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    public String updateuser(@ModelAttribute("dayOff") DaysOff daysOff, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "redirect:/error";
        }
        daysOff.setStatus((long) 1);
        daysOffRepository.save(daysOff);
        if (employeeCredentialsRepository.findByEmployeeId(daysOff.getEmployeeId()) != null) {
            EmployeeCredentials employeeCredentials = employeeCredentialsRepository.findByEmployeeId(daysOff.getEmployeeId());
            employeeCredentials.setDaysOffCredits(employeeCredentials.getDaysOffCredits() - daysOff.getNumberOfWorkDays());
            employeeCredentialsApiController.updateEmployeeCredentials(employeeCredentials);

            if (employeeRepository.findById(employeeCredentials.getId()) != null) {
                if (employeeRepository.findById(employeeCredentials.getId()).isPresent()) {
                    Employee employee = employeeRepository.findById(employeeCredentials.getId()).get();
                    employee.setDaysOffCredits(employee.getDaysOffCredits() - daysOff.getNumberOfWorkDays());
                    employeeRepository.save(employee);
                }
            }
        }
        return "redirect:/restricted/hrhomepage";
    }
}

