package Retrofit;

import com.example.btl_appbanhang.Model.Address;
import com.example.btl_appbanhang.Model.SanPhamMoi;
import com.example.btl_appbanhang.Respone.SanPhamMoiModel;
import com.example.btl_appbanhang.Model.User;
import com.example.btl_appbanhang.Respone.OrderRequest;
import com.example.btl_appbanhang.Respone.OrderResponse;
import com.example.btl_appbanhang.Respone.Response;
import com.example.btl_appbanhang.Respone.loginResponse;
import com.example.btl_appbanhang.Respone.productResponse;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiBanHang {

    @GET("getSanPham.php")
    Observable<SanPhamMoiModel> getSpmoi();

    // Phương thức này cần sử dụng POST vì có @FormUrlEncoded và @Field
    @FormUrlEncoded
    @POST("getProductByCategory.php")
    Observable<SanPhamMoiModel> getSanPham(
            @Field("page") int page,
            @Field("loai") int loai
    );
    @FormUrlEncoded
    @POST("postLogin.php")
    Call<loginResponse> postLogin(
            @Field("username") String username,
            @Field("password") String password
    );
    @FormUrlEncoded
    @POST("postRegister.php")
    Call<Response<User>> postRegister(
            @Field("username") String username,
            @Field("password") String password,
            @Field("email") String email
    );
    @GET("getChitietSanPham.php") // Thay đổi với đường dẫn của API
    Call<productResponse> getProductById(@Query("product_id") int productId);

    @POST("postPayment.php") // Đường dẫn đến file PHP
    Call<OrderResponse> postPayment(@Body OrderRequest request);

    @POST("postAddress.php")
    Call<Response<Address>> postAddress(@Body Address request);

    @GET("getAddressDefault.php") // Thay đổi với đường dẫn của API
    Call<Response<List<Address>>> getAddressDefault(@Query("user_id") int user_id);

    @GET("getAddressByUserID.php") // Thay đổi với đường dẫn của API
    Call<Response<List<Address>>> getAddressByUserID(@Query("user_id") int user_id);

    @PUT("putAddressIsActive.php") // Thay đổi với đường dẫn của API
    Call<Response<Address>> putAddressIsActive(@Body JsonObject id);

    @POST("postNewProduct.php") // Thay đổi với đường dẫn của API
    Call<Response<SanPhamMoi>> postNewProduct(@Body SanPhamMoi sanPhamMoi);

    @PUT("putProduct.php") // Thay đổi với đường dẫn của API
    Call<Response<SanPhamMoi>> putProduct(@Body SanPhamMoi sanPhamMoi);

    @DELETE("deleteProduct.php") // Thay đổi với đường dẫn của API
    Call<Response<SanPhamMoi>> deleteProduct(@Query("id") int sanPhamMoiID);

}
