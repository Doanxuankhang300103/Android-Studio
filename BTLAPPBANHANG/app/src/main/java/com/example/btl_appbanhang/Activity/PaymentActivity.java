package com.example.btl_appbanhang.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_appbanhang.Adapter.PaymentAdapter;
import com.example.btl_appbanhang.Dao.DaoCart;
import com.example.btl_appbanhang.Model.Address;
import com.example.btl_appbanhang.Model.Cart;
import com.example.btl_appbanhang.Model.GioHang;
import com.example.btl_appbanhang.Model.OrderItem;
import com.example.btl_appbanhang.R;
import com.example.btl_appbanhang.Respone.OrderRequest;
import com.example.btl_appbanhang.Respone.OrderResponse;
import com.example.btl_appbanhang.utils.Utils;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Retrofit.ApiBanHang;
import Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    TextView tongtien;
    androidx.appcompat.widget.Toolbar toolbar; // Sửa lại Toolbar import đúng
    RecyclerView recyclerView;
    Button btnThanhToan;
    EditText edtDiaChi;
    //    GioHangAdapter adapter;
    List<GioHang> gioHangList;
    List<Cart> cartList;
    PaymentAdapter paymentAdapter;
    private DaoCart daoCart;
    private SharedPreferences sharedPreferences;
    private Spinner spinnerPaymentMethods;
    ApiBanHang apiBanHang;
    String paymentMethod;
    int address_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Anhxa();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thanh Toán");
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        //
        chonPhuongThucThanhToan();
        loadData();
        getDataAddressDefault();
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPayment();
            }
        });
        edtDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, AddressActivity.class);
                startActivity(intent);
            }
        });
    }
    private void loadData() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartList = daoCart.selectCartByIdUser(String.valueOf(sharedPreferences.getInt("id", 0)));

        if (cartList.size() != 0) {
            paymentAdapter = new PaymentAdapter(this, cartList);
            recyclerView.setAdapter(paymentAdapter);
        }
        tinhTongTien();
    }
    private void tinhTongTien() {
        long tongTien = 0;
        for (Cart item : cartList) {
            tongTien += item.getQuantity() * item.getPrice();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien.setText("Total payment: \n\t\t\t" + decimalFormat.format(tongTien) + " VNĐ");
    }
    private void chonPhuongThucThanhToan() {
        // Tạo adapter để kết nối với spinner
        ArrayList<String> thanhToanArr = new ArrayList<>();
        thanhToanArr.add("Thanh toán online");
        thanhToanArr.add("Thanh toán khi nhận hàng");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, thanhToanArr);
        spinnerPaymentMethods.setAdapter(adapter);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.payment_methods, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerPaymentMethods.setAdapter(adapter);

        // Thiết lập sự kiện chọn item trong spinner
        spinnerPaymentMethods.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    paymentMethod = "online";
                } else if (position == 1) {
                    paymentMethod = "cash_on_delivery";
                }
//                Toast.makeText(PaymentActivity.this, position+": " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void Anhxa() {
        spinnerPaymentMethods = findViewById(R.id.spinnerPaymentMethods);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.rcv_payments);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        daoCart = new DaoCart(this);
        tongtien = findViewById(R.id.txtTongTien);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        sharedPreferences = getSharedPreferences("INFO", Context.MODE_PRIVATE);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
    }
    private void setPayment() {
        ApiBanHang apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        // Trong một phương thức nào đó, ví dụ trong onCreate() của Activity

        // Tạo dữ liệu đơn hàng và các mục
        List<OrderItem> orderItems = new ArrayList<>();
        for (Cart cart : cartList) {
            orderItems.add(new OrderItem( cart.getIdproduct(), cart.getQuantity(), cart.getPrice()));
        }

        OrderRequest request = new OrderRequest(sharedPreferences.getInt("id", 0), 1,  paymentMethod, orderItems, address_id);
//        Toast.makeText(this, "" + address_id, Toast.LENGTH_SHORT).show();
        // Gọi API
        Call<OrderResponse> call = apiBanHang.postPayment(request);
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, retrofit2.Response<OrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response Body", "Response: " + response.body());
                    Log.d("API Response", response.body().getMessage());
                    if (response.body().isSuccess()) {
                        Toast.makeText(PaymentActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                        daoCart.deleteAll(String.valueOf(sharedPreferences.getInt("id", 0)));
                        finishAffinity();
                        startActivity(new Intent(PaymentActivity.this, MainActivity.class));
                    }
                } else {
                    Log.e("API Error", "Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.e("API Failure", t.getMessage());
            }
        });
    }
    private void getDataAddressDefault() {
        ApiBanHang apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        Call<com.example.btl_appbanhang.Respone.Response<List<Address>>> call = apiBanHang.getAddressDefault(sharedPreferences.getInt("id", 0));
        call.enqueue(new Callback<com.example.btl_appbanhang.Respone.Response<List<Address>>>() {
            @Override
            public void onResponse(Call<com.example.btl_appbanhang.Respone.Response<List<Address>>> call, Response<com.example.btl_appbanhang.Respone.Response<List<Address>>> response) {
                if (response.isSuccessful() && response.body()!= null) {
                    if (response.body().isSuccess()) {
                        List<Address> addressList = response.body().getData();
//                        Log.d("onResponse: ", new Gson().toJson(addressList));
                        Address addressDefault = addressList.get(0);
                        if (addressDefault != null) {
                            edtDiaChi.setText(addressDefault.getName()+" | " + addressDefault.getPhone() + "\n" + addressDefault.getAddress() + "\n" + addressDefault.getWard() + ", " + addressDefault.getDistrict() + ", " + addressDefault.getCity());
                            address_id = addressDefault.getId();
                        }
//                        Toast.makeText(PaymentActivity.this, "TC", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("API Error", "Response code: " + response.code());
//                    Toast.makeText(PaymentActivity.this, "TC2", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<com.example.btl_appbanhang.Respone.Response<List<Address>>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataAddressDefault();
    }
}