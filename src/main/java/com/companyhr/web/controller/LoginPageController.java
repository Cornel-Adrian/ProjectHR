package com.companyhr.web.controller;

import com.companyhr.controller.EmployeeCredentialsApiController;
import com.companyhr.model.Employee;
import com.companyhr.model.EmployeeCredentials;
import com.companyhr.repository.EmployeeCredentialsRepository;
import com.companyhr.service.EmployeeService;
import com.companyhr.service.SecurityService;
import com.companyhr.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import java.util.List;
import java.util.Random;

/**
 * The Login and Registration controller.
 */
@Controller
public class LoginPageController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

	@Autowired
    EmployeeCredentialsRepository employeeCredentialsRepository;

	@Autowired
    EmployeeCredentialsApiController employeeCredentialsApiController;

	/**
	 * Init binder.
	 *
	 * @param webDataBinder the web data binder
	 */
	@InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	/**
	 * The registration method which checks for existing users and if none is found, it redirects to the init method
	 *
	 * @param model the model
	 * @return the template
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        List<EmployeeCredentials> employeeCredentialsList = employeeCredentialsRepository.findAll();

        if (employeeCredentialsList.isEmpty()) {
            EmployeeCredentials tempCredentials = new EmployeeCredentials();
            Random random = new Random();

            String username = String.valueOf(Math.abs(random.nextLong()));
            tempCredentials.setUsername(username);
            String password = String.valueOf(Math.abs(random.nextLong()));
            tempCredentials.setPassword(bCryptPasswordEncoder.encode(password));
            tempCredentials.setPasswordConfirm(bCryptPasswordEncoder.encode(password));
            tempCredentials.setEmployeeId((long) -1);
            tempCredentials.setJobId((long) 1);
            employeeCredentialsRepository.save(tempCredentials);
            securityService.autologin(username, password);
            return "/init";
        }
        model.addAttribute("userForm", new EmployeeCredentials());

        return "registration";
    }

	/**
	 * Registrationdetails GET method.
	 *
	 * @param model the model
	 * @return the template
	 */
	@RequestMapping(value = "/registrationdetails", method = RequestMethod.GET)
    public String registrationdetails(Model model) {

        model.addAttribute("userForm", new Employee());

        return "registrationdetails";
    }

	/**
	 * The Registration method which if successful redirects to the registration details method
	 *
	 * @param employeeCredentials the employee credentials model
	 * @param bindingResult       the binding result validation
	 * @param model               the model
	 * @return the template
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("employeeCredentials") EmployeeCredentials employeeCredentials, BindingResult bindingResult, Model model) {

        userValidator.validate(employeeCredentials, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        employeeCredentials.setJobId((long) 0);
        Random random = new Random();
        long employeeID = Math.abs(random.nextLong());
        while (employeeCredentialsRepository.findByEmployeeId(employeeID) != null) {
            employeeID = Math.abs(random.nextLong());
        }
        employeeCredentials.setEmployeeId(employeeID);
        employeeService.save(employeeCredentials);
        securityService.autologin(employeeCredentials.getUsername(), employeeCredentials.getPassword());
        return "redirect:/registrationdetails";
    }

	/**
	 * Registrationdetails POST method which redirects to the Redirect controller
	 *
	 * @param employee      the employee credentials model
	 * @param bindingResult the binding result validation
	 * @param model         the model
	 * @return the template
	 */
	@RequestMapping(value = "/registrationdetails", method = RequestMethod.POST)
    public String registration(@ModelAttribute("employee") Employee employee, BindingResult bindingResult, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        employee.setJobId(employeeService.findByUsername(currentPrincipalName).getJobId());
        employee.setId(employeeService.findByUsername(currentPrincipalName).getId());
        EmployeeCredentials employeeCredentials = employeeCredentialsRepository.findByUsername(currentPrincipalName);
        employeeCredentials.setDaysOffCredits(-1);
        employee.setDaysOffCredits((long) -1);
        employee.setExperience(-1);
        employee.setSalary(-1.0);

        employeeCredentialsApiController.updateEmployeeCredentials(employeeCredentials);
        employeeService.save(employee);

        return "redirect:/redirect";
    }

	/**
	 * Login GET Method.
	 *
	 * @param model the model
	 * @return the template
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("userForm", new EmployeeCredentials());
        return "login";
    }

	/**
	 * Dologin POST Method which verfies user credentials and redirects to the appropriate user management page
	 *
	 * @param employeeCredentials the employee credentials model
	 * @param bindingResult       the binding result validation
	 * @param model               the model
	 * @return the template
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public String dologin(@ModelAttribute("employeeCredentials") EmployeeCredentials employeeCredentials, BindingResult bindingResult, Model model) {

        userValidator.validatelogin(employeeCredentials, bindingResult);
        if (bindingResult.hasErrors()) {
            return "login";
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(employeeCredentials.getUsername());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, employeeCredentials.getPassword(), userDetails.getAuthorities());
        securityService.autologin(employeeCredentials.getUsername(), employeeCredentials.getPassword());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeCredentials employeeCredentials1 = employeeCredentialsRepository.findByUsername(((UserDetails) principal).getUsername());
        if ((employeeCredentials1.getJobId() == 2)) {
            return "redirect:/restricted/hrhomepage";
        }
        if (employeeCredentials1.getJobId() == 1) {
            return "redirect:/restricted/adminhomepage";
        }
        return "redirect:/restricted/userhomepage";
    }
}

