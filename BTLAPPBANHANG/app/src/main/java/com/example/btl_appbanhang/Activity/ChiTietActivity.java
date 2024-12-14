package com.example.btl_appbanhang.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.btl_appbanhang.Dao.DaoCart;
import com.example.btl_appbanhang.Model.Cart;
import com.example.btl_appbanhang.Model.GioHang;
import com.example.btl_appbanhang.R;
import com.example.btl_appbanhang.Model.SanPhamMoi;
import com.example.btl_appbanhang.utils.Utils;

import java.util.Objects;

public class ChiTietActivity extends AppCompatActivity {
    private TextView tensp, giasp, mota, badgeCount;
    private Button nutthem;
    private ImageView img, cartIcon;
    private Spinner spinner;
    private Toolbar toolbar;
    private SanPhamMoi sanPhamMoi;
    private DaoCart daoCart;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);

        Anhxa();
        ActionToolBar();
        initData();

        nutthem.setOnClickListener(v -> addToCart());
    }

    private void initData() {
        sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra("chitiet");

        if (sanPhamMoi != null) {
            tensp.setText(sanPhamMoi.getTensp());
            mota.setText(sanPhamMoi.getMota());
            giasp.setText("Giá: " + sanPhamMoi.getGiasp() + " VNĐ");

            Glide.with(this)
                    .load(sanPhamMoi.getHinhanh())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(img);

            // Setup spinner với số lượng sản phẩm
            Integer[] so = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            ArrayAdapter<Integer> adapterspinner = new ArrayAdapter<>(this,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, so);
            adapterspinner.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            spinner.setAdapter(adapterspinner);
        }

//        updateBadge();
    }

//    private void themVaoGioHang() {
////        if (sanPhamMoi != null) {
////            if (Utils.manggiohang == null) {
////                Utils.manggiohang = new ArrayList<>();
////            }
////
////            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
////            boolean exists = false;
////
////            for (GioHang item : Utils.manggiohang) {
////                if (item.getId() == sanPhamMoi.getId()) {
////                    item.setSoluong(item.getSoluong() + soluong);
////                    exists = true;
////                    break;
////                }
////            }
////
////            if (!exists) {
////                GioHang gioHang = new GioHang(sanPhamMoi.getId(), sanPhamMoi.getTensp(), sanPhamMoi.getGiasp(), sanPhamMoi.getHinhanh(), soluong);
////                Utils.manggiohang.add(gioHang);
////            }
////
////            Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
////            updateBadge();
////        }
//
//    }
    private void addToCart() {
        if (sanPhamMoi != null) {
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());

            boolean exists = false;

            if (!exists) {
                Cart cart = new Cart();
                cart.setQuantity(soluong);
                cart.setPrice(convertStringToInt(sanPhamMoi.getGiasp()));
                cart.setIduser(sharedPreferences.getInt("id", 0));
                cart.setIdproduct(sanPhamMoi.getId());
                daoCart.insert(cart);
            }

            Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        }
    }
    public int convertStringToInt(String input) {
        // Loại bỏ dấu chấm (.)
        String sanitizedInput = input.replace(".", "");

        // Chuyển chuỗi sang kiểu int
        return Integer.parseInt(sanitizedInput);
    }
    private void updateBadge() {
        if (Utils.manggiohang != null && Utils.manggiohang.size() > 0) {
            int totalItems = 0;
            for (GioHang item : Utils.manggiohang) {
                totalItems += item.getSoluong();
            }
            badgeCount.setText(String.valueOf(totalItems));
            badgeCount.setVisibility(View.VISIBLE);
        } else {
            badgeCount.setVisibility(View.GONE);
        }
    }

    private void Anhxa() {
        tensp = findViewById(R.id.ctdt_ten);
        giasp = findViewById(R.id.ctdt_gia);
        mota = findViewById(R.id.ctdt_mota);
        nutthem = findViewById(R.id.nutthemgh);
        spinner = findViewById(R.id.spinnerct);
        img = findViewById(R.id.ctdt_hinhanh);
        toolbar = findViewById(R.id.toolbarct);
        badgeCount = findViewById(R.id.badge_count);
        cartIcon = findViewById(R.id.cart_icon);
        FrameLayout frameLayout = findViewById(R.id.framegiohang);
        frameLayout.setOnClickListener(view -> {
            Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
            startActivity(giohang);
        });
        daoCart = new DaoCart(this);
        sharedPreferences = getSharedPreferences("INFO", Context.MODE_PRIVATE);
//        Toast.makeText(this, "IDUser: " + sharedPreferences.getInt("id", 1), Toast.LENGTH_SHORT).show();
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());
    }
}
