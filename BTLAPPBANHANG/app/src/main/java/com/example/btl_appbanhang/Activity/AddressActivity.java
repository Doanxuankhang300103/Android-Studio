package com.example.btl_appbanhang.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_appbanhang.Adapter.AddressAdapter;
import com.example.btl_appbanhang.Interface.ItemClickListener;
import com.example.btl_appbanhang.Model.Address;
import com.example.btl_appbanhang.Model.SanPhamMoi;
import com.example.btl_appbanhang.R;
import com.example.btl_appbanhang.Respone.Response;
import com.example.btl_appbanhang.utils.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import Retrofit.ApiBanHang;
import Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;

public class AddressActivity extends AppCompatActivity {
    RecyclerView rcv_address;
    AddressAdapter addressAdapter;
    ArrayList<Address> addressList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    Toolbar toolbar;
    Button btnAddAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_address);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AnhXa();
        GetEvent();
        GetData();
    }
    private void AnhXa() {
        rcv_address = findViewById(R.id.rcv_address);
        addressAdapter = new AddressAdapter(this, addressList, new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {

            }

            @Override
            public void onButtonClick() {

            }

            @Override
            public void clickAddressAdapter() {
                finish();
            }
            @Override
            public void clickProductAdapter(SanPhamMoi sanPhamMoi) {

            }
            @Override
            public void clickDeleteProductAdapter(SanPhamMoi sanPhamMoi) {

            }
        });
        rcv_address.setLayoutManager(new LinearLayoutManager(this));
        rcv_address.setAdapter(addressAdapter);
        toolbar = findViewById(R.id.toolbar);
        btnAddAddress = findViewById(R.id.btnNewAddress);
        sharedPreferences = getSharedPreferences("INFO", Context.MODE_PRIVATE);
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
        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
                startActivity(intent);
            }
        });
    }
    private void GetData() {
        ApiBanHang apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        Call<Response<List<Address>>> call = apiBanHang.getAddressByUserID(sharedPreferences.getInt("id", 0));
        call.enqueue(new Callback<Response<List<Address>>>() {
            @Override
            public void onResponse(Call<Response<List<Address>>> call, retrofit2.Response<Response<List<Address>>> response) {
//                Toast.makeText(AddressActivity.this, "TC1", Toast.LENGTH_SHORT).show();
                if (response.isSuccessful() && response.body()!= null) {
//                    Toast.makeText(AddressActivity.this, "TC3", Toast.LENGTH_SHORT).show();
//                        Log.d("onResponse: ", new Gson().toJson(response.body()));
                    if (response.body().isSuccess()) {
                        List<Address> list = response.body().getData();
                        Log.d("onResponse: ", new Gson().toJson(list));
                        addressList = (ArrayList<Address>) list;
                        handleData();
//                        Toast.makeText(AddressActivity.this, "TC", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("API Error", "Response code: " + response.code());
//                    Toast.makeText(AddressActivity.this, "TC2", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<List<Address>>> call, Throwable t) {
                Log.d("API Fail", t.getMessage());
            }
        });
    }
    private void handleData() {
//        GetData();
        addressAdapter = new AddressAdapter(this, addressList, new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {

            }

            @Override
            public void onButtonClick() {

            }

            @Override
            public void clickAddressAdapter() {
                finish();
            }
            @Override
            public void clickProductAdapter(SanPhamMoi sanPhamMoi) {

            }
            @Override
            public void clickDeleteProductAdapter(SanPhamMoi sanPhamMoi) {

            }
        });
        rcv_address.setLayoutManager(new LinearLayoutManager(this));
        rcv_address.setAdapter(addressAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetData();
    }
}