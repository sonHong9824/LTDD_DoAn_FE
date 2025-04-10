package com.example.template_project.retrofit;

import com.example.template_project.model.Showtime;
import com.example.template_project.response.MovieShowtimeResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ShowtimeApi {
    @GET("/showtime/movie-date-cinema")
    Call<List<Showtime>> getShowtimes(
            @Query("movieId") String movieId,
            @Query("cinemaId") String cinemaId,
            @Query("date") String date // YYYY-MM-DD
    );

    @GET("/showtime/movie-date")
    Call<List<Showtime>> getShowtimes_group(
            @Query("movieId") String movieId,
            @Query("date") String date // YYYY-MM-DD
    );
    @GET("/showtimes")
    Call<List<MovieShowtimeResponse>> getShowtimesByCinemaAndDate(
            @Query("cinemaId") String cinemaId,
            @Query("date") String date  // format yyyy-MM-dd
    );
}
