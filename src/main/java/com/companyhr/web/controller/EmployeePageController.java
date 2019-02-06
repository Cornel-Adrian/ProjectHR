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
        int credits = (int) employeeCredentialsRepository.findByUsername(username).getDays_off_credits();
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
            return "adddayoff";
        }

        if (credits == 0) {
            return "adddayoff";
        }


        daysOff.setEmployeeId(employeeCredentialsRepository.findByUsername(username).getEmployee_id());
        daysOff.setDaysOffTypeId(Long.valueOf(1));
        daysOff.setStatus(Long.valueOf(0));
        daysOffRepository.save(daysOff);
        return "/restricted/afterlogin";
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

        Long employeeId = employeeCredentialsRepository.findByUsername(username).getEmployee_id();

        model.addAttribute("daysoff", daysOffRepository.findByemployeeId(employeeId));
        return "viewvacancies";
    }




//    @RequestMapping (value = "/viewpersonaldetails", method = RequestMethod.POST)
//    public String viewpersonaldetails(ModelMap model, @RequestParam String username, @RequestParam String name, @RequestMapping String role, @RequestMapping String employeeid, @RequestMapping String departmentid, @RequestMapping String salary){
//model.put("username", );
//
//}
//
//        return "restricted/userhomepage";
    //}


}
