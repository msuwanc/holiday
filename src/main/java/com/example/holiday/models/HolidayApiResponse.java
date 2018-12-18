package com.example.holiday.models;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class HolidayApiResponse {
    private Integer status;
    private Map<LocalDate, List<HolidayApi>> holidays;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Map<LocalDate, List<HolidayApi>> getHolidays() {
        return holidays;
    }

    public void setHolidays(Map<LocalDate, List<HolidayApi>> holidays) {
        this.holidays = holidays;
    }
}
