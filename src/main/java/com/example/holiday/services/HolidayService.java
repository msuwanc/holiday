package com.example.holiday.services;

import com.example.holiday.models.Holiday;
import com.example.holiday.models.HolidayApiResponse;
import com.example.holiday.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HolidayService {
    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    public Holiday getNextHoliday(LocalDate targetDate, String countryCode1, String countryCode2) {
        var year = targetDate.getYear();
        var firstCountryRequest = "https://holidayapi.com/v1/holidays?country="+countryCode1+"&year="+year+"&key="+apiKey;
        var secondCountryRequest = "https://holidayapi.com/v1/holidays?country="+countryCode2+"&year="+year+"&key="+apiKey;

        var result = new Holiday();

        try {
            HolidayApiResponse firstCountryResponse = restTemplate.getForObject(firstCountryRequest, HolidayApiResponse.class);
            HolidayApiResponse secondCountryResponse = restTemplate.getForObject(secondCountryRequest, HolidayApiResponse.class);

            Set<LocalDate> firstLocalDates = firstCountryResponse.getHolidays().keySet();
            Set<LocalDate> secondLocalDates = secondCountryResponse.getHolidays().keySet();
            firstLocalDates.retainAll(secondLocalDates);

            Set<LocalDate> distinctLocalDates = firstLocalDates;

            List<Date> distinctDates = new ArrayList<>(distinctLocalDates).stream().map(DateUtil::toDate).collect(Collectors.toList());

            Date dateNearest = DateUtil.getDateNearest(distinctDates, DateUtil.toDate(targetDate));

            if(dateNearest == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no next holiday for this year");
            } else {
                LocalDate localDateNearest = DateUtil.toLocalDate(dateNearest);

                if(firstCountryResponse.getHolidays().get(localDateNearest).stream().findFirst().isPresent() &&
                        secondCountryResponse.getHolidays().get(localDateNearest).stream().findFirst().isPresent()) {
                    result.setDate(localDateNearest);
                    result.setName1(firstCountryResponse.getHolidays().get(localDateNearest).stream().findFirst().get().getName());
                    result.setName2(secondCountryResponse.getHolidays().get(localDateNearest).stream().findFirst().get().getName());
                }
            }
        } catch (HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported country codes");
            }
            if(e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "API key is not valid");
            }
            if(e.getStatusCode() == HttpStatus.PAYMENT_REQUIRED) {
                throw new ResponseStatusException(HttpStatus.PAYMENT_REQUIRED,
                        "Default API key support only historical data. " +
                                "If you want to get future data, you have to pay for paid API key(https://holidayapi.com/) and config it in application.properties file");
            }
        }

        return result;
    }
}
