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

import com.example.template_project.request.VerifyUserRequest;
import com.example.template_project.response.UserResponse;
import com.example.template_project.retrofit.AuthApi;
import com.example.template_project.retrofit.RetrofitService;

import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class ResetPasswordActivity extends AppCompatActivity {
    private EditText edtNewPassword, edtConfirmNewPassword;
    private Button btnResetPassword;

    private String email, otp; // Nhận từ màn hình trước đó

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password);

        edtNewPassword = findViewById(R.id.editTextNewPassword);
        edtConfirmNewPassword = findViewById(R.id.editTextConfirmNewPassword);
        btnResetPassword = findViewById(R.id.buttonResetPassword);

        email = getIntent().getStringExtra("email");
        otp = getIntent().getStringExtra("otp");


        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = edtNewPassword.getText().toString().trim();
                String confirmNewPassword = edtConfirmNewPassword.getText().toString().trim();

                // Kiểm tra đầu vào
                if (newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
                    showToast("Vui lòng nhập đầy đủ thông tin!");
                    return;
                }
                // Kiểm tra độ dài mật khẩu
                if (newPassword.length() < 6) {
                    showToast("Mật khẩu phải có ít nhất 6 ký tự!");
                    return;
                }
                if (!newPassword.equals(confirmNewPassword)) {
                    showToast("Mật khẩu xác nhận không khớp!");
                    return;
                }
                resetPassword(email, otp, newPassword);
            }
        });
    }
    private void resetPassword(String email, String otp, String newPassword) {
        RetrofitService retrofitService = new RetrofitService();
        AuthApi authApi = retrofitService.getRetrofit().create(AuthApi.class);

        VerifyUserRequest request = new VerifyUserRequest(email, newPassword, otp);

        authApi.resetPassword(request).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String message = response.body().getMessage();
                    showToast(message);
                    if (message.equals("Mật khẩu đã được đặt lại thành công")) {
                        startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                        finish();
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