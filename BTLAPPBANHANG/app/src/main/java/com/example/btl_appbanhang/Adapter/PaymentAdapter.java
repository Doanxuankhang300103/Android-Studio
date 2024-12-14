package com.example.btl_appbanhang.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.btl_appbanhang.Dao.DaoCart;
import com.example.btl_appbanhang.Interface.ItemClickListener;
import com.example.btl_appbanhang.Model.Cart;
import com.example.btl_appbanhang.R;
import com.example.btl_appbanhang.Respone.productResponse;
import com.example.btl_appbanhang.Model.SanPhamMoi;
import com.example.btl_appbanhang.utils.Utils;

import java.text.DecimalFormat;
import java.util.List;

import Retrofit.ApiBanHang;
import Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {
    private Context context;
    private List<Cart> cartList;
    private DaoCart daoCart;
    private SharedPreferences sharedPreferences;
    private ApiBanHang apiBanHang;
    private ItemClickListener itemClickListener;

    public PaymentAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
        daoCart = new DaoCart(context);
        sharedPreferences = context.getSharedPreferences("INFO", Context.MODE_PRIVATE);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cart cart = cartList.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

        apiBanHang.getProductById(cart.getIdproduct()).enqueue(new Callback<productResponse>() {
            @Override
            public void onResponse(Call<productResponse> call, Response<productResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {

                        SanPhamMoi sanPhamMoi = response.body().getProduct();

                        Glide.with(context)
                                .load(sanPhamMoi.getHinhanh())
                                .placeholder(R.drawable.loading)
                                .error(R.drawable.error)
                                .into(holder.itemgh_img);

                        holder.item_tensp.setText(sanPhamMoi.getTensp());

                        holder.item_giasp.setText("Giá: " + sanPhamMoi.getGiasp() + " Đ");

                        holder.item_soluong.setText("x" + cart.getQuantity());
                    }
                }
            }

            @Override
            public void onFailure(Call<productResponse> call, Throwable t) {
                Log.d("API_RESPONSE_Product", "API call failed: " + t.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList != null ? cartList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView itemgh_img;
        TextView item_tensp, item_giasp, item_soluong;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ các view trong item_giohang.xml
            itemgh_img = itemView.findViewById(R.id.item_hinhanh);
            item_tensp = itemView.findViewById(R.id.item_tensp);
            item_giasp = itemView.findViewById(R.id.item_gia);
            item_soluong = itemView.findViewById(R.id.item_soluong);
        }
    }
}
