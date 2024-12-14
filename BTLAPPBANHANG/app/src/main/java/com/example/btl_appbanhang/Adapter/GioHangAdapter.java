package com.example.btl_appbanhang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.btl_appbanhang.Model.GioHang;
import com.example.btl_appbanhang.R;
import com.example.btl_appbanhang.utils.Utils;

import java.text.DecimalFormat;
import java.util.List;

import Retrofit.ApiBanHang;
import Retrofit.RetrofitClient;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {
    private Context context;
    private List<GioHang> gioHangList;
    private ApiBanHang apiBanHang;

    // Constructor nhận context và danh sách giỏ hàng
    public GioHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
//        apiBanHang.getProductById()
    }

    // Tạo ViewHolder khi RecyclerView yêu cầu
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout item giỏ hàng
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent, false);
        return new MyViewHolder(view);
    }

    // Gán dữ liệu cho ViewHolder
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GioHang gioHang = gioHangList.get(position);
        //
//        apiBanHang.getProductById()
        // Gán tên sản phẩm và số lượng
        holder.itemgiohang_tensp.setText(gioHang.getTensp());
        holder.itemgiohang_soluong.setText(String.valueOf(gioHang.getSoluong())+ " ");

        // Sử dụng Glide để tải hình ảnh sản phẩm
        Glide.with(context)
                .load(gioHang.getHinhanh())
                .placeholder(R.drawable.loading)  // Ảnh tạm thời khi đang tải
                .error(R.drawable.error)          // Ảnh lỗi khi tải không thành công
                .into(holder.itemgh_img);

        // Định dạng giá sản phẩm
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.itemgiohang_giasp.setText(decimalFormat.format(gioHang.getGiasp()) + " Đ");

        // Tính tổng giá theo số lượng
        long giaTong = gioHang.getSoluong() * gioHang.getGiasp();
        holder.itemgiohang_giasp2.setText(decimalFormat.format(giaTong) + " Đ");
    }

    // Trả về số lượng item trong giỏ hàng
    @Override
    public int getItemCount() {
        return gioHangList != null ? gioHangList.size() : 0;
    }

    // Lớp ViewHolder để quản lý các view trong item giỏ hàng
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView itemgh_img;
        TextView itemgiohang_tensp, itemgiohang_giasp, itemgiohang_soluong, itemgiohang_giasp2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // Ánh xạ các view trong item_giohang.xml
            itemgh_img = itemView.findViewById(R.id.itemgh_hinhanh);
            itemgiohang_tensp = itemView.findViewById(R.id.itemgh_tensp);
            itemgiohang_giasp = itemView.findViewById(R.id.itemgh_gia);
            itemgiohang_giasp2 = itemView.findViewById(R.id.itemgh_gia2);
            itemgiohang_soluong = itemView.findViewById(R.id.itemgh_soluong);
        }
    }
}
