package com.example.template_project.model;

import java.io.Serializable;
import java.util.List;

public class ShowtimeType implements Serializable {
    private String languageFormat;
    private List<Showtime> showtimes;

    public String getLanguageFormat() {
        return languageFormat;
    }

    public void setLanguageFormat(String languageFormat) {
        this.languageFormat = languageFormat;
    }

    public List<Showtime> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(List<Showtime> showtimes) {
        this.showtimes = showtimes;
    }
}
