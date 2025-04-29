package com.example.template_project.model;

public class ReviewRequest {
    private String movieId;
    private String userId;
    private int rating;
    private String comment;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public ReviewRequest() {
    }

    public ReviewRequest(String movieId, String userId, int rating, String comment) {
        this.movieId = movieId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
    }
}
