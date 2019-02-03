package com.companyhr.controller;

import com.companyhr.model.PublicHoliday;
import com.companyhr.repository.PublicHolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public class PublicHolidayApiController {

    @Autowired
    PublicHolidayRepository publicHolidayRepository;

    @GetMapping("/getBankHolidays")
    public List<PublicHoliday> getAllBankHolidays() {
        return publicHolidayRepository.findAll();
    }

    @PostMapping("/addBankHoliday")
    public PublicHoliday addBankHoliday(@Valid @RequestBody PublicHoliday publicHoliday) {
        return publicHolidayRepository.save(publicHoliday);
    }

    /*@GetMapping("/getBankHolidays/{startDate}-{endDate}")
    public List<Date> findByStartDateBetween(@PathVariable(value = "startDate") Date startDate, @PathVariable(value = "endDate") Date endDate) {
        List<Date> between = publicHolidayRepository.findByStartDateBetween(startDate, endDate);

        return between;

    }*/
}


