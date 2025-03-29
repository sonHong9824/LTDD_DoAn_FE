package com.example.template_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.template_project.model.User;
import com.example.template_project.response.MessageResponse;
import com.example.template_project.response.UserResponse;
import com.example.template_project.retrofit.AuthApi;
import com.example.template_project.retrofit.RetrofitService;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private EditText edtName, edtEmail,edtConfirmPassword, edtPassword;
    private Button btnSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        edtName = findViewById(R.id.editTextUsername);
        edtEmail = findViewById(R.id.editTextEmail);
        edtPassword = findViewById(R.id.editTextPassword);
        edtConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        btnSignup = findViewById(R.id.buttonSignup);

        btnSignup.setOnClickListener(view -> {
            String name = edtName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            String confirmPassword = edtConfirmPassword.getText().toString().trim();

            // Kiểm tra nhập đầy đủ thông tin
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(SignupActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra mật khẩu có khớp không
            if (!password.equals(confirmPassword)) {
                Toast.makeText(SignupActivity.this, "Mật khẩu xác nhận không khớp!", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setName(name);

            sendOtp(user);
        });
    }
    private void sendOtp(User user) {
        RetrofitService retrofitService = new RetrofitService();
        AuthApi userApi = retrofitService.getRetrofit().create(AuthApi.class);
        userApi.sendOtp(user)
                .enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        String mess = response.body().getMessage();
                        Toast.makeText(SignupActivity.this,mess,Toast.LENGTH_SHORT).show();

                        if(mess.equals("Đã gửi OTP")){
                            Intent intent = new Intent(SignupActivity.this, OtpActivity.class);
                            intent.putExtra("user", user); // Truyền User
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        String errorMessage;

                        if (t instanceof IOException) {
                            errorMessage = "Lỗi kết nối mạng! Vui lòng kiểm tra Internet.";
                        } else {
                            errorMessage = "Lỗi không xác định: " + t.getMessage();
                        }

                        Toast.makeText(SignupActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        Logger.getLogger(SignupActivity.class.getName()).log(Level.SEVERE, "Lỗi xảy ra", t);

                    }
                });
    }

}