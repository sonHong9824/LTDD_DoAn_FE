package com.example.template_project.model;

import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable {
    private String id;
    private String title;
    private String description;
    private int duration;
    private String releaseDate;  // Retrofit xử lý kiểu String thay vì LocalDate
    private String posterUrl;
    private String trailerUrl;
    private String scope;
    private String backdropUrl;

    public String getBackdropUrl() {
        return backdropUrl;
    }

    public void setBackdropUrl(String backdropUrl) {
        this.backdropUrl = backdropUrl;
    }

    public Movie(String id, String title, String description, int duration, String releaseDate, String posterUrl, String trailerUrl, String scope, String backdropUrl, String language, String status, List<Genre> genres) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.posterUrl = posterUrl;
        this.trailerUrl = trailerUrl;
        this.scope = scope;
        this.backdropUrl = backdropUrl;
        this.language = language;
        this.status = status;
        this.genres = genres;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    private String language;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Genre> getGenres() {
        return genres;
    }
    private String status;
    private List<Genre> genres;

    public Movie(String id, String title, String description, int duration, String releaseDate, String posterUrl, String trailerUrl, String status, List<Genre> genres,String scope,String language) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.posterUrl = posterUrl;
        this.trailerUrl = trailerUrl;
        this.status = status;
        this.genres = genres;
        this.scope = scope;
        this.language = language;
    }
}
