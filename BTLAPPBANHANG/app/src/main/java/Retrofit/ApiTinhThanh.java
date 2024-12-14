package Retrofit;

import com.example.btl_appbanhang.Model.Province;
import com.example.btl_appbanhang.Respone.ResponseProvince;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiTinhThanh {
    @GET("api-tinhthanh/{A}/{B}.htm")
    Call<ResponseProvince<List<Province>>> getProvinces(@Path("A") String a, @Path("B") String b);
}
