package com.example.holiday.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

public class DateUtil {
    public static Date getDateNearest(List<Date> dates, Date targetDate){
        return new TreeSet<>(dates).ceiling(targetDate);
    }

    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
