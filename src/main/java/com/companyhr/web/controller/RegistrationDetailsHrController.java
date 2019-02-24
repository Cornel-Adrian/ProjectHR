package com.companyhr.web.controller;

import com.companyhr.controller.EmployeeCredentialsApiController;
import com.companyhr.model.Employee;
import com.companyhr.model.EmployeeCredentials;
import com.companyhr.repository.EmployeeCredentialsRepository;
import com.companyhr.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

/**
 * The Registration details hr controller.
 */
@Controller

public class RegistrationDetailsHrController {

	@Autowired
    EmployeeRepository employeeRepository;

	@Autowired
    EmployeeCredentialsRepository employeeCredentialsRepository;

	@Autowired
    EmployeeCredentialsApiController employeeCredentialsApiController;

	/**
	 * ReturnEmployee method that returns specific employee based on id
	 *
	 * @param id    the employee id
	 * @param model the model
	 * @return the template
	 */
	@RequestMapping(value = "/registrationdetailshr/{id}", method = RequestMethod.GET)
    public String returnEmployee(@PathVariable Long id, Model model) {
        Optional<Employee> employee = null;
        if (employeeRepository.findById(id).isPresent()) {
            employee = employeeRepository.findById(id);
        }

        model.addAttribute("employee", employee);
        return "registrationdetailshr";
    }


	/**
	 * Updateuser method that updaate users based on input.
	 *
	 * @param employee      the employee object
	 * @param bindingResult the binding result validation
	 * @param model         the model
	 * @return the string
	 */
	@RequestMapping(value = "/registrationdetailshr", method = RequestMethod.POST)
    public String updateuser(@ModelAttribute("employee") Employee employee, BindingResult bindingResult, Model model) {

        employeeRepository.save(employee);
        if (employeeCredentialsRepository.findById(employee.getId()).isPresent()) {
            EmployeeCredentials employeeCredentials = (employeeCredentialsRepository.findById(employee.getId()).get());
            employeeCredentials.setDaysOffCredits(employee.getDaysOffCredits());
            employeeCredentials.setJobId(employee.getJobId());
            employeeCredentialsApiController.updateEmployeeCredentials(employeeCredentials);
        }
        return "redirect:/restricted/hrhomepage";
    }
}