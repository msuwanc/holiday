package com.example.holiday.controllers;

import com.example.holiday.models.Holiday;
import com.example.holiday.services.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("holidays")
public class HolidayController {
    @Autowired
    HolidayService holidayService;

    @RequestMapping(value = "next-holidays",
            method= RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Holiday> getNextHoliday(
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate targetDate,
            @RequestParam("country-code1")
                    String countryCode1,
            @RequestParam("country-code2")
                    String countryCode2
    ) {
        Holiday holiday = holidayService.getNextHoliday(targetDate, countryCode1.toUpperCase(), countryCode2.toUpperCase());

        return new ResponseEntity<>(holiday, HttpStatus.OK);
    }
}
