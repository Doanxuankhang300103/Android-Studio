package com.example.btl_appbanhang.Respone;

import com.example.btl_appbanhang.Model.User;

public class loginResponse {
    private boolean success;
    private String message;
    private User user;

    public loginResponse() {
    }

    public loginResponse(boolean success, String message, User user) {
        this.success = success;
        this.message = message;
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
