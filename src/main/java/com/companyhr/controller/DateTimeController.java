package com.companyhr.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
public class DateTimeController {

    public void date(@RequestParam("date")
                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {

    }

    @PostMapping("/local-date")
    public void localDate(@RequestParam("localDate")
                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
    }

    @PostMapping("/local-date-time")
    public void dateTime(@RequestParam("localDateTime")
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime) {

    }
}