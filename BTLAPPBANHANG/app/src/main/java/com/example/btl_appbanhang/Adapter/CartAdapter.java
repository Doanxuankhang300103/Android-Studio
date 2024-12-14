package com.example.btl_appbanhang.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private Context context;
    private List<Cart> cartList;
    private DaoCart daoCart;
    private SharedPreferences sharedPreferences;
    private ApiBanHang apiBanHang;
    private ItemClickListener itemClickListener;

    public CartAdapter(Context context, List<Cart> cartList, ItemClickListener listener) {
        this.context = context;
        this.cartList = cartList;
        daoCart = new DaoCart(context);
        this.itemClickListener = listener;
        sharedPreferences = context.getSharedPreferences("INFO", Context.MODE_PRIVATE);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
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

                        holder.itemgiohang_tensp.setText(sanPhamMoi.getTensp());

                        holder.itemgiohang_giasp.setText("Giá: " + sanPhamMoi.getGiasp() + " Đ");

                        holder.itemgiohang_soluong.setText(String.valueOf(cart.getQuantity()));
                    }
                }
            }

            @Override
            public void onFailure(Call<productResponse> call, Throwable t) {
                Log.d("API_RESPONSE_Product", "API call failed: " + t.getMessage());
            }
        });

        int totalPrice = cart.getQuantity() * Integer.valueOf(cart.getPrice());
        holder.itemgiohang_giasp2.setText("Tổng: " + decimalFormat.format(totalPrice) + " Đ");

        // Xử lý sự kiện khi nhấn vào nút xóa
        holder.delete_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDeleteCart(cart); // Gọi dialog để xác nhận xóa
            }
        });

        // Sự kiện giảm số lượng
        holder.itemgh_tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cart.getQuantity() > 1) {
                    cart.setQuantity(cart.getQuantity() - 1);

                    int totalPrice = cart.getQuantity() * cart.getPrice();
                    holder.itemgiohang_giasp2.setText("Tổng: " + decimalFormat.format(totalPrice) + " Đ");
                    daoCart.update(cart);
                    notifyDataSetChanged();
                    itemClickListener.onButtonClick();
                } else {
                    Toast.makeText(context, "Số lượng không thể nhỏ hơn 1", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Sự kiện tăng số lượng
        holder.itemgh_cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.setQuantity(cart.getQuantity() + 1);
                int totalPrice = cart.getQuantity() * cart.getPrice();
                holder.itemgiohang_giasp2.setText("Tổng: " + decimalFormat.format(totalPrice) + " Đ");
                daoCart.update(cart);
                notifyDataSetChanged();
                itemClickListener.onButtonClick();
            }
        });
    }

    private void DialogDeleteCart(Cart cart) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.warning);
        builder.setCancelable(false);
        builder.setTitle("Xóa sản phẩm khỏi giỏ hàng");
        builder.setMessage("Bạn có muốn xóa không ?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // bắt sự kiện nhấn nút Yes
                if (daoCart.delete(cart.getId())) {
                    cartList.clear();
                    cartList.addAll(daoCart.selectCartByIdUser(String.valueOf(sharedPreferences.getInt("id", 0))));
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    itemClickListener.onButtonClick();
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // bắt sự kiện nhấn nút No
                dialog.dismiss();
            }
        });
        builder.show();
    }
    @Override
    public int getItemCount() {
        return cartList != null ? cartList.size() : 0;
    }

    // Lớp ViewHolder để quản lý các view trong item giỏ hàng
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView itemgh_img, itemgh_tru, itemgh_cong;
        TextView itemgiohang_tensp, itemgiohang_giasp, itemgiohang_soluong, itemgiohang_giasp2;
        ImageButton delete_cart;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ các view trong item_giohang.xml
            itemgh_img = itemView.findViewById(R.id.itemgh_hinhanh);
            itemgiohang_tensp = itemView.findViewById(R.id.itemgh_tensp);
            itemgiohang_giasp = itemView.findViewById(R.id.itemgh_gia);
            itemgiohang_giasp2 = itemView.findViewById(R.id.itemgh_gia2);
            itemgiohang_soluong = itemView.findViewById(R.id.itemgh_soluong);
            delete_cart = itemView.findViewById(R.id.delete_cart);
            itemgh_cong = itemView.findViewById(R.id.itemgh_cong);
            itemgh_tru = itemView.findViewById(R.id.itemgh_tru);
        }
    }
}
