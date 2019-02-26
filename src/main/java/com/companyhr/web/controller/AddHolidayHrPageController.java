package com.companyhr.web.controller;

import com.companyhr.model.EmployeeCredentials;
import com.companyhr.model.PublicHoliday;
import com.companyhr.repository.EmployeeCredentialsRepository;
import com.companyhr.repository.PublicHolidayRepository;
import com.companyhr.service.EmployeeService;
import com.companyhr.service.SecurityService;
import com.companyhr.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

/**
 * Handles operations that allow a HR user to add a publci holiday (Bank holiday)
 * which will be used further in calculating the number of working days for each holiday request
 */
@Controller
public class AddHolidayHrPageController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    EmployeeCredentialsRepository employeeCredentialsRepository;
    @Autowired
    private PublicHolidayRepository publicHolidayRepository;
    @Autowired
    private EmployeeService employeeService;

    /**
     * @param webDataBinder used for populating form object arguments of annotated handler methods.
     *                      Used to parse date directly
     */
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * @param model used to map the holiday request get method request
     * @return the mapping for the adding a public holiday accessible only for users with HR role
     */
    @RequestMapping(value = "/addholidayhr", method = RequestMethod.GET)
    public String addholidayhr(Model model) {
        model.addAttribute("userForm", new PublicHoliday());

        return "addholidayhr";
    }

    /**
     * @param publicHoliday maps the public holiday
     * @param bindingResult allows for  Validator to be applied, and adds binding-specific analysis and model building.
     * @param model generate with GET
     * @return the mapping for the adding a public holiday accessible only for users with HR role
     */
    @RequestMapping(value = "/addholidayhr", method = RequestMethod.POST)
    public String addholidayhr(@ModelAttribute("publicholiday") PublicHoliday publicHoliday, BindingResult
            bindingResult, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        EmployeeCredentials employeeCredentials = employeeService.findByUsername(currentPrincipalName);

        if (bindingResult.hasErrors()) {
            return "addholidayhr";
        }

        publicHolidayRepository.save(publicHoliday);

        if (employeeCredentialsRepository.findByUsername(currentPrincipalName).getJobId() == 2) {
            return "/restricted/hrhomepage";

        }
        return "/restricted/userhomepage";
    }
}
