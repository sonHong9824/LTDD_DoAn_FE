package com.example.template_project.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class BookedSeat implements Serializable {
    private String id;

    public BookedSeat() {
    }

    public BookedSeat(BigDecimal price, String seat, Showtime showtime, String id) {
        this.price = price;
        this.seat = seat;
        this.showtime = showtime;
        this.id = id;
    }

    private Showtime showtime;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String seat;
    private BigDecimal price;
}
