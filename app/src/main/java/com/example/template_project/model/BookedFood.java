package com.example.template_project.model;

import java.io.Serializable;

public class BookedFood implements Serializable {
    private String id;
    private int quantity;
    private Food food;

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public BookedFood(String id, int quantity, Food food) {
        this.id = id;
        this.quantity = quantity;
        this.food = food;
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

    public BookedFood(String id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public void increaseQuantity() { this.quantity++; }
    public void decreaseQuantity() { if (this.quantity > 0) this.quantity--; }
}
