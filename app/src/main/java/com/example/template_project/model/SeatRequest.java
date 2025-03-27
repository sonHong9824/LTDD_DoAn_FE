package com.example.template_project.model;

import java.math.BigDecimal;

public class SeatRequest {
    private String showtime_id;
    private String seat;
    private BigDecimal price;

    public String getShowtime_id() {
        return showtime_id;
    }

    public void setShowtime_id(String showtime_id) {
        this.showtime_id = showtime_id;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public SeatRequest() {
    }

    public SeatRequest(String showtime_id, String seat, BigDecimal price) {
        this.showtime_id = showtime_id;
        this.seat = seat;
        this.price = price;
    }
}
