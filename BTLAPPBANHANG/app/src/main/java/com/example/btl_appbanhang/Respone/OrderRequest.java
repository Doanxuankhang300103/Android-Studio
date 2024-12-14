package com.example.btl_appbanhang.Respone;

import com.example.btl_appbanhang.Model.OrderItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderRequest {
    @SerializedName("id")
    private int id;
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("total_amount")
    private double total_amount;
    @SerializedName("status")
    private int status;
    @SerializedName("payment_method")
    private String paymentMethod;
    @SerializedName("order_item")
    private List<OrderItem> order_item;
    @SerializedName("address_id")
    private int address_id;

    public OrderRequest(int user_id, int status, String paymentMethod, List<OrderItem> order_item, int address_id) {
        this.user_id = user_id;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.order_item = order_item;
        this.address_id = address_id;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<OrderItem> getOrder_item() {
        return order_item;
    }

    public void setOrder_item(List<OrderItem> order_item) {
        this.order_item = order_item;
    }
}