package com.example.template_project.model;

public class Movie {
    private int resourceId;
    private String title;

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
