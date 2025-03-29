package com.example.template_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.template_project.model.User;
import com.example.template_project.response.UserResponse;
import com.example.template_project.retrofit.AuthApi;
import com.example.template_project.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {
    private EditText edtOtp;
    private Button btnVerifyOtp;
    private String name, email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(com.google.android.material.R.style.Theme_MaterialComponents_DayNight_NoActionBar);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otp);

        //edtOtp = findViewById(R.id.edtOtp);
        btnVerifyOtp = findViewById(R.id.btn_verify);

        // Lấy thông tin từ Intent
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
    }

    private void registerUser(String name, String email, String password, String otp) {
        RetrofitService retrofitService = new RetrofitService();
        AuthApi authApi = retrofitService.getRetrofit().create(AuthApi.class);
        User user = new User(name, email, password);

        Call<UserResponse> call = authApi.register(user, otp);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(OtpActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OtpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(OtpActivity.this, "OTP không đúng!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(OtpActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
