package com.example.template_project.retrofit;

import com.example.template_project.model.MovieSummary;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieApi {
    @GET("/movie/now-showing")
    Call<List<MovieSummary>> getNowShowingMovies();

    @GET("/movie/coming-soon")
    Call<List<MovieSummary>> getComingSoonMovies();
}
