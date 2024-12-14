package com.example.btl_appbanhang.Respone;

import com.example.btl_appbanhang.Model.SanPhamMoi;
import com.google.gson.annotations.SerializedName;

public class productResponse {
    private boolean success;
    private String message;
    @SerializedName("product")
    private SanPhamMoi product;

    public productResponse(boolean success, String message, SanPhamMoi product) {
        this.success = success;
        this.message = message;
        this.product = product;
    }

    public productResponse() {
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

    public SanPhamMoi getProduct() {
        return product;
    }

    public void setProduct(SanPhamMoi product) {
        this.product = product;
    }
}
