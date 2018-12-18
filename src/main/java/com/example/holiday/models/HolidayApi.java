package com.example.holiday.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class HolidayApi {
    private String name;
    private LocalDate date;
    private LocalDate observed;

    @JsonProperty("public")
    private Boolean publicBoolean;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getObserved() {
        return observed;
    }

    public void setObserved(LocalDate observed) {
        this.observed = observed;
    }

    public Boolean getPublicBoolean() {
        return publicBoolean;
    }

    public void setPublicBoolean(Boolean publicBoolean) {
        this.publicBoolean = publicBoolean;
    }
}
