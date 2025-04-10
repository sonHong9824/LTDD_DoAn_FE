package com.example.template_project.response;

import java.util.List;

public class MovieShowtimeResponse {
    private String movieId;
    private List<ShowtimeGroup> showtimes;

    public static class ShowtimeGroup {
        private String languageFormat;
        private List<String> times;

        public String getLanguageFormat() {
            return languageFormat;
        }

        public void setLanguageFormat(String languageFormat) {
            this.languageFormat = languageFormat;
        }

        public List<String> getTimes() {
            return times;
        }

        public void setTimes(List<String> times) {
            this.times = times;
        }
    }
    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public List<ShowtimeGroup> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(List<ShowtimeGroup> showtimes) {
        this.showtimes = showtimes;
    }
}
