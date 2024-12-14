package com.example.btl_appbanhang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.btl_appbanhang.Model.LoaiSp;
import com.example.btl_appbanhang.R;

import java.util.List;

public class LoaiSpAdapter extends BaseAdapter {
    List<LoaiSp> array;
    Context context;

    public LoaiSpAdapter(Context context, List<LoaiSp> array) {
        this.context = context;
        this.array = array;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int i) {
        return array.get(i);  // Trả về phần tử tại vị trí i
    }

    @Override
    public long getItemId(int i) {
        return i;  // Trả về vị trí i như là ID
    }

    public class ViewHolder {
        TextView texttensp;
        ImageView imgsp;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        // Sử dụng ViewHolder để tối ưu hóa việc tái sử dụng View
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_sanpham, null);
            viewHolder.texttensp = view.findViewById(R.id.item_tensanpham);
            viewHolder.imgsp = view.findViewById(R.id.item_image);
            view.setTag(viewHolder);  // Lưu ViewHolder vào tag của View
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        // Thiết lập dữ liệu cho item
        viewHolder.texttensp.setText(array.get(i).getTensanpham());

        // Sử dụng Glide để load ảnh với kích thước override
        Glide.with(context)
                .load(array.get(i).getHinhanh())
                .override(150, 150)  // Kích thước của hình ảnh (không phải ImageView)
                .into(viewHolder.imgsp);

        return view;
    }
}
