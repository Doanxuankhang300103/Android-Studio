package com.example.btl_appbanhang.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.btl_appbanhang.R;

public class LienheActivity extends AppCompatActivity {

    private TextView tvEmail, tvPhone, tvFacebook;
    private Button btnFacebook, btnYoutube, btnEmail, btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lienhe);

        // Ánh xạ các view
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvFacebook = findViewById(R.id.tvFacebook);

        btnFacebook = findViewById(R.id.btnFacebook);
        btnYoutube = findViewById(R.id.btnYoutube);
        btnEmail = findViewById(R.id.btnEmail);
        btnCall = findViewById(R.id.btnCall);

        Toolbar toolbar = findViewById(R.id.toolbarlh);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Hiện nút quay lại
        getSupportActionBar().setHomeButtonEnabled(true);

        // Thiết lập sự kiện click cho các button
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSocialMedia("https://www.facebook.com/xuankhangmobi");
            }
        });

        btnYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSocialMedia("https://www.youtube.com/channel/your_channel"); // Thay đổi đường dẫn
            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail("xkmobile@gmail.com"); // Địa chỉ email
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall("+84363186833"); // Số điện thoại
            }
        });
    }

    // Mở trang mạng xã hội
    private void openSocialMedia(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    // Gửi email
    private void sendEmail(String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + email));
        startActivity(Intent.createChooser(emailIntent, "Gửi Email"));
    }

    // Gọi điện thoại
    private void makePhoneCall(String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
    }
    public boolean onSupportNavigateUp() {
        onBackPressed(); // Quay lại khi nhấn nút quay lại
        return true;
    }
}
