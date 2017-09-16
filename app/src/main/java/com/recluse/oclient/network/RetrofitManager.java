package com.recluse.oclient.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    public static final String BASE_URL = "http://c.open.163.com";

    private static Map<String, Retrofit> sRetrofitMap = new HashMap<>();

    public static Retrofit getRetrofit(String baseUrl) {
        Retrofit retrofit = sRetrofitMap.get(baseUrl);
        if (retrofit == null) {
            retrofit = createRetrofit(baseUrl);
            sRetrofitMap.put(baseUrl, retrofit);
        }
        return retrofit;
    }
    public static Retrofit createRetrofit(String baseUrl) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
