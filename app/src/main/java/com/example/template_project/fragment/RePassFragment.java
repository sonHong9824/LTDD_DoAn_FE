package com.example.template_project.fragment;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.template_project.R;
import com.example.template_project.SharedPreferences.PrefUser;
import com.example.template_project.request.ChangePasswordRequest;
import com.example.template_project.response.UserResponse;
import com.example.template_project.retrofit.AuthApi;
import com.example.template_project.retrofit.MovieApi;
import com.example.template_project.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RePassFragment extends Fragment {
    EditText txtCurrentPass, txtNewPass, txtConfirmPass;
    private PrefUser prefUser;
    Button btnConfirm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repass, container, false);
        prefUser = new PrefUser(requireContext());
        txtCurrentPass = view.findViewById(R.id.editTextCurrentPassword);
        txtNewPass = view.findViewById(R.id.editTextNewPassword);
        txtConfirmPass = view.findViewById(R.id.editTextConfirmNewPassword);
        btnConfirm = view.findViewById(R.id.buttonChangePassword);

        btnConfirm.setOnClickListener(v -> {
            changePassword();
        });

        return view;
    }
    private void changePassword() {
        String currentPass = txtCurrentPass.getText().toString().trim();
        String newPass = txtNewPass.getText().toString().trim();
        String confirmPass = txtConfirmPass.getText().toString().trim();
        String userId = prefUser.getId();

        if (currentPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPass.equals(confirmPass)) {
            Toast.makeText(getContext(), "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        ChangePasswordRequest request = new ChangePasswordRequest(userId, currentPass, newPass);
        RetrofitService retrofitService = new RetrofitService();
        AuthApi authApi = retrofitService.getRetrofit().create(AuthApi.class);

        Call<UserResponse> call = authApi.changePassword(request);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    new android.os.Handler().postDelayed(() -> {
                        requireActivity().onBackPressed();
                    }, 500); // 0.5 giây
                } else {
                    Toast.makeText(getContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
