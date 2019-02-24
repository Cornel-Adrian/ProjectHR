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

/**
 * The Reject controller.
 */
@Controller
public class RejectController {
	/**
	 * The Employee repository.
	 */
	@Autowired
    EmployeeRepository employeeRepository;
	/**
	 * The Employee credentials repository.
	 */
	@Autowired
    EmployeeCredentialsRepository employeeCredentialsRepository;
	/**
	 * The Employee credentials api controller.
	 */
	@Autowired
    EmployeeCredentialsApiController employeeCredentialsApiController;
	/**
	 * The Days off repository.
	 */
	@Autowired
    DaysOffRepository daysOffRepository;

	/**
	 * Init binder.
	 *
	 * @param webDataBinder the web data binder
	 */
	@InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));
    }

	/**
	 * Reject method GET mapping, for rejecting days off requests.
	 *
	 * @param id    the id
	 * @param model the model
	 * @return the template
	 */
	@RequestMapping(value = "/reject/{id}", method = RequestMethod.GET)
    public String rejectDayOff(@PathVariable Long id, Model model) {

        Optional<DaysOff> daysOffs = daysOffRepository.findById(id);
        if (daysOffRepository.findById(id).isPresent()) {
            DaysOff daysOff = (daysOffRepository.findById(id)).get();
            model.addAttribute("daysOff", daysOff);
            return "reject";
        }

        return "/error";


    }


	/**
	 * RejectDayOffSend method for POST mapping for rejecting days off requests.
	 *
	 * @param daysOff       the days off
	 * @param bindingResult the binding result
	 * @param model         the model
	 * @return the template
	 */
	@RequestMapping(value = "/reject", method = RequestMethod.POST)
    public String rejectDayOffSend(@ModelAttribute("dayOff") DaysOff daysOff, BindingResult bindingResult, Model model) {

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
