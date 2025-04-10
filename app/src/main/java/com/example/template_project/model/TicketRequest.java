package com.example.template_project.model;

import java.util.List;

public class TicketRequest {
    private String userId;
    private String showtimeId;
    private List<String> seats;
    private List<BookedFoodRequest> bookedFoods;
    private int price;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(String showtimeId) {
        this.showtimeId = showtimeId;
    }

    public List<String> getSeats() {
        return seats;
    }

    public void setSeats(List<String> seats) {
        this.seats = seats;
    }

    public List<BookedFoodRequest> getBookedFoods() {
        return bookedFoods;
    }

    public void setBookedFoods(List<BookedFoodRequest> bookedFoods) {
        this.bookedFoods = bookedFoods;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public static class BookedFoodRequest {
        private String id;
        private int quantity;

        public BookedFoodRequest(String id, int quantity) {
            this.id = id;
            this.quantity = quantity;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
