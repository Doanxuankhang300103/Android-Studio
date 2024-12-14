package com.example.btl_appbanhang.Respone;

import com.example.btl_appbanhang.Model.OrderItem;
import com.example.btl_appbanhang.Model.User;

import java.util.List;

public class OrderResponse {
    private boolean success;
    private String message;
    private List<OrderItem> order_item; // Danh sách các mục đơn hàng
    // Getters and Setters

    public OrderResponse() {
    }

    public OrderResponse(boolean success, String message, List<OrderItem> order_item) {
        this.success = success;
        this.message = message;
        this.order_item = order_item;
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

    public List<OrderItem> getOrder_item() {
        return order_item;
    }

    public void setOrder_item(List<OrderItem> order_item) {
        this.order_item = order_item;
    }
}

