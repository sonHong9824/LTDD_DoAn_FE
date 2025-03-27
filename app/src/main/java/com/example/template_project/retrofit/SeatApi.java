package com.example.template_project.retrofit;

import com.example.template_project.model.BookedSeat;
import com.example.template_project.model.SeatRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SeatApi {
    @GET("/seat/getByShowtime")
    Call<List<BookedSeat>> getByShowtime(@Query("showtimeId") String showtimeId);

    @POST("/seat/create")
    Call<BookedSeat> create(@Body SeatRequest seatRequest);
}
