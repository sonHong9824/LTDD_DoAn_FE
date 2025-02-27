package com.example.template_project.model;

import java.util.List;

public class Movie {
    private int resourceId;
    private String title;

    private String posterUrl;
    private List<String> genre;

    public Movie(String title, String posterUrl, List<String> genre) {
        this.title = title;
        this.posterUrl = posterUrl;
        this.genre = genre;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public Movie(String title, int resourceId) {
        this.title = title;
        this.resourceId = resourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public Movie(int resourceId) {
        this.resourceId = resourceId;
    }
}
