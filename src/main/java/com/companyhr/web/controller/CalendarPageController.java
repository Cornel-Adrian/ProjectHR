package com.companyhr.web.controller;

import com.companyhr.model.CustomDate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CalendarPageController {

    @RequestMapping(value = "/showBankHolidays", method = RequestMethod.GET)
    public List<CustomDate> getBankHolidays(List<CustomDate> workCalendar) {
        List<CustomDate> bankHolidays = new ArrayList<>();
        for (int i = 0; i < workCalendar.size(); i++) {
            if (workCalendar.get(i).getBankHoliday() == true) {
                bankHolidays.add(workCalendar.get(i));
            }
        }
        return bankHolidays;
    }

    @RequestMapping(value = "/setBankHolidays", method = RequestMethod.POST)

    public List<CustomDate> setBankHolidays(List<CustomDate> workCalendar, List<String> bankHolidays) {
        for (String bankHoliday : bankHolidays) {
            CustomDate customDate = new CustomDate(bankHoliday);
            for (int i = 0; i < workCalendar.size(); i++) {
                if (customDate.equals(workCalendar.get(i))) {
                    workCalendar.get(i).setBankHoliday(true);
                    break;
                }
            }
        }
        return workCalendar;
    }

    @RequestMapping(value = "/addBankHoliday", method = RequestMethod.POST)

    public List<CustomDate> addBankHoliday(List<CustomDate> workCalendar, String dateAsString) {
        CustomDate customDate = new CustomDate(dateAsString);
        for (int i = 0; i < workCalendar.size(); i++) {
            if (customDate.equals(workCalendar.get(i))) {
                workCalendar.get(i).setBankHoliday(true);
                break;
            }
        }
        return workCalendar;
    }

    @RequestMapping(value = "/getWorkCalendar", method = RequestMethod.GET)

    public List<CustomDate> getWorkCalendar(String startDateAsString, String endDateAsString) {
        List<CustomDate> workCalendar = new ArrayList<>();
        String[] startDate = startDateAsString.split("/");
        String[] endDate = endDateAsString.split("/");
        LocalDate startDateLocale = LocalDate.of(Integer.parseInt(startDate[2]), Integer.parseInt(startDate[1]), Integer.parseInt(startDate[0]));
        LocalDate endDateLocale = LocalDate.of(Integer.parseInt(endDate[2]), Integer.parseInt(endDate[1]), Integer.parseInt(endDate[0]));
        String dateAsString = null;
        for (LocalDate date = startDateLocale; date.isBefore(endDateLocale.plusDays(1)); date = date.plusDays(1)) {
            dateAsString = new String(date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear());
            workCalendar.add(new CustomDate(dateAsString));
        }
        return workCalendar;
    }
}

