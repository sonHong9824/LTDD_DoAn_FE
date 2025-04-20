package com.example.template_project.retrofit;

import com.example.template_project.model.User;
import com.example.template_project.request.ChangePasswordRequest;
import com.example.template_project.request.VerifyUserRequest;
import com.example.template_project.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthApi {
    @POST("/user/login")
    Call<User> login(@Body User user);
    @POST("/user/create")
    Call<UserResponse> register(@Body User user, @Query("otp") String otp);
    @POST("/user/send-otp")
    Call<UserResponse> sendOtp(@Body User User);
    @POST("/user/send-otp-forgot-pass")
    Call<UserResponse> sendOtpForgotPass(@Query("email") String email);
    @POST("/user/verify-forgot-password-otp")
    Call<UserResponse> verifyForgotPassword(@Query("email") String email, @Query("otp") String otp);
    @POST("/user/reset-password")
    Call<UserResponse> resetPassword(@Body VerifyUserRequest request);
    @POST("/user/change-password")
    Call<UserResponse> changePassword(@Body ChangePasswordRequest request);
    }


