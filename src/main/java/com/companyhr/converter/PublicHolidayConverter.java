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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        LocalDate startDateLocale = LocalDate.parse(startDateAsString, formatter);
        LocalDate endDateLocale = LocalDate.parse(startDateAsString, formatter);
        String dateAsString = null;
        for (LocalDate date = startDateLocale; date.isBefore(endDateLocale.plusDays(1)); date = date.plusDays(1)) {
            dateAsString = new String(date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear());
            workCalendar.add(new CustomDate(dateAsString));
        }
        return workCalendar;
    }
}
