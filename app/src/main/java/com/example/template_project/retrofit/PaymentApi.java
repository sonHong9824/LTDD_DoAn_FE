package com.example.template_project.retrofit;

import com.example.template_project.response.PaymentResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PaymentApi {
    @GET("api/vnpay/create-payment")
    Call<PaymentResponse> createPayment(@Query("amount") String amount);
}
