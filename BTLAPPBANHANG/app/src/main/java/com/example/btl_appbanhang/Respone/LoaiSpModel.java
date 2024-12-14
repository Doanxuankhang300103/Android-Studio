package com.example.btl_appbanhang.Respone;

import com.example.btl_appbanhang.Model.LoaiSp;

import java.util.List;

public class LoaiSpModel {
    private boolean success;
    private String message;
    private List<LoaiSp> result;

    // Constructor mặc định
    public LoaiSpModel() {
    }

    // Constructor với tham số
    public LoaiSpModel(boolean success, String message, List<LoaiSp> result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }

    // Getter và Setter
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

    public List<LoaiSp> getResult() {
        return result;
    }

    public void setResult(List<LoaiSp> result) {
        this.result = result;
    }
}
