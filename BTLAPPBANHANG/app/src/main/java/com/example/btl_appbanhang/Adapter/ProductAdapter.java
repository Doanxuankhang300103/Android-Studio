package com.example.btl_appbanhang.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.btl_appbanhang.Activity.AddProduct;
import com.example.btl_appbanhang.Interface.ItemClickListener;
import com.example.btl_appbanhang.Model.SanPhamMoi;
import com.example.btl_appbanhang.R;
import com.example.btl_appbanhang.Respone.Response;
import com.example.btl_appbanhang.utils.Utils;

import java.util.ArrayList;

import Retrofit.ApiBanHang;
import Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    Context context;
    ArrayList<SanPhamMoi> list;
    int maTheLoai;
    private ItemClickListener itemClickListener;
    public ProductAdapter(Context context, ArrayList<SanPhamMoi> list, ItemClickListener itemClickListener) {
        this.context = context;
        this.list = list;
        this.itemClickListener = itemClickListener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SanPhamMoi sanPhamMoi = list.get(position);
        holder.tensp.setText(sanPhamMoi.getTensp());
        holder.giasp.setText(sanPhamMoi.getGiasp());
        holder.mota.setText(sanPhamMoi.getMota());
        // Gán ảnh cho ImageView
        Glide.with(context).load(sanPhamMoi.getHinhanh()).into(((MyViewHolder) holder).hinhanh);
        // Xử lý sự kiện cho nút sửa và xóa
        holder.btn_edit.setOnClickListener(v -> {
            itemClickListener.clickProductAdapter(sanPhamMoi);
        });
        holder.btn_delete.setOnClickListener(v -> {
            itemClickListener.clickDeleteProductAdapter(sanPhamMoi);
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageButton btn_edit, btn_delete;
        TextView tensp, giasp, mota;
        ImageView hinhanh;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ các view trong item_giohang.xml
            btn_edit = itemView.findViewById(R.id.btn_edit);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            tensp = itemView.findViewById(R.id.itemdt_ten);
            giasp = itemView.findViewById(R.id.itemdt_gia);
            mota = itemView.findViewById(R.id.itemdt_mota);
            hinhanh = itemView.findViewById(R.id.itemdt_hinhanh);
        }
    }
}
