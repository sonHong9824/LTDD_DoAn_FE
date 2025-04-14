package com.example.template_project.model;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class Ticket implements Serializable {
    private Long id;
    private List<BookedSeat> bookedSeat;
    private List<BookedFood> bookedFoods;
    private int price;
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BookedSeat> getBookedSeat() {
        return bookedSeat;
    }

    public void setBookedSeat(List<BookedSeat> bookedSeat) {
        this.bookedSeat = bookedSeat;
    }

    public List<BookedFood> getBookedFoods() {
        return bookedFoods;
    }

    public void setBookedFoods(List<BookedFood> bookedFoods) {
        this.bookedFoods = bookedFoods;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public Ticket() {
    }

    public Ticket(Long id, List<BookedSeat> bookedSeat, List<BookedFood> bookedFoods, int price, User user, Showtime showtime) {
        this.id = id;
        this.bookedSeat = bookedSeat;
        this.bookedFoods = bookedFoods;
        this.price = price;
        this.user = user;
        this.showtime = showtime;
    }

    private Showtime showtime;
    public String getSeatListString() {
        return bookedSeat.stream()
                .map(bs -> bs.getSeat())
                .collect(Collectors.joining(", "));
    }


}
