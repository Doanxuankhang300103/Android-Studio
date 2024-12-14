package com.example.btl_appbanhang.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_appbanhang.Adapter.TabletAdapter;
import com.example.btl_appbanhang.R;
import com.example.btl_appbanhang.Model.SanPhamMoi;
import com.example.btl_appbanhang.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import Retrofit.ApiBanHang;
import Retrofit.RetrofitClient;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TabletActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerview;
    private ApiBanHang apiBanHang;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int page = 1;
    private int loai;
    private TabletAdapter adaptertb;
    private List<SanPhamMoi> sanPhamMoiList;
    private LinearLayoutManager linearLayoutManager;
    private Handler handler = new Handler();
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablet);

        // Khởi tạo API
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        // Lấy loại sản phẩm từ Intent
        loai = getIntent().getIntExtra("loai", 2);

        // Ánh xạ các view và thiết lập Toolbar
        Anhxa();
        ActionToolBar();

        // Lấy dữ liệu từ API
        getData(page);

        // Thêm sự kiện cuộn để tải thêm dữ liệu
        addEventLoading();

        // Thiết lập WindowInsets để hỗ trợ layout toàn màn hình
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Xử lý khi cuộn đến cuối danh sách, thêm ProgressBar và tải thêm dữ liệu
    private void addEventLoading() {
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLoading && linearLayoutManager.findLastCompletelyVisibleItemPosition() == sanPhamMoiList.size() - 1) {
                    isLoading = true;
                    loadMore();
                }
            }

            private void loadMore() {
                // Thêm phần tử null để hiển thị ProgressBar
                sanPhamMoiList.add(null);
                adaptertb.notifyItemInserted(sanPhamMoiList.size() - 1);

                handler.postDelayed(() -> {
                    // Loại bỏ phần tử null và tải thêm dữ liệu
                    sanPhamMoiList.remove(sanPhamMoiList.size() - 1);
                    adaptertb.notifyItemRemoved(sanPhamMoiList.size());

                    page++;
                    getData(page);
                    isLoading = false;
                }, 2000);
            }
        });
    }

    // Lấy dữ liệu từ API
    private void getData(int page) {
        compositeDisposable.add(apiBanHang.getSanPham(page, loai)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if (sanPhamMoiModel.isSuccess()) {
                                if (adaptertb == null) {
                                    sanPhamMoiList = sanPhamMoiModel.getResult();
                                    adaptertb = new TabletAdapter(this, sanPhamMoiList);
                                    recyclerview.setAdapter(adaptertb);
                                } else {
                                    int positionStart = sanPhamMoiList.size();
                                    sanPhamMoiList.addAll(sanPhamMoiModel.getResult());
                                    adaptertb.notifyItemRangeInserted(positionStart, sanPhamMoiModel.getResult().size());
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Hết dữ liệu rồi bạn ơi", Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Log.e("Lỗi API", throwable.getMessage(), throwable);
                            Toast.makeText(this, "Không kết nối được tới Server!", Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    // Thiết lập Toolbar với nút quay lại
    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    // Ánh xạ các thành phần giao diện và thiết lập RecyclerView
    private void Anhxa() {
        toolbar = findViewById(R.id.toolbartb);
        recyclerview = findViewById(R.id.recycleviewtb);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setHasFixedSize(true);
        sanPhamMoiList = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
