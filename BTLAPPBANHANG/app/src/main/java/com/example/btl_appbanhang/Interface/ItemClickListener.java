package com.example.btl_appbanhang.Interface;

import android.view.View;

import com.example.btl_appbanhang.Model.Cart;
import com.example.btl_appbanhang.Model.SanPhamMoi;

public interface ItemClickListener {
    void onClick(View view, int pos, boolean isLongClick);
    void onButtonClick();// Truyền thêm vị trí nếu cần
    void clickAddressAdapter();
    void clickProductAdapter(SanPhamMoi sanPhamMoi);
    void clickDeleteProductAdapter(SanPhamMoi sanPhamMoi);
}