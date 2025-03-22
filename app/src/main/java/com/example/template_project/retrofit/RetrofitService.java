package com.example.template_project.retrofit;

import com.example.template_project.adapter.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService
{
    private Retrofit retrofit;

    public RetrofitService()
    {
        initializeRetrofit();
    }
    private void initializeRetrofit()
    {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()) // Thêm adapter
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.128.1:8080/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
    public Retrofit getRetrofit()
    {
        return retrofit;
    }
}
