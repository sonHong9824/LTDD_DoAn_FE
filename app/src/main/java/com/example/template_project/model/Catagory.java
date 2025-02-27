package com.example.template_project.model;

import java.util.List;

public class Catagory {
    private String nameCatagory;
    private List<Movie> movies;

    public Catagory(String nameCatagory, List<Movie> movies) {
        this.nameCatagory = nameCatagory;
        this.movies = movies;
    }

    public String getNameCatagory() {
        return nameCatagory;
    }

    public void setNameCatagory(String nameCatagory) {
        this.nameCatagory = nameCatagory;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
