package com.example.btl_appbanhang.Model;

import com.google.gson.annotations.SerializedName;

public class Cart {
//    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//            "tensp TEXT NOT NULL, " +
//            "giasp INTEGER NOT NULL, " +
//            "soluong INTEGER NOT NULL, " +
//            "tongtien INTEGER NOT NULL, " +
//            "hinhanh TEXT NOT NULL, " +
//            "iduser TEXT NOT NULL)";
    @SerializedName("id")
    private int id;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("totalprice")
    private int price;
    @SerializedName("iduser")
    private int iduser;
    @SerializedName("idproduct")
    private int idproduct;

    public Cart() {
    }

    public Cart(int quantity, int price, int iduser, int idproduct) {
        this.quantity = quantity;
        this.price = price;
        this.iduser = iduser;
        this.idproduct = idproduct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getIdproduct() {
        return idproduct;
    }

    public void setIdproduct(int idproduct) {
        this.idproduct = idproduct;
    }
}
