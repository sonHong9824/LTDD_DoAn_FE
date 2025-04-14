package com.example.template_project.retrofit;

import com.example.template_project.model.BookedSeat;
import com.example.template_project.model.SeatRequest;
import com.example.template_project.model.Ticket;
import com.example.template_project.model.TicketRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TicketApi {
    @POST("/ticket/create")
    Call<Ticket> create(@Body TicketRequest ticketRequest);
    @GET("/ticket/userId")
    Call<List<Ticket>> findbyuser(@Query("userId") String userId);
}
