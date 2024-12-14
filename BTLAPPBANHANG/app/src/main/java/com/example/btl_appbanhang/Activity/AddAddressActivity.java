package com.example.btl_appbanhang.Activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import static com.example.btl_appbanhang.utils.Utils.BASE_URL;
import static com.example.btl_appbanhang.utils.Utils.BASE_URL_TINH_THANH;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btl_appbanhang.Model.Address;
import com.example.btl_appbanhang.Model.Province;
import com.example.btl_appbanhang.R;
import com.example.btl_appbanhang.Respone.ResponseProvince;
import com.example.btl_appbanhang.utils.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Retrofit.ApiBanHang;
import Retrofit.ApiTinhThanh;
import Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressActivity extends AppCompatActivity {
    EditText edtHoVaTen, edtSoDienThoai, edtTinh, edtHuyen, edtXa, edtAddress;
    Button btnHoanThanh;
    String Tinh = null;
    String Huyen = null;
    List<Province> provinces;
    boolean check = false;
    Toolbar toolbar;
    private SharedPreferences sharedPreferences;
    int id_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_address);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AnhXa();
        GetEvent();
    }
    private void AnhXa() {
        edtTinh = findViewById(R.id.edtTinh);
        edtHoVaTen = findViewById(R.id.edtHoVaTen);
        edtSoDienThoai = findViewById(R.id.edtSoDienThoai);
        edtHuyen = findViewById(R.id.edtHuyen);
        edtXa = findViewById(R.id.edtXa);
        edtAddress = findViewById(R.id.edtAddress);
        btnHoanThanh = findViewById(R.id.btnHoanThanh);
        toolbar = findViewById(R.id.toolbar);
        edtTinh.setText("Ha Noi");
        edtHuyen.setText("Chuong My");
        edtXa.setText("Phuong Dong");
        sharedPreferences = getSharedPreferences("INFO", Context.MODE_PRIVATE);
        id_user = sharedPreferences.getInt("id", 0);
    }
    private void GetEvent() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        edtTinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!check) {
                    check = true;
                    DialogGetTinh();
                }
            }
        });
        edtHuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!check && Tinh != null) {
                    check = true;
                    DialogGetHuyen();
                } else {
                    Toast.makeText(AddAddressActivity.this, "Vui lòng chọn tỉnh trước", Toast.LENGTH_SHORT).show();
                }
            }
        });
        edtXa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!check && Huyen != null) {
                    check = true;
                    DialogGetXa();
                } else {
                    Toast.makeText(AddAddressActivity.this, "Vui lòng chọn huyện trước", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnHoanThanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tinh = edtTinh.getText().toString();
                String hoVaTen = edtHoVaTen.getText().toString();
                String soDienThoai = edtSoDienThoai.getText().toString();
                String huyen = edtHuyen.getText().toString();
                String xa = edtXa.getText().toString();
                String diaChi = edtAddress.getText().toString();
                if (tinh.isEmpty() || hoVaTen.isEmpty() || soDienThoai.isEmpty() || huyen.isEmpty() || xa.isEmpty() || diaChi.isEmpty()) {
                    Toast.makeText(AddAddressActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                Address newAddress = new Address(id_user, hoVaTen, soDienThoai, diaChi, xa, huyen, tinh);
                postAddress(newAddress);
            }
        });
    }
    private void DialogGetTinh() {
        ApiTinhThanh apiTinhThanh = RetrofitClient.getInstance(BASE_URL_TINH_THANH).create(ApiTinhThanh.class);
        Call<ResponseProvince<List<Province>>> call = apiTinhThanh.getProvinces("1", "0");
        Log.d(TAG, "DialogGetTinh: " + new Gson().toJson(apiTinhThanh));
        call.enqueue(new Callback<ResponseProvince<List<Province>>>() {
            @Override
            public void onResponse(Call<ResponseProvince<List<Province>>> call, Response<ResponseProvince<List<Province>>> response) {
                if (response.isSuccessful() && response.body()!= null) {
                    ResponseProvince<List<Province>> responseProvinces = response.body();
//                    Log.d("API Success", responseProvinces.getError_text());
                    provinces = responseProvinces.getData();
                    // Tạo một Map để lưu id và tên các tỉnh
                    Map<String, String> provinceMap = new HashMap<>();
                    for (Province province : provinces) {
                        provinceMap.put(province.getId(), province.getFull_name());
                    }

                    // Hiển thị danh sách các tỉnh trong một Dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddAddressActivity.this);
                    builder.setTitle("Chọn Tỉnh Thành");

                    // Sử dụng List<String> để lưu tên các tỉnh (hiển thị trong dialog)
                    List<String> provinceNames = new ArrayList<>(provinceMap.values());
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_list_item_1, provinceNames);
                    builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            // Khi người dùng chọn một tỉnh thành
                            String selectedProvinceName = provinceNames.get(position);
                            String selectedProvinceId = null;

                            // Lấy id của tỉnh thành đã chọn từ Map
                            for (Map.Entry<String, String> entry : provinceMap.entrySet()) {
                                if (entry.getValue().equals(selectedProvinceName)) {
                                    selectedProvinceId = entry.getKey();
                                    break;
                                }
                            }
                            Tinh = selectedProvinceId;
                            edtTinh.setText(selectedProvinceName);
                            check = false;
                        }
                    });
                    // Thêm sự kiện khi dialog bị hủy (người dùng không chọn gì)
                    builder.setOnCancelListener(dialog -> {
                        // Đặt check lại về false khi người dùng hủy dialog
                        check = false;
                    });
                    // Hiển thị dialog
                    builder.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseProvince<List<Province>>> call, Throwable t) {
//                Toast.makeText(AddAddressActivity.this, "Click 3", Toast.LENGTH_SHORT).show();
                Log.d("API Fail", t.getMessage());
            }
        });

    }
    private void DialogGetHuyen() {
        ApiTinhThanh apiTinhThanh = RetrofitClient.getInstance(BASE_URL_TINH_THANH).create(ApiTinhThanh.class);
        Call<ResponseProvince<List<Province>>> call = apiTinhThanh.getProvinces("2", Tinh);
        call.enqueue(new Callback<ResponseProvince<List<Province>>>() {
            @Override
            public void onResponse(Call<ResponseProvince<List<Province>>> call, Response<ResponseProvince<List<Province>>> response) {
                if (response.isSuccessful() && response.body()!= null) {
                    ResponseProvince<List<Province>> responseProvinces = response.body();
//                    Log.d("API Success", responseProvinces.getError_text());
                    provinces = responseProvinces.getData();
                    // Tạo một Map để lưu id và tên các tỉnh
                    Map<String, String> provinceMap = new HashMap<>();
                    for (Province province : provinces) {
                        provinceMap.put(province.getId(), province.getFull_name());
                    }

                    // Hiển thị danh sách các tỉnh trong một Dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddAddressActivity.this);
                    builder.setTitle("Chọn Quận Huyện");

                    // Sử dụng List<String> để lưu tên các tỉnh (hiển thị trong dialog)
                    List<String> provinceNames = new ArrayList<>(provinceMap.values());
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_list_item_1, provinceNames);
                    builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            // Khi người dùng chọn một tỉnh thành
                            String selectedProvinceName = provinceNames.get(position);
                            String selectedProvinceId = null;

                            // Lấy id của tỉnh thành đã chọn từ Map
                            for (Map.Entry<String, String> entry : provinceMap.entrySet()) {
                                if (entry.getValue().equals(selectedProvinceName)) {
                                    selectedProvinceId = entry.getKey();
                                    break;
                                }
                            }
                            Huyen = selectedProvinceId;
                            edtHuyen.setText(selectedProvinceName);
                            check = false;
                        }
                    });
                    // Thêm sự kiện khi dialog bị hủy (người dùng không chọn gì)
                    builder.setOnCancelListener(dialog -> {
                        // Đặt check lại về false khi người dùng hủy dialog
                        check = false;
                    });
                    // Hiển thị dialog
                    builder.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseProvince<List<Province>>> call, Throwable t) {
//                Toast.makeText(AddAddressActivity.this, "Click 3", Toast.LENGTH_SHORT).show();
                Log.d("API Fail", t.getMessage());
            }
        });

    }
    private void DialogGetXa() {
        ApiTinhThanh apiTinhThanh = RetrofitClient.getInstance(BASE_URL_TINH_THANH).create(ApiTinhThanh.class);
        Call<ResponseProvince<List<Province>>> call = apiTinhThanh.getProvinces("3", Huyen);
        call.enqueue(new Callback<ResponseProvince<List<Province>>>() {
            @Override
            public void onResponse(Call<ResponseProvince<List<Province>>> call, Response<ResponseProvince<List<Province>>> response) {
                if (response.isSuccessful() && response.body()!= null) {
                    ResponseProvince<List<Province>> responseProvinces = response.body();
//                    Log.d("API Success", responseProvinces.getError_text());
                    provinces = responseProvinces.getData();
                    // Tạo một Map để lưu id và tên các tỉnh
                    Map<String, String> provinceMap = new HashMap<>();
                    for (Province province : provinces) {
                        provinceMap.put(province.getId(), province.getFull_name());
                    }

                    // Hiển thị danh sách các tỉnh trong một Dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddAddressActivity.this);
                    builder.setTitle("Chọn Quận Huyện");

                    // Sử dụng List<String> để lưu tên các tỉnh (hiển thị trong dialog)
                    List<String> provinceNames = new ArrayList<>(provinceMap.values());
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_list_item_1, provinceNames);
                    builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            // Khi người dùng chọn một tỉnh thành
                            String selectedProvinceName = provinceNames.get(position);
                            String selectedProvinceId = null;

                            // Lấy id của tỉnh thành đã chọn từ Map
                            for (Map.Entry<String, String> entry : provinceMap.entrySet()) {
                                if (entry.getValue().equals(selectedProvinceName)) {
                                    selectedProvinceId = entry.getKey();
                                    break;
                                }
                            }
//                            Huyen = selectedProvinceId;
                            edtXa.setText(selectedProvinceName);
                            check = false;
                        }
                    });
                    // Thêm sự kiện khi dialog bị hủy (người dùng không chọn gì)
                    builder.setOnCancelListener(dialog -> {
                        // Đặt check lại về false khi người dùng hủy dialog
                        check = false;
                    });
                    // Hiển thị dialog
                    builder.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseProvince<List<Province>>> call, Throwable t) {
//                Toast.makeText(AddAddressActivity.this, "Click 3", Toast.LENGTH_SHORT).show();
                Log.d("API Fail", t.getMessage());
            }
        });

    }
    private void postAddress(Address newAddress) {
//        Log.d("Address Data", new Gson().toJson(newAddress));
        ApiBanHang apiBanHang = RetrofitClient.getInstance(BASE_URL).create(ApiBanHang.class);
        Call<com.example.btl_appbanhang.Respone.Response<Address>> call = apiBanHang.postAddress(newAddress);
        call.enqueue(new Callback<com.example.btl_appbanhang.Respone.Response<Address>>() {
            @Override
            public void onResponse(Call<com.example.btl_appbanhang.Respone.Response<Address>> call, Response<com.example.btl_appbanhang.Respone.Response<Address>> response) {
                Log.d("onResponse1", String.valueOf(response));
                if (response.isSuccessful() && response.body() != null) {
                    com.example.btl_appbanhang.Respone.Response<Address> responseAddress = response.body();
//                    Log.d("API Success", responseAddress.getMessage());
                    Toast.makeText(AddAddressActivity.this, "Thêm đia chỉ thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddAddressActivity.this, "Thêm đia chỉ thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<com.example.btl_appbanhang.Respone.Response<Address>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}