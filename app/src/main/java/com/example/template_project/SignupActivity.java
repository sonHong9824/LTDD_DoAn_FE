package com.example.template_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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

public class SignupActivity extends AppCompatActivity {
    private EditText edtName, edtEmail, edtPassword, edtConfirmPassword;
    private Button btnSignup;
    private TextView loginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        initViews();
        setupListeners();
    }

    private void initViews() {
        edtName = findViewById(R.id.editTextUsername);
        edtEmail = findViewById(R.id.editTextEmail);
        edtPassword = findViewById(R.id.editTextPassword);
        edtConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        btnSignup = findViewById(R.id.buttonSignup);
        loginText = findViewById(R.id.loginText);
    }

    private void setupListeners() {
        btnSignup.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            String confirmPassword = edtConfirmPassword.getText().toString().trim();

            if (validateInputs(name, email, password, confirmPassword)) {
                sendOtp(email, name, password);
            }
        });
        loginText.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
    }

    private boolean validateInputs(String name, String email, String password, String confirmPassword) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showToast("Vui lòng nhập đầy đủ thông tin!");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            showToast("Mật khẩu xác nhận không khớp!");
            return false;
        }
        return true;
    }

    private void sendOtp(String email, String name, String password) {
        RetrofitService retrofitService = new RetrofitService();
        AuthApi authApi = retrofitService.getRetrofit().create(AuthApi.class);

        authApi.sendOtp(new User(email)).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    showToast(response.body().getMessage());
                    String message = response.body().getMessage();
                    if ("Đã gửi OTP".equals(message)) {
                        navigateToOtpActivity(email, name, password, message);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("RetrofitError", "Lỗi kết nối: " + t.getMessage(), t);
                showToast("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    private void navigateToOtpActivity(String email, String name, String password, String message) {
        Intent intent = new Intent(SignupActivity.this, OtpActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("name", name);
        intent.putExtra("message", message);
        intent.putExtra("password", password);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
