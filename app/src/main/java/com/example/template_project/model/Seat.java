package com.example.template_project.model;

public class Seat {
    private String seatName;
    private boolean isSelected;
    private boolean isPicked;

    public boolean isPicked() {
        return isPicked;
    }

    public void setPicked(boolean picked) {
        isPicked = picked;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public Seat(String seatName, boolean isPicked) {
        this.seatName = seatName;
        this.isSelected = false;
        this.isPicked = isPicked;
    }

    public Seat(String seatName) {
        this.seatName = seatName;
        this.isSelected = false;
        this.isPicked = false;
    }

    public String getSeatName() {
        return seatName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

