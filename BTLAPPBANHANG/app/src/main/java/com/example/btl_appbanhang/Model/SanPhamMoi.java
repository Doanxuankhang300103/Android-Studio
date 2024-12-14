package com.example.btl_appbanhang.Model;

import java.io.Serializable;

public class SanPhamMoi implements Serializable {
    int id;
    String tensp;
    String giasp;
    String hinhanh;
    String mota;
    int loai;

    public SanPhamMoi() {
    }

    public SanPhamMoi(int id, String tensp, String giasp, String hinhanh, String mota, int loai) {
        this.id = id;
        this.tensp = tensp;
        this.giasp = giasp;
        this.hinhanh = hinhanh;
        this.mota = mota;
        this.loai = loai;
    }

    public SanPhamMoi(String tensp, String giasp, String hinhanh, String mota, int loai) {
        this.tensp = tensp;
        this.giasp = giasp;
        this.hinhanh = hinhanh;
        this.mota = mota;
        this.loai = loai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoai() {
        return loai;
    }

    public void setLoai(int loai) {
        this.loai = loai;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getGiasp() {
        return giasp;
    }

    public void setGiasp(String giasp) {
        this.giasp = giasp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }
}
