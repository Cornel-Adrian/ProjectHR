package com.companyhr.converter;


import com.companyhr.model.CustomDate;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PublicHolidayConverter {



    public List<CustomDate> getWorkCalendar(String startDateAsString, String endDateAsString) {
        List<CustomDate> workCalendar = new ArrayList<>();
//      String[] startDate = startDateAsString.split("/");
//      String[] endDate = endDateAsString.split("/");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

        LocalDate startDateLocale = LocalDate.parse(startDateAsString, formatter);
        LocalDate endDateLocale = LocalDate.parse(startDateAsString, formatter);

        //LocalDate startDateLocale = LocalDate.of(Integer.parseInt(startDate[2]), Integer.parseInt(startDate[1]), Integer.parseInt(startDate[0]));
        //LocalDate endDateLocale = LocalDate.of(Integer.parseInt(endDate[2]), Integer.parseInt(endDate[1]), Integer.parseInt(endDate[0]));
        String dateAsString = null;
        for (LocalDate date = startDateLocale; date.isBefore(endDateLocale.plusDays(1)); date = date.plusDays(1)) {
            dateAsString = new String(date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear());
            workCalendar.add(new CustomDate(dateAsString));
        }
        return workCalendar;
    }
}
