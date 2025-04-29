package com.example.template_project.model;

import java.time.LocalDateTime;

public class Review {
    private String id;
    private User user;
    private String movieId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Review() {
    }

    public Review(String id, User user, String movieId, int rating, String comment, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.movieId = movieId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }
}
