package com.example.template_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.template_project.response.UserResponse;
import com.example.template_project.retrofit.AuthApi;
import com.example.template_project.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity {
    private EditText edtEmail;
    private Button btnSendOtp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(com.google.android.material.R.style.Theme_AppCompat_Light_NoActionBar);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_password);

        edtEmail = findViewById(R.id.editEmailForgot);
        btnSendOtp = findViewById(R.id.buttonSendOTP);

        btnSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendOtp();
            }
        });
    }
//    private void sendOtp() {
//        RetrofitService retrofitService = new RetrofitService();
//        AuthApi authApi = retrofitService.getRetrofit().create(AuthApi.class);
//
//        String email = edtEmail.getText().toString().trim();
//        if (email.isEmpty()) {
//            Toast.makeText(this, "Vui lòng nhập email!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        retrofit2.Call<UserResponse> call = authApi.register(user, otp);
//        call.enqueue(new Callback<UserResponse>() {
//            @Override
//            public void onResponse(retrofit2.Call<UserResponse> call, Response<UserResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    Toast.makeText(OtpActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(OtpActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Toast.makeText(OtpActivity.this, "OTP không đúng!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserResponse> call, Throwable t) {
//                Toast.makeText(OtpActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
//    }
}