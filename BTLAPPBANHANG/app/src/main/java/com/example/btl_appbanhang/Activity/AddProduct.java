package com.example.btl_appbanhang.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_appbanhang.Adapter.DienthoaiAdapter;
import com.example.btl_appbanhang.Adapter.ProductAdapter;
import com.example.btl_appbanhang.Adapter.SanPhamMoiAdapter;
import com.example.btl_appbanhang.Interface.ItemClickListener;
import com.example.btl_appbanhang.Model.Address;
import com.example.btl_appbanhang.Model.Province;
import com.example.btl_appbanhang.Model.SanPhamMoi;
import com.example.btl_appbanhang.R;
import com.example.btl_appbanhang.Respone.Response;
import com.example.btl_appbanhang.Respone.ResponseProvince;
import com.example.btl_appbanhang.Respone.SanPhamMoiModel;
import com.example.btl_appbanhang.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import Retrofit.ApiBanHang;
import Retrofit.RetrofitClient;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;

public class AddProduct extends AppCompatActivity {
    RecyclerView rcv_product;
    ProductAdapter productAdapter;
    ArrayList<SanPhamMoi> list = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    int maTheLoai;
    FloatingActionButton fab_them;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AnhXa();
        getData();
        getEvent();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dang_xuat, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.btnDangXuat) {
            showDangXuat();
            Toast.makeText(this, "Dang Xuat", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    private void AnhXa() {
        rcv_product = findViewById(R.id.rcv_product);
        productAdapter = new ProductAdapter(this, list, new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {

            }

            @Override
            public void onButtonClick() {

            }

            @Override
            public void clickAddressAdapter() {
            }

            @Override
            public void clickProductAdapter(SanPhamMoi sanPhamMoi) {
                OpenDialog_Sua(sanPhamMoi);
            }
            @Override
            public void clickDeleteProductAdapter(SanPhamMoi sanPhamMoi) {
                showDeleteConfirmationDialog(sanPhamMoi);
            }
        });
        rcv_product.setAdapter(productAdapter);
        fab_them = findViewById(R.id.flbtn_add);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    private void getData() {
        ApiBanHang apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        compositeDisposable.add(apiBanHang.getSpmoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if (sanPhamMoiModel.isSuccess()) {
                                // Thiết lập Adapter cho RecyclerView
                                list = (ArrayList<SanPhamMoi>) sanPhamMoiModel.getResult();
                                productAdapter = new ProductAdapter(getApplicationContext(), list, new ItemClickListener() {
                                    @Override
                                    public void onClick(View view, int pos, boolean isLongClick) {

                                    }

                                    @Override
                                    public void onButtonClick() {

                                    }

                                    @Override
                                    public void clickAddressAdapter() {

                                    }

                                    @Override
                                    public void clickProductAdapter(SanPhamMoi sanPhamMoi) {
                                        OpenDialog_Sua(sanPhamMoi);
                                    }
                                    @Override
                                    public void clickDeleteProductAdapter(SanPhamMoi sanPhamMoi) {
                                        showDeleteConfirmationDialog(sanPhamMoi);
                                    }
                                });
                                rcv_product.setAdapter(productAdapter);
                                productAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getApplicationContext(), "Không lấy được sản phẩm mới", Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "KHÔNG KẾT NỐI ĐƯỢC TỚI CƠ SỞ DỮ LIỆU: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("API_ERROR", throwable.getMessage());
                        }
                ));
    }
    private void getEvent() {
        fab_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDialog_Them();
            }
        });
    }
    private void OpenDialog_Them() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddProduct.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_product, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        EditText edt_ten_sp = view.findViewById(R.id.edt_ten_sp);
        EditText edt_gia_sp = view.findViewById(R.id.edt_gia_sp);
        EditText edt_mo_ta_sp = view.findViewById(R.id.edt_mo_ta_sp);
        EditText edt_anh_sp = view.findViewById(R.id.edt_anh_sp);
        Spinner spn_loai_sp = view.findViewById(R.id.spn_loai_sp);

        TextView tv_title = view.findViewById(R.id.tv_title_dialog);
        tv_title.setText("Thêm sản phẩm");

        ArrayList<String> listL = new ArrayList<>();
        listL.add("Chọn loại sản phẩm");
        listL.add("Điện thoại");
        listL.add("Máy tính bảng");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddProduct.this, android.R.layout.simple_spinner_dropdown_item, listL);
        spn_loai_sp.setAdapter(adapter);
        spn_loai_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maTheLoai = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        view.findViewById(R.id.btn_them).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSP = edt_ten_sp.getText().toString().trim();
                String giaSP = edt_gia_sp.getText().toString().trim();
                String moTaSP = edt_mo_ta_sp.getText().toString().trim();
                String anhSP = edt_anh_sp.getText().toString().trim();

                if (tenSP.isEmpty() || giaSP.isEmpty() || moTaSP.isEmpty() || anhSP.isEmpty()) {
                    Toast.makeText(AddProduct.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (maTheLoai == 0) {
                    Toast.makeText(AddProduct.this, "Vui lòng chọn loại sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                }
                SanPhamMoi sanPhamMoi = new SanPhamMoi(tenSP, giaSP, anhSP, moTaSP, maTheLoai);
                postNewProduct(sanPhamMoi);
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.btn_huy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    private void postNewProduct(SanPhamMoi sanPhamMoi) {
        ApiBanHang apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        Call<Response<SanPhamMoi>> call = apiBanHang.postNewProduct(sanPhamMoi);
        call.enqueue(new Callback<Response<SanPhamMoi>>() {
            @Override
            public void onResponse(Call<Response<SanPhamMoi>> call, retrofit2.Response<Response<SanPhamMoi>> response) {
                if (response.isSuccessful() && response.body()!= null) {
//                    Toast.makeText(AddProduct.this, "TC3", Toast.LENGTH_SHORT).show();
//                        Log.d("onResponse: ", new Gson().toJson(response.body()));
                    if (response.body().isSuccess()) {
                        Toast.makeText(AddProduct.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        list.clear();
                        getData();
                    }
                } else {
                    Log.d("API Error", "Response code: " + response.code());
//                    Toast.makeText(AddProduct.this, "TC2", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<SanPhamMoi>> call, Throwable t) {

            }
        });
    }
    private void OpenDialog_Sua(SanPhamMoi sanPham) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddProduct.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_product, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        EditText edt_ten_sp = view.findViewById(R.id.edt_ten_sp);
        EditText edt_gia_sp = view.findViewById(R.id.edt_gia_sp);
        EditText edt_mo_ta_sp = view.findViewById(R.id.edt_mo_ta_sp);
        EditText edt_anh_sp = view.findViewById(R.id.edt_anh_sp);
        Spinner spn_loai_sp = view.findViewById(R.id.spn_loai_sp);

        TextView tv_title = view.findViewById(R.id.tv_title_dialog);
        tv_title.setText("Sửa sản phẩm");

        ArrayList<String> listL = new ArrayList<>();
        listL.add("Chọn loại sản phẩm");
        listL.add("Điện thoại");
        listL.add("Máy tính bảng");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddProduct.this, android.R.layout.simple_spinner_dropdown_item, listL);
        spn_loai_sp.setAdapter(adapter);
        spn_loai_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maTheLoai = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Hiển thị dữ liệu cũ
        edt_ten_sp.setText(sanPham.getTensp());
        edt_gia_sp.setText(sanPham.getGiasp());
        edt_mo_ta_sp.setText(sanPham.getMota());
        edt_anh_sp.setText(sanPham.getHinhanh());
        spn_loai_sp.setSelection(sanPham.getLoai());

        view.findViewById(R.id.btn_them).setOnClickListener(v -> {
            String tenSP = edt_ten_sp.getText().toString().trim();
            String giaSP = edt_gia_sp.getText().toString().trim();
            String moTaSP = edt_mo_ta_sp.getText().toString().trim();
            String anhSP = edt_anh_sp.getText().toString().trim();

            if (tenSP.isEmpty()) {
                edt_ten_sp.setError("Vui lòng nhập tên sản phẩm");
                return;
            }
            if (giaSP.isEmpty()) {
                edt_gia_sp.setError("Vui lòng nhập giá sản phẩm");
                return;
            }
            if (maTheLoai <= 0) {
                Toast.makeText(AddProduct.this, "Vui lòng chọn loại sản phẩm", Toast.LENGTH_SHORT).show();
                return;
            }

            SanPhamMoi sanPhamMoi = new SanPhamMoi();
            sanPhamMoi.setId(sanPham.getId());
            sanPhamMoi.setTensp(tenSP);
            sanPhamMoi.setGiasp(giaSP);
            sanPhamMoi.setMota(moTaSP);
            sanPhamMoi.setHinhanh(anhSP);
            sanPhamMoi.setLoai(maTheLoai);

            putProduct(sanPhamMoi); // Gửi API
            dialog.dismiss();
        });

        view.findViewById(R.id.btn_huy).setOnClickListener(v -> dialog.dismiss());
    }
    private void putProduct(SanPhamMoi sanPhamMoi) {
        ApiBanHang apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        Call<Response<SanPhamMoi>> call = apiBanHang.putProduct(sanPhamMoi);
        call.enqueue(new Callback<Response<SanPhamMoi>>() {
            @Override
            public void onResponse(Call<Response<SanPhamMoi>> call, retrofit2.Response<Response<SanPhamMoi>> response) {
//                Log.d("onResponse: ", new Gson().toJson(response.body()));
                if (response.isSuccessful() && response.body()!= null) {
//                    Toast.makeText(AddProduct.this, "TC3", Toast.LENGTH_SHORT).show();
                    if (response.body().isSuccess()) {
                        Toast.makeText(AddProduct.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        list.clear();
                        getData();
                    }
                } else {
                    Log.d("API Error", "Response code: " + response.code());
//                    Toast.makeText(AddProduct.this, "TC2", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<SanPhamMoi>> call, Throwable t) {
                Log.d("API Error", "Response code: " + t.getMessage());
//                Toast.makeText(AddProduct.this, "TC2", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void deleteProduct(SanPhamMoi sanPhamMoi) {
        ApiBanHang apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        Call<Response<SanPhamMoi>> call = apiBanHang.deleteProduct(sanPhamMoi.getId());
        call.enqueue(new Callback<Response<SanPhamMoi>>() {
            @Override
            public void onResponse(Call<Response<SanPhamMoi>> call, retrofit2.Response<Response<SanPhamMoi>> response) {
//                Log.d("onResponse: ", new Gson().toJson(response.body()));
                if (response.isSuccessful() && response.body()!= null) {
//                    Toast.makeText(AddProduct.this, "TC3", Toast.LENGTH_SHORT).show();
                    if (response.body().isSuccess()) {
                        Toast.makeText(AddProduct.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        list.clear();
                        getData();
                    }
                } else {
                    Log.d("API Error", "Response code: " + response.code());
//                    Toast.makeText(AddProduct.this, "TC2", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<SanPhamMoi>> call, Throwable t) {
                Log.d("API Error", "Response code: " + t.getMessage());
//                Toast.makeText(AddProduct.this, "TC4", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showDeleteConfirmationDialog(SanPhamMoi sanPhamMoi) {
        new AlertDialog.Builder(AddProduct.this)
                .setTitle("Xóa sản phẩm")
                .setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    deleteProduct(sanPhamMoi);
                })
                .setNegativeButton("Hủy", (dialog, which) -> {
                    // Đóng dialog nếu người dùng chọn hủy
                    dialog.dismiss();
                })
                .show();
    }
    private void showDangXuat() {
        new AlertDialog.Builder(AddProduct.this)
                .setTitle("Đăng xuất")
                .setMessage("Bạn có chắc chắn muốn đăng xuất?")
                .setPositiveButton("Đăng xuất", (dialog, which) -> {
                    Intent intent = new Intent(AddProduct.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Hủy", (dialog, which) -> {
                    // Đóng dialog nếu người dùng chọn hủy
                    dialog.dismiss();
                })
                .show();
    }

}