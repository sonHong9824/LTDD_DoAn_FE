package com.example.template_project.model;

import java.io.Serializable;

public class MovieSummary implements Serializable {
    private Movie movie;
    private double averageRating;
    private long totalReviews;

    public Movie getMovie() {
        return movie;
    }
    public double getAverageRating() {
        return averageRating;
    }
    public long getTotalReviews() {
        return totalReviews;
    }
}
