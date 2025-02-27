package com.example.template_project.retrofit;


import com.example.template_project.model.Cinema;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CinemaApi {
    @GET("/cinemas")
    Call<List<Cinema>> getAllCinemas();
    @POST("/cinemas")
    Call<Cinema>save(@Body Cinema cinema);
}
