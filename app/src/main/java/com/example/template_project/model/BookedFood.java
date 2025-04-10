package com.example.template_project.model;

import java.io.Serializable;

public class BookedFood implements Serializable {
    private String id;
    private int quantity;

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

    public BookedFood(String id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public void increaseQuantity() { this.quantity++; }
    public void decreaseQuantity() { if (this.quantity > 0) this.quantity--; }
}
