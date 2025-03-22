package com.example.template_project.model;

import java.time.LocalDateTime;

public class Showtime {
    private String id;
    private Movie movie;
    private Cinema cinema;
    private String room;
    private LocalDateTime showtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public LocalDateTime getShowtime() {
        return showtime;
    }

    public void setShowtime(LocalDateTime showtime) {
        this.showtime = showtime;
    }

    public Showtime() {
    }

    public Showtime(String id, Movie movie, Cinema cinema, String room, LocalDateTime showtime) {
        this.id = id;
        this.movie = movie;
        this.cinema = cinema;
        this.room = room;
        this.showtime = showtime;
    }
}
