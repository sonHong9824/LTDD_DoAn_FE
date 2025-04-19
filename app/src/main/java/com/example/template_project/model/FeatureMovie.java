package com.example.template_project.model;

public class FeatureMovie {
    private String id;
    private int score;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public FeatureMovie() {
    }

    public FeatureMovie(String id, int score) {
        this.id = id;
        this.score = score;
    }
}
