package com.example.template_project.retrofit;

import com.example.template_project.model.FeatureMovie;
import com.example.template_project.model.MovieSummary;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MovieApi {
    @GET("/movie/now-showing")
    Call<List<MovieSummary>> getNowShowingMovies();

    @GET("/movie/coming-soon")
    Call<List<MovieSummary>> getComingSoonMovies();

    @POST("/feature/increase")
    Call<FeatureMovie> increase(@Query("id") String id, @Query("score") int score);

    @GET("/movie/feature")
    Call<List<MovieSummary>> getFeatureMovies();

}
