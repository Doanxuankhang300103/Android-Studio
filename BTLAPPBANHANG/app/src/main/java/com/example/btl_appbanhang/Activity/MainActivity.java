package com.example.btl_appbanhang.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_appbanhang.Adapter.LoaiSpAdapter;
import com.example.btl_appbanhang.Adapter.SanPhamMoiAdapter;
import com.example.btl_appbanhang.Model.LoaiSp;
import com.example.btl_appbanhang.R;
import com.example.btl_appbanhang.Model.SanPhamMoi;
import com.example.btl_appbanhang.utils.Utils;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import Retrofit.ApiBanHang;
import Retrofit.RetrofitClient;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbarhome;
    private ViewFlipper viewflipperhome;
    private RecyclerView recycleviewhome;
    private NavigationView navigationviewhome;
    private ListView listviewhome;
    private DrawerLayout drawerLayout;
    private LoaiSpAdapter loaiSpAdapter;
    private List<LoaiSp> mangloaisp = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiBanHang apiBanHang;
    private List<SanPhamMoi> mangspmoi = new ArrayList<>();
    private SanPhamMoiAdapter spmoiAdapter;
    private NotificationBadge badge;
    NavigationView navigationView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo API
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        // Ánh xạ các thành phần giao diện và thiết lập ActionBar
        Anhxa();
        Actionbar();

        // Kiểm tra kết nối internet và tải dữ liệu nếu có mạng
        if (isConnected(this)) {
            ActionViewFlipper();
//            getLoaiSanpham();
            getSpmoi();
//            getEventClick();
        } else {
            Toast.makeText(getApplicationContext(), "KHÔNG CÓ KẾT NỐI INTERNET", Toast.LENGTH_LONG).show();
        }
    }

    // Xử lý sự kiện khi click vào menu
//    private void getEventClick() {
////        listviewhome.setOnItemClickListener((adapterView, view, position, id) -> {
////            Intent intent;
////            switch (position) {
////                case 0:
////                    intent = new Intent(MainActivity.this, MainActivity.class);
////                    break;
////                case 1:
////                    intent = new Intent(MainActivity.this, DienThoaiActivity.class);
////                    intent.putExtra("loai", 1);  // Truyền thêm dữ liệu vào DienThoaiActivity
////                    break;
////                case 2:
////                    intent = new Intent(MainActivity.this, TabletActivity.class);
////                    intent.putExtra("loai", 2);  // Truyền thêm dữ liệu vào TabletActivity
////                    break;
////                case 3:
////                    intent = new Intent(MainActivity.this, ThongtinActivity.class);
////                    break;
////                case 4:
////                    intent = new Intent(MainActivity.this, LienheActivity.class);
////                    break;
////                default:
////                    return;
////            }
////            startActivity(intent);  // Bắt đầu Activity tương ứng
////        });
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.cart) {
            startActivity(new Intent(MainActivity.this, GioHangActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    // Lấy danh sách sản phẩm mới từ API
    private void getSpmoi() {
        compositeDisposable.add(apiBanHang.getSpmoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if (sanPhamMoiModel.isSuccess()) {
                                // Thiết lập Adapter cho RecyclerView
                                mangspmoi = sanPhamMoiModel.getResult();
                                spmoiAdapter = new SanPhamMoiAdapter(getApplicationContext(), mangspmoi);
                                recycleviewhome.setAdapter(spmoiAdapter);
                                spmoiAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getApplicationContext(), "Không lấy được sản phẩm mới", Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "KHÔNG KẾT NỐI ĐƯỢC TỚI CƠ SỞ DỮ LIỆU: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("API_ERROR", throwable.getMessage());
                        }
                ));
    }

    // Ánh xạ các thành phần giao diện
    private void Anhxa() {
        sharedPreferences = getSharedPreferences("INFO", Context.MODE_PRIVATE);
        toolbarhome = findViewById(R.id.toolbartrangchu);
        viewflipperhome = findViewById(R.id.viewflippertrangchu);
        recycleviewhome = findViewById(R.id.recycleviewtrangchu);
        recycleviewhome.setLayoutManager(new GridLayoutManager(this, 2));
        recycleviewhome.setHasFixedSize(true);
//        navigationviewhome = findViewById(R.id.navigationviewtrangchu);
//        listviewhome = findViewById(R.id.listviewtrangchu);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationView);
//        loaiSpAdapter = new LoaiSpAdapter(getApplicationContext(), mangloaisp);
//        listviewhome.setAdapter(loaiSpAdapter);
        //

    }

    // Lấy danh sách loại sản phẩm từ API
//    private void getLoaiSanpham() {
//        compositeDisposable.add(apiBanHang.getLoaiSp()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        loaiSpModel -> {
//                            if (loaiSpModel.isSuccess()) {
//                                mangloaisp = loaiSpModel.getResult();
//                                loaiSpAdapter = new LoaiSpAdapter(MainActivity.this, mangloaisp);
//                                listviewhome.setAdapter(loaiSpAdapter);
//                                loaiSpAdapter.notifyDataSetChanged();
//                            } else {
//                                Log.e("API_ERROR", "Lấy dữ liệu loại SP không thành công: " + loaiSpModel.getMessage());
//                            }
//                        },
//                        throwable -> {
//                            Log.e("API_ERROR", "Lỗi khi gọi API: " + throwable.getMessage());
//                        }
//                ));
//    }

    // Thiết lập ViewFlipper cho quảng cáo
    private void ActionViewFlipper() {
        int[] mangquangcao = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};

        for (int imageResource : mangquangcao) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setImageResource(imageResource);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewflipperhome.addView(imageView);
        }

        viewflipperhome.setFlipInterval(3000);
        viewflipperhome.setAutoStart(true);

        Animation inAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation outAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewflipperhome.setInAnimation(inAnimation);
        viewflipperhome.setOutAnimation(outAnimation);
    }

    // Thiết lập ActionBar
    private void Actionbar() {
        setSupportActionBar(toolbarhome);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//            toolbarhome.setNavigationIcon(R.drawable.listic2);
//
//            toolbarhome.setNavigationOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));
//        } else {
//            Log.e("ActionBarError", "Action bar is not available");
//        }
        toolbarhome.setNavigationIcon(R.drawable.menunav);
        toolbarhome.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_tranhchu) {
                    intentActivity(MainActivity.class);
                } else if (item.getItemId() == R.id.nav_dienthoai) {
                    intentActivity(DienThoaiActivity.class);
                } else if (item.getItemId() == R.id.nav_maytinhbang) {
                    intentActivity(TabletActivity.class);
                } else if (item.getItemId() == R.id.nav_thongtin) {
                    intentActivity(ThongtinActivity.class);
                } else if (item.getItemId() == R.id.nav_lienhe) {
                    intentActivity(LienheActivity.class);
                } else if (item.getItemId() == R.id.nav_dangxuat) {
                    OpenDialog_DangXuat();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }
    private void intentActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(MainActivity.this, activityClass);
        startActivity(intent);
    }

    public void OpenDialog_DangXuat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.warning);
        builder.setTitle("WARNING");
        builder.setMessage("Bạn có muốn đăng xuất không ?");
        builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                sharedPreferences.edit().clear().apply();  // Xóa thông tin đăng nhập
                finish();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }
    // Kiểm tra kết nối internet
    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();  // Xóa tất cả subscription để tránh rò rỉ bộ nhớ
        super.onDestroy();
    }
}

