package com.example.template_project.response;

public class UserResponse {
    private String name;
    private String email;
    private String message;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }

    public UserResponse(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
    }
}
