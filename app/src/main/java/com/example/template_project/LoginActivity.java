package com.example.template_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.template_project.SharedPreferences.PrefUser;
import com.example.template_project.model.User;
import com.example.template_project.response.UserResponse;
import com.example.template_project.retrofit.AuthApi;
import com.example.template_project.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private ImageButton btnback;
    private TextView tvForgotPassword, registerText;
    private PrefUser prefUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        prefUser = new PrefUser(this);
        initViews();
        setupListeners();
    }

    private void initViews() {
        edtEmail = findViewById(R.id.editEmail);
        edtPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.buttonLogin);
        tvForgotPassword = findViewById(R.id.forgetText);
        registerText = findViewById(R.id.registerText);
    }

    private void setupListeners() {

        btnLogin.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                showToast("Vui lòng nhập username và mật khẩu!");
            } else {
                // Kiểm tra định dạng email
                String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
                if (!email.matches(emailPattern)) {
                    showToast("Email không hợp lệ!");
                } else if (password.length() < 6) {
                    showToast("Mật khẩu phải có ít nhất 6 ký tự!");
                } else {
                    loginUser(email, password);
                }
            }
        });
        registerText.setOnClickListener(v -> startActivity(new Intent(this, SignupActivity.class)));
        tvForgotPassword.setOnClickListener(v -> startActivity(new Intent(this, ForgetPasswordActivity.class)));
    }

    private void loginUser(String email, String password) {
        RetrofitService retrofitService = new RetrofitService();
        AuthApi authApi = retrofitService.getRetrofit().create(AuthApi.class);
        User user = new User(email, password);

        authApi.login(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User authUser = response.body();
                    showToast("Chào " + authUser.getName());
                    prefUser.saveLoginDetails(authUser.getEmail(), authUser.getPassword(), authUser.getName(), authUser.getId());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    showToast("Sai tài khoản hoặc mật khẩu");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("LoginError", "Lỗi kết nối: " + t.getMessage());
                showToast("Lỗi kết nối!");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
