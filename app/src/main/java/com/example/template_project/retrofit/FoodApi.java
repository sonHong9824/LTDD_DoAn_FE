package com.example.template_project.retrofit;

import com.example.template_project.model.Food;
import com.example.template_project.model.MovieSummary;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FoodApi {
    @GET("/food/get-all")
    Call<List<Food>> getAllFood();
}
