package com.example.template_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.template_project.model.User;
import com.example.template_project.response.UserResponse;
import com.example.template_project.retrofit.AuthApi;
import com.example.template_project.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {
    private PinView edtOtp;
    TextView otpDescriptionText;
    private Button btnVerifyOtp;
    private String name, email, password;
    RetrofitService retrofitService;
    AuthApi authApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(com.google.android.material.R.style.Theme_MaterialComponents_DayNight_NoActionBar);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otp);

        retrofitService = new RetrofitService();
        authApi = retrofitService.getRetrofit().create(AuthApi.class);
        edtOtp = findViewById(R.id.pin_view);
        btnVerifyOtp = findViewById(R.id.btn_verify);
        otpDescriptionText = findViewById(R.id.otp_description_text);
        // Lấy thông tin từ Intent
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        String message = getIntent().getStringExtra("message");

        String otpMessage = String.format("Vui lòng nhập mã OTP gửi đến %s", email);
        otpDescriptionText.setText(otpMessage);

        btnVerifyOtp.setOnClickListener(v -> {
            String otp = edtOtp.getText().toString().trim();
            if (otp.isEmpty() || otp.length() < 6) {
                Toast.makeText(OtpActivity.this, "Vui lòng nhập đầy đủ mã OTP", Toast.LENGTH_SHORT).show();
                return;
            }
            if (message.equals("Đã gửi OTP"))
            {
                registerUser(name, email, password, otp);
            }
            if (message.equals("OTP đặt lại mật khẩu đã được gửi"))
            {
                verifyForgotOtp(email,otp);
            }
        });
    }

    private void registerUser(String name, String email, String password, String otp) {
        User user = new User(name, email, password);
        Call<UserResponse> call = authApi.register(user, otp);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(OtpActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    if(response.body().getMessage().equals("Tạo tài khoản thành công")){
                        Intent intent = new Intent(OtpActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(OtpActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void verifyForgotOtp(String email, String otp) {
        authApi.verifyForgotPassword(email, otp).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String message = response.body().getMessage();
                    Toast.makeText(OtpActivity.this, message, Toast.LENGTH_SHORT).show();
                    if (message.equals("OTP xác nhận thành công")) {
                        Intent intent = new Intent(OtpActivity.this, ResetPasswordActivity.class);
                        intent.putExtra("email", email);
                        intent.putExtra("otp", otp);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(OtpActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
