package com.example.template_project.model;

import java.util.List;

public class Catagory {
    private String nameCatagory;
    private List<MovieSummary> movies;

    public String getNameCatagory() {
        return nameCatagory;
    }

    public void setNameCatagory(String nameCatagory) {
        this.nameCatagory = nameCatagory;
    }

    public List<MovieSummary> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieSummary> movies) {
        this.movies = movies;
    }

    public Catagory(String nameCatagory, List<MovieSummary> movies) {
        this.nameCatagory = nameCatagory;
        this.movies = movies;
    }
}
