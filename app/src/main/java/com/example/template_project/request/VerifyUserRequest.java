package com.example.template_project.request;

public class VerifyUserRequest {
    private String email;
    private String password;
    private String otp;

    public VerifyUserRequest(String email, String password, String otp) {
        this.email = email;
        this.password = password;
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getOtp() {
        return otp;
    }
}
