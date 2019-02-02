package com.companyhr.web.controller;

import com.companyhr.converter.PublicHolidayConverter;
import com.companyhr.model.CustomDate;
import com.companyhr.model.DaysOff;
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


    @RequestMapping(value = "/addDayOff", method = RequestMethod.POST)
    public String registration(@ModelAttribute("daysOff") DaysOff daysOff, BindingResult bindingResult, Model model) {

        String username;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        PublicHolidayConverter publicHolidayConverter = new PublicHolidayConverter();
        int credits = (int) employeeCredentialsRepository.findByUsername(username).getDaysOffCredits();


        String startDate = new String(Integer.toString(daysOff.getStart_date().getDay()) + "/" +
                Integer.toString(daysOff.getStart_date().getMonth()) + Integer.toString(daysOff.getStart_date().getYear()));
        String endDate = new String(Integer.toString(daysOff.getEndDate().getDay()) + "/" +
                Integer.toString(daysOff.getEndDate().getMonth()) + Integer.toString(daysOff.getEndDate().getYear()));

        List<CustomDate> between = publicHolidayConverter.getWorkCalendar( startDate, endDate);


        LocalDate startDateLocale = LocalDate.of(daysOff.getStart_date().getDay(), daysOff.getStart_date().getMonth(), daysOff.getStart_date().getYear());
        LocalDate endDateLocale = LocalDate.of(daysOff.getEndDate().getDay(), daysOff.getEndDate().getMonth(), daysOff.getEndDate().getYear());


        int days = (int) ChronoUnit.DAYS.between(startDateLocale, endDateLocale);

        int bankHoliday = 0;
        List<java.util.Date> serviceList = new ArrayList<>();
        List<PublicHoliday> allPublicHoliday = publicHolidayRepository.findAll();
        for (int i = 0; i < allPublicHoliday.size(); i++) {
            serviceList.addAll( publicHolidayRepository.findByStartDateBetween(allPublicHoliday.get(i).getStartDate(), allPublicHoliday.get(i).getEndDate()));
            bankHoliday += serviceList.size();


        }

        int weekend = 0;

        for (int i = 0; i < between.size(); i++) {
            if (between.get(i).getBankHoliday()) {
                weekend++;
            }
        }


        if (credits - (days - bankHoliday - weekend) <= 0) {
            return "addDaysOff";
        }

        if (credits == 0) {
            return "addDayOff";
        }


        if (bindingResult.hasErrors()) {
            return "addDayOff";
        }


        daysOff.setEmployeeId(employeeCredentialsRepository.findByUsername(username).getEmployee_id());
        daysOff.setDaysOffTypeId(Long.valueOf(1));
        daysOffRepository.save(daysOff);
        return "/restricted/afterlogin";
    }


}
