package com.companyhr.web.controller;

import com.companyhr.converter.PublicHolidayConverter;
import com.companyhr.model.CustomDate;
import com.companyhr.model.DaysOff;
import com.companyhr.model.EmployeeCredentials;
import com.companyhr.model.PublicHoliday;
import com.companyhr.repository.DaysOffRepository;
import com.companyhr.repository.EmployeeCredentialsRepository;
import com.companyhr.repository.PublicHolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Controller
public class EmployeePageController {


    @Autowired
    DaysOffRepository daysOffRepository;

    @Autowired
    PublicHolidayRepository publicHolidayRepository;

    @Autowired
    EmployeeCredentialsRepository employeeCredentialsRepository;


    /**
     * @param webDataBinder used for populating form object arguments of annotated handler methods.
     *                      Used to parse date directly
     */
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     *  * Method that performs operations for holiday requests- GET method
     * @param model the model
     * @return mapping add holiday request if success
     */
    @RequestMapping(value = "/adddayoff", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new DaysOff());

        return "adddayoff";
    }

    /**
     * Method that performs operations for holiday requests- POST method
     * @param daysOff the holiday request
     * @param bindingResult allows for  Validator to be applied, and adds binding-specific analysis and model building.
     * @param model the model
     * @return mapping for user homepage if success
     */
    @RequestMapping(value = "/adddayoff", method = RequestMethod.POST)
    public String registration(@ModelAttribute("daysOff") DaysOff daysOff, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "adddayoff";
        }


        String username;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        PublicHolidayConverter publicHolidayConverter = new PublicHolidayConverter();
        int credits = (int) employeeCredentialsRepository.findByUsername(username).getDaysOffCredits();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String startDate = formatter.format(LocalDate.parse(simpleDateFormat.format(daysOff.getStartDate()), formatter));
        String endDate = formatter.format(LocalDate.parse(simpleDateFormat.format(daysOff.getEndDate()), formatter));
        List<CustomDate> between = publicHolidayConverter.getWorkCalendar(startDate, endDate);
        LocalDate startDateLocale = LocalDate.parse(simpleDateFormat.format(daysOff.getStartDate()), formatter);
        LocalDate endDateLocale = LocalDate.parse(simpleDateFormat.format(daysOff.getEndDate()), formatter);
        int days = (int) ChronoUnit.DAYS.between(startDateLocale, endDateLocale);
        int bankHoliday = 0;
        List<java.util.Date> serviceList = new ArrayList<>();
        List<PublicHoliday> allPublicHoliday = publicHolidayRepository.findAll();
        for (int i = 0; i < allPublicHoliday.size(); i++) {
            serviceList.addAll(publicHolidayRepository.findByStartDateBetween(allPublicHoliday.get(i).getStartDate(), allPublicHoliday.get(i).getEndDate()));
            bankHoliday += serviceList.size();

        }
        int weekend = 0;

        for (int i = 0; i < between.size(); i++) {
            if (between.get(i).getBankHoliday()) {
                weekend++;
            }
        }
        if (credits <= 0) {
            return "adddayoff";
        }

        if (credits - (days - bankHoliday - weekend) <= 0) {

            return "adddayoff";
        }

        Long total = Long.valueOf(credits - ((days - bankHoliday) - weekend));
        EmployeeCredentials temp = employeeCredentialsRepository.findByUsername(username);
        employeeCredentialsRepository.save(temp);
        daysOff.setEmployeeId(employeeCredentialsRepository.findByUsername(username).getEmployeeId());
        daysOff.setDaysOffTypeId(Long.valueOf(1));
        daysOff.setStatus(Long.valueOf(0));
        daysOff.setNumberOfWorkDays((long) (days - bankHoliday - weekend));
        daysOffRepository.save(daysOff);
        if (employeeCredentialsRepository.findByUsername(username).getJobId() == 2) {
            return "/restricted/hrhomepage";

        }
        return "/restricted/userhomepage";
    }

    /**
     * Redirect towards the userhomepage- GET method
     * @param model the model
     * @return mapping for user home page
     */
    @RequestMapping(value = "/userhomepage", method = RequestMethod.GET)
    public String userhomepage(Model model) {
        model.addAttribute("userForm", new EmployeeCredentials());

        return "restricted/userhomepage";
    }

    /**
     * * Redirect towards the userhomepage- POST method
     *
     * @param employeeCredentials the model for employeecredentials
     * @param bindingResult allows for  Validator to be applied, and adds binding-specific analysis and model building.
     * @param model the model
     * @return mapping for user home page
     */
    @RequestMapping(value = "/userhomepage", method = RequestMethod.POST)
    public String userhomepage(@ModelAttribute("employeeCredentials") EmployeeCredentials employeeCredentials, BindingResult bindingResult, Model model) {


        return "restricted/userhomepage";
    }


    /**
     * Allows a user to view his personal holiday history
     * @param model the model
     * @return mapping for viewing his history page
     */
    @RequestMapping("/viewvacancies")
    public String countsList(Model model) {

        String username;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Long employeeId = employeeCredentialsRepository.findByUsername(username).getEmployeeId();

        model.addAttribute("daysoff", daysOffRepository.findByemployeeId(employeeId));
        return "viewvacancies";
    }

    /**
     * Allows a HR user to view the holiday history for all users - approved holiday requests
     * @param model the model
     * @return mapping for the viewallvacanies page
     */
    @RequestMapping("/viewallvacancies")
    public String viewAllVacancies(Model model) {

        List<DaysOff> listOfdays = daysOffRepository.findAll();
        List<DaysOff> listOfApprovedDaysOff = new ArrayList<>();
        for (DaysOff daysOff : listOfdays) {
            if (daysOff.getStatus() == 1) {
                listOfApprovedDaysOff.add(daysOff);
            }
        }
        model.addAttribute("daysoff", listOfdays);
        return "viewallvacancies";
    }

    /**
     * Allows a user to view his/her credits
     * @param model the model
     * @return mapping for the viewcredits page
     */
    @RequestMapping("/restricted/viewcredits")
    public String listcredits(Model model) {

        String username;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        List<EmployeeCredentials> employeeId = Collections.singletonList(employeeCredentialsRepository.findByUsername(username));

        model.addAttribute("daysoff", employeeId);
        return "/restricted/viewcredits";
    }
}
