package com.cspl.tourtravelapps.RetrofitAPI;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HP on 07/31/2018.
 */

public class ApiClient {

    public static final String BASE_URL = "https://csplserver.in/AmazingThailandAPI/";
//    public static final String EXCHANGE_URL = "https://api-cloud.bitmart.com/";
    public static final String EXCHANGE_URL = "https://api.coingecko.com/api/v3/";
//    public static final String CURRENCY_URL = "https://exchange-rates.abstractapi.com/v1/";

    private static Retrofit retrofit = null;
    private static Retrofit retrofitExchange = null;
//    private static Retrofit retrofitCurrency = null;

    public static Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getRequestHeader())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClientExchange(){
        if(retrofitExchange == null){
            retrofitExchange = new Retrofit.Builder()
                    .baseUrl(EXCHANGE_URL)
                    .client(getRequestHeader())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitExchange;
    }

//    public static Retrofit getClientCurrency(){
//        if(retrofitCurrency == null){
//            retrofitCurrency = new Retrofit.Builder()
//                    .baseUrl(CURRENCY_URL)
//                    .client(getRequestHeader())
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }
//        return retrofitCurrency;
//    }

    private static OkHttpClient getRequestHeader() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }
}
