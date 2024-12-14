package Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//public class RetrofitClient {
//    private static Retrofit instance;
//    public static Retrofit getInstance(String baseUrl) {
//        if (instance == null) {
//            instance = new Retrofit.Builder()
//                    .baseUrl(baseUrl)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//                    .build();
//        }
//        return instance;
//    }
//}
public class RetrofitClient {
    private static Retrofit instance;
    private static String currentBaseUrl = "";
    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // Tăng thời gian kết nối
            .readTimeout(30, TimeUnit.SECONDS)    // Tăng thời gian đọc dữ liệu
            .writeTimeout(30, TimeUnit.SECONDS)   // Tăng thời gian ghi dữ liệu
            .build();

    public static Retrofit getInstance(String baseUrl) {
        // Tạo Retrofit mới nếu baseUrl thay đổi
        if (instance == null || !currentBaseUrl.equals(baseUrl)) {
            currentBaseUrl = baseUrl; // Lưu baseUrl hiện tại
            instance = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
        return instance;
    }
}
