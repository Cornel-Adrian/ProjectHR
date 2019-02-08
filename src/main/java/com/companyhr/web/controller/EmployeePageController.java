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


    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(value = "/adddayoff", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new DaysOff());

        return "adddayoff";
    }

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


        if (credits - (days - bankHoliday - weekend) <= 0) {

            return "/restricted/afterlogin";
        }

        if (credits == 0) {
            return "adddayoff";
        }

        Long total = Long.valueOf(credits - ((days - bankHoliday) - weekend));
        EmployeeCredentials temp = employeeCredentialsRepository.findByUsername(username);
        temp.setDaysOffCredits(total);
        employeeCredentialsRepository.save(temp);


        daysOff.setEmployeeId(employeeCredentialsRepository.findByUsername(username).getEmployeeId());
        daysOff.setDaysOffTypeId(Long.valueOf(1));
        daysOff.setStatus(Long.valueOf(0));
        daysOffRepository.save(daysOff);
        if (employeeCredentialsRepository.findByUsername(username).getJobId() == 2) {
            return "/restricted/hrhomepage";

        }
        return "/restricted/userhomepage";
    }

    @RequestMapping(value = "/userhomepage", method = RequestMethod.GET)
    public String userhomepage(Model model) {
        model.addAttribute("userForm", new EmployeeCredentials());

        return "restricted/userhomepage";
    }

    @RequestMapping(value = "/userhomepage", method = RequestMethod.POST)
    public String userhomepage(@ModelAttribute("employeeCredentials") EmployeeCredentials employeeCredentials, BindingResult bindingResult, Model model) {


        return "restricted/userhomepage";
    }


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

    @RequestMapping("/viewallvacancies")
    public String viewAllVacancies(Model model) {

        String username;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        List<DaysOff> listOfdays=daysOffRepository.findAll();

        model.addAttribute("daysoff", listOfdays);
       for(DaysOff days: listOfdays){
           System.out.println(days.getReasonLeave());
        }
        return "viewvacancies";
    }

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
/*
* private String username;
    private String name;
    private String role;
    private String employeeid;
    private String deparmentid;
    private String salary;

* */

//    @RequestMapping (value = "/viewpersonaldetails", method = RequestMethod.POST)
//    public String viewpersonaldetails(ModelMap model, @RequestParam String username, @RequestParam String name, @RequestMapping String role, @RequestMapping String employeeid, @RequestMapping String departmentid, @RequestMapping String salary){
//model.put("username", );
//
//}
//
//        return "restricted/userhomepage";
    //}


}
