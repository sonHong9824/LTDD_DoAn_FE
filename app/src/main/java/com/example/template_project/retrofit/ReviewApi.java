package com.example.template_project.retrofit;

import com.example.template_project.model.Review;
import com.example.template_project.model.ReviewRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ReviewApi {
    @GET("/review/get")
    Call<List<Review>> getReviews(@Query("movieId") String movieId);

    @POST("/review/create")
    Call<Review> create(@Body ReviewRequest reviewRequest);
}
