package com.example.btl_appbanhang.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_appbanhang.Adapter.CartAdapter;
import com.example.btl_appbanhang.Adapter.GioHangAdapter;
import com.example.btl_appbanhang.Dao.DaoCart;
import com.example.btl_appbanhang.Interface.ItemClickListener;
import com.example.btl_appbanhang.Model.Cart;
import com.example.btl_appbanhang.Model.GioHang;
import com.example.btl_appbanhang.Model.SanPhamMoi;
import com.example.btl_appbanhang.R;
import com.example.btl_appbanhang.utils.Utils;
import java.text.DecimalFormat;
import java.util.List;

public class GioHangActivity extends AppCompatActivity {
    TextView giohangtrong, tongtien;
    androidx.appcompat.widget.Toolbar toolbar; // Sửa lại Toolbar import đúng
    RecyclerView recyclerView;
    Button nutmua;
//    GioHangAdapter adapter;
    List<GioHang> gioHangList;
    List<Cart> cartList;
    private DaoCart daoCart;
    private CartAdapter cartAdapter;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        Anhxa();
        initControl();
        tinhTongTien(); // Tính tổng tiền khi khởi tạo activity
//        Toast.makeText(this, "" + sharedPreferences.getInt("id", 1), Toast.LENGTH_SHORT).show();
        nutmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartList.size() != 0) {
                    Intent intent = new Intent(GioHangActivity.this, PaymentActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(GioHangActivity.this, "Giỏ hàng không có sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initControl() {
        // Thiết lập toolbar và nút quay lại
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Kiểm tra giỏ hàng có sản phẩm hay không
//        if (Utils.manggiohang.size() == 0) {
//            giohangtrong.setVisibility(View.VISIBLE);
//        } else {
//            giohangtrong.setVisibility(View.GONE); // Ẩn thông báo giỏ hàng trống
//            adapter = new GioHangAdapter(getApplicationContext(), Utils.manggiohang);
//            recyclerView.setAdapter(adapter);
//        }
        cartList = daoCart.selectCartByIdUser(String.valueOf(sharedPreferences.getInt("id", 0)));

        if (cartList.size() == 0) {
            giohangtrong.setVisibility(View.VISIBLE);
        } else {
            giohangtrong.setVisibility(View.GONE); // Ẩn thông báo giỏ hàng trống
//            Toast.makeText(this, "Có sản phẩm", Toast.LENGTH_SHORT).show();
//            adapter = new GioHangAdapter(getApplicationContext(), Utils.manggiohang);
            cartAdapter = new CartAdapter(this, cartList, new ItemClickListener() {
                @Override
                public void onClick(View view, int pos, boolean isLongClick) {

                }

                @Override
                public void onButtonClick() {
                    tinhTongTien();
                }

                @Override
                public void clickAddressAdapter() {

                }
                @Override
                public void clickProductAdapter(SanPhamMoi sanPhamMoi) {

                }
                @Override
                public void clickDeleteProductAdapter(SanPhamMoi sanPhamMoi) {

                }
            });
            recyclerView.setAdapter(cartAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_clear_cart, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.clear_cart) {
            // Xóa toàn bộ gi�� hàng
            OpenDialog_ClearCart();
        }
        return super.onOptionsItemSelected(item);
    }

    public void OpenDialog_ClearCart() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.warning);
        builder.setTitle("WARNING");
        builder.setMessage("Bạn có muốn xóa toàn giỏ hàng khoong ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                daoCart.deleteAll(String.valueOf(sharedPreferences.getInt("id", 0)));
                cartList.clear();
                cartAdapter.notifyDataSetChanged();
                tinhTongTien();
                Toast.makeText(GioHangActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
//                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }
    // Hàm tính tổng tiền cho tất cả sản phẩm trong giỏ hàng
    private void tinhTongTien() {
        long tongTien = 0;
        for (Cart item : cartList) {
            tongTien += item.getQuantity() * item.getPrice();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien.setText(decimalFormat.format(tongTien) + " VNĐ");
    }

    private void Anhxa() {
        giohangtrong = findViewById(R.id.txtgiohangtrong);
        tongtien = findViewById(R.id.txttongtien);
        toolbar = findViewById(R.id.toolbargh);
        recyclerView = findViewById(R.id.recycleviewgh);
        nutmua = findViewById(R.id.nutmuahang);
        daoCart = new DaoCart(this);
        sharedPreferences = getSharedPreferences("INFO", Context.MODE_PRIVATE);
    }
}
