package com.example.btl_appbanhang.Adapter;

import static com.example.btl_appbanhang.utils.Utils.BASE_URL;
import static com.example.btl_appbanhang.utils.Utils.BASE_URL_TINH_THANH;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_appbanhang.Interface.ItemClickListener;
import com.example.btl_appbanhang.Model.Address;
import com.example.btl_appbanhang.Model.Province;
import com.example.btl_appbanhang.R;
import com.example.btl_appbanhang.Respone.Response;
import com.example.btl_appbanhang.Respone.ResponseProvince;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import Retrofit.ApiBanHang;
import Retrofit.ApiTinhThanh;
import Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Address> list;
    private ItemClickListener clickListener;
    public AddressAdapter(Context context, ArrayList<Address> list, ItemClickListener clickListener) {
        this.context = context;
        this.list = list;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Address address = list.get(position);
        holder.rdi_is_active.setChecked(address.getIs_active() == 1);
//        if (address.getIs_active() == 1) {
//            holder.rdi_is_active.setChecked(true);
//        } else {
//            holder.rdi_is_active.setChecked(false);
//        }
        holder.tv_ho_va_ten.setText(address.getName());
        holder.tv_sdt.setText(address.getPhone());
        holder.tv_address.setText(address.getAddress());
        holder.tv_provider.setText(address.getWard() + ", " + address.getDistrict() + ", " + address.getCity());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putAddressIsActive(address.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RadioButton rdi_is_active;
        TextView tv_ho_va_ten, tv_address, tv_sdt, tv_provider;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ các view trong item_giohang.xml
            rdi_is_active = itemView.findViewById(R.id.rdo_is_active);
            tv_ho_va_ten = itemView.findViewById(R.id.tv_ho_va_ten);
            tv_sdt = itemView.findViewById(R.id.tv_sdt);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_provider = itemView.findViewById(R.id.tv_provider);
        }
    }

    private void putAddressIsActive(int id) {
        ApiBanHang apiBanHang = RetrofitClient.getInstance(BASE_URL).create(ApiBanHang.class);
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("id", id);// ID của address cần cập nhật
        Call<Response<Address>> call = apiBanHang.putAddressIsActive(requestBody);
        call.enqueue(new Callback<Response<Address>>() {
            @Override
            public void onResponse(Call<Response<Address>> call, retrofit2.Response<Response<Address>> response) {
                if (response.isSuccessful() && response.body()!= null) {
//                    Toast.makeText(context, "TC3", Toast.LENGTH_SHORT).show();
//                        Log.d("onResponse: ", new Gson().toJson(response.body()));
                    if (response.body().isSuccess()) {
                        clickListener.clickAddressAdapter();
                        Toast.makeText(context, "Đổi địa chỉ thành công", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("API Error", "Response code: " + response.code());
//                    Toast.makeText(context, "TC2", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<Address>> call, Throwable t) {
                Log.d("API Error", t.getMessage());
            }
        });
    }
}
