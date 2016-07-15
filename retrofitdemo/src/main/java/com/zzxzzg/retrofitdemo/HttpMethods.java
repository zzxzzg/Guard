package com.zzxzzg.retrofitdemo;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yxwang on 16/7/13.
 */
public enum HttpMethods {
    INSTANCE;

    private static final int DEFAULT_TIMEOUT = 5;

    private boolean hasInitialized=false;
    private String mBaseUrl;

    private Retrofit mRetrofit;

    private void initialize(String baseUrl){
        hasInitialized=true;
        mBaseUrl=baseUrl;

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        mRetrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(mBaseUrl)
                .build();

    }

}
