package com.example.template_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_password);

        edtEmail = findViewById(R.id.editEmailForgot);
        btnSendOtp = findViewById(R.id.buttonSendOTP);

        btnSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOtp();
            }
        });
    }
    private void sendOtp() {
        // Lấy email từ EditText
        String email = edtEmail.getText().toString().trim();

        // Kiểm tra nếu email rỗng
        if (email.isEmpty()) {
            showToast("Vui lòng nhập email!");
            return;
        }

        // Khởi tạo Retrofit
        RetrofitService retrofitService = new RetrofitService();
        AuthApi authApi = retrofitService.getRetrofit().create(AuthApi.class);

        // Gọi API gửi OTP
        authApi.sendOtpForgotPass(email).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String message = response.body().getMessage();
                    showToast(message);

                    if ("OTP đặt lại mật khẩu đã được gửi".equals(message)) {
                        // Chuyển sang màn hình nhập OTP
                        Intent intent = new Intent(ForgetPasswordActivity.this, OtpActivity.class);
                        intent.putExtra("email", email);
                        intent.putExtra("message", message);
                        startActivity(intent);
                    }
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                showToast("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}