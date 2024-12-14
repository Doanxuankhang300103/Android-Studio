package com.example.btl_appbanhang.Model;

public class GioHang {
    private int id;
    private String tensp;
    private long giasp;
    private String hinhanh;
    private int soluong;

    public GioHang(int id, String tensp, String giasp, String hinhanh, int soluong) {

    }

    public int getId() {
        return id;
    }

    public String getTensp() {
        return tensp;
    }

    public long getGiasp() {
        return giasp;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {

        this.soluong = soluong;
    }
}