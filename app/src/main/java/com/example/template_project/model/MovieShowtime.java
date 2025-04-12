package com.example.template_project.model;

import java.io.Serializable;
import java.util.List;

public class MovieShowtime implements Serializable {
    private Movie movie;
    private List<ShowtimeType> showtimetype;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public List<ShowtimeType> getShowtimetype() {
        return showtimetype;
    }

    public void setShowtimetype(List<ShowtimeType> showtimetype) {
        this.showtimetype = showtimetype;
    }
}
