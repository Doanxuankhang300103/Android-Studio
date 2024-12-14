package com.example.btl_appbanhang.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.btl_appbanhang.Adapter.DienthoaiAdapter;
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

public class DienThoaiActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerview;
    private ApiBanHang apiBanHang;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int page = 1;
    private int loai;
    private DienthoaiAdapter adapterdt;
    private List<SanPhamMoi> sanPhamMoiList;
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);  // Nếu EdgeToEdge là thư viện liên quan đến UI
        setContentView(R.layout.activity_dien_thoai);

        // Khởi tạo API
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        // Lấy loại sản phẩm từ Intent
        loai = getIntent().getIntExtra("loai", 1);

        // Ánh xạ các view và thiết lập Toolbar
        Anhxa();
        ActionToolBar();

        // Lấy dữ liệu từ API
        getData(page);
        // progressbar
        addEventLoading();

        // Thiết lập WindowInsets để hỗ trợ layout toàn màn hình
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    //progressbar
    private void addEventLoading() {
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isLoading == false) {
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == sanPhamMoiList.size()-1){
                        isLoading = true;
                        loadMore();
                    }
                }
            }

            private void loadMore() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //them null
                        sanPhamMoiList.add(null);
                        adapterdt.notifyItemInserted(sanPhamMoiList.size()-1);
                    }
                });
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //loai bo null
                        sanPhamMoiList.remove(sanPhamMoiList.size()-1);
                        adapterdt.notifyItemRemoved(sanPhamMoiList.size());
                        page = page+1;
                        getData(page);
                        adapterdt.notifyDataSetChanged();
                        isLoading = false;
                    }
                }, 2000);
            }
        });
    }

    private void getData(int page) {
        if (apiBanHang != null) {
            compositeDisposable.add(apiBanHang.getSanPham(page, loai)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            sanPhamMoiModel -> {
                                if (sanPhamMoiModel.isSuccess()) {
                                    if (adapterdt == null) {
                                        sanPhamMoiList = sanPhamMoiModel.getResult();
                                        adapterdt = new DienthoaiAdapter(this, sanPhamMoiList);
                                        recyclerview.setAdapter(adapterdt);
                                    }else {
                                        int vitri = sanPhamMoiList.size()-1;
                                        int soluongadd = sanPhamMoiModel.getResult().size();
                                        for (int i= 0 ; i<soluongadd; i++){
                                            sanPhamMoiList.add(sanPhamMoiModel.getResult().get(i));
                                        }
                                        adapterdt.notifyItemRangeInserted(vitri, soluongadd);

                                    }
                                }else {
                                    Toast.makeText(getApplicationContext(), "Hết dữ liệu rồi bạn ơi", Toast.LENGTH_SHORT).show();

                                }
                            },
                            throwable -> {
                                Log.e("Lỗi API", throwable.getMessage(), throwable);
                                Toast.makeText(this, "Không kết nối được tới Server!", Toast.LENGTH_SHORT).show();
                            }
                    )
            );
        }
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
        toolbar = findViewById(R.id.toolbardt);
        recyclerview = findViewById(R.id.recycleviewdt);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
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
