package com.cspl.tourtravelapps.RetrofitAPI;

import com.cspl.tourtravelapps.network.AMZEExchnageRateResponse;
import com.cspl.tourtravelapps.network.AMZERateResponse;
import com.cspl.tourtravelapps.network.AddCartRequest;
import com.cspl.tourtravelapps.network.AddCartResponse;
import com.cspl.tourtravelapps.network.CityResponse;
import com.cspl.tourtravelapps.network.CountryResponse;
import com.cspl.tourtravelapps.network.LoginRequest;
import com.cspl.tourtravelapps.network.LoginResponse;
import com.cspl.tourtravelapps.network.OrderRequest;
import com.cspl.tourtravelapps.network.OrderResponse;
import com.cspl.tourtravelapps.network.PackageCategoryResponse;
import com.cspl.tourtravelapps.network.PackageRequest;
import com.cspl.tourtravelapps.network.PackageResponse;
import com.cspl.tourtravelapps.network.ReviewRequest;
import com.cspl.tourtravelapps.network.ReviewResponse;
import com.cspl.tourtravelapps.network.SignUpRequest;
import com.cspl.tourtravelapps.network.SignUpResponse;
import com.cspl.tourtravelapps.network.SupportRequest;
import com.cspl.tourtravelapps.network.SupportResponse;
import com.cspl.tourtravelapps.network.THBToUSDRateResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by HP on 07/31/2018.
 */

public interface ApiInterface {

    @POST("api/AmzThai/userLogin")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("api/AmzThai/getCityList")
    Call<CityResponse> getCity();

    @GET("api/AmzThai/getCountryList")
    Call<CountryResponse> getCountry();

    @POST("api/AmzThai/getPackageDetails")
    Call<PackageResponse> getPackageDetails(@Body PackageRequest packageRequest);

    @POST("api/AmzThai/getPackageCategoryDetails")
    Call<PackageCategoryResponse> getPackageCategoryDetails(@Body PackageRequest packageRequest);

    @POST("api/AmzThai/userRegistration")
    Call<SignUpResponse> signup(@Body SignUpRequest signUpRequest);

    @POST("api/AmzThai/AddsupportDetails")
    Call<SupportResponse> addSupportDetails(@Body SupportRequest supportRequest);

    @POST("api/AmzThai/AddToCartItem")
    Call<AddCartResponse> addtoCart(@Body AddCartRequest addCartRequest);

    @POST("api/AmzThai/AddToCartItem")
    Call<AddCartResponse> getCart(@Body AddCartRequest addCartRequest);

    @POST("api/AmzThai/AddToCartItem")
    Call<AddCartResponse> deleteCart(@Body AddCartRequest addCartRequest);

    @POST("api/AmzThai/AddOrderItem")
    Call<OrderResponse> addOrder(@Body List<OrderRequest> orderRequestList);

    @POST("api/AmzThai/getOrderItem")
    Call<OrderResponse> getOrder(@Body OrderRequest orderRequest);

    @POST("api/AmzThai/getTripFeedBackData")
    Call<ReviewResponse> getReviews(@Body ReviewRequest reviewRequest);

//    @GET("spot/v1/ticker?symbol=AVAX_USDT")
//    Call<AMZEExchnageRateResponse> getAMZERate();

    @GET("simple/price?ids=the-amaze-world&vs_currencies=thb")
    Call<AMZERateResponse> getAMZERate();

//    @GET("live/?api_key=9113a4b8b69d415c95306cf35d6a8350&base=THB&target=USD")
//    Call<THBToUSDRateResponse> getTHB_USDRate();
}
