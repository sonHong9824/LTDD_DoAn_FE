package com.example.template_project.model;

public class Seat {
    private String seatName;
    private boolean isSelected;

    public Seat(String seatName) {
        this.seatName = seatName;
        this.isSelected = false;
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

