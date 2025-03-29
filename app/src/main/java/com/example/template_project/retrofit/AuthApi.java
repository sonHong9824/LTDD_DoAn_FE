package com.example.template_project.retrofit;

import com.example.template_project.model.User;
import com.example.template_project.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthApi {
    @POST("/user/login")
    Call<UserResponse> login(@Body User user);
    @POST("/user/create")
    Call<UserResponse> register(@Body User user, @Query("otp") String otp);
    @POST("/user/send-otp")
    Call<UserResponse> sendOtp(@Body User User);
    @POST("/user/send-otp-forgot-pass")
    Call<UserResponse> sendOtpForgotPass(@Query("email") String email);
}
