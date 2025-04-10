package com.example.template_project.retrofit;

import com.example.template_project.model.BookedSeat;
import com.example.template_project.model.SeatRequest;
import com.example.template_project.model.Ticket;
import com.example.template_project.model.TicketRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TicketApi {
    @POST("/ticket/create")
    Call<Ticket> create(@Body TicketRequest ticketRequest);
}
