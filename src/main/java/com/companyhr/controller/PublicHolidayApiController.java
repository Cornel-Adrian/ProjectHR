package com.companyhr.controller;

import com.companyhr.model.PublicHoliday;
import com.companyhr.repository.PublicHolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

public class PublicHolidayApiController {

    @Autowired
    PublicHolidayRepository publicHolidayRepository;

    @GetMapping("/getBankHolidays")
    public List<PublicHoliday> getAllBankHolidays() {
        return publicHolidayRepository.findAll();
    }


    @GetMapping("/getBankHolidays/{startDate}-{endDate}")
    public List<Date> findByStartDateBetween(@PathVariable(value = "startDate") java.util.Date startDate, @PathVariable(value = "endDate") java.util.Date endDate) {
        List<Date> between = publicHolidayRepository.findByStartDateBetween(startDate, endDate);

        return between;

    }
}


