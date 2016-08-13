package com.zzxzzg.retrofitdemo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
        httpClientBuilder.addInterceptor(new Interceptor(){
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original=chain.request();
                //add common params
                Request.Builder requestBuilder = original.newBuilder();
                if(original.body() instanceof FormBody){
                    FormBody.Builder newFormBody = new FormBody.Builder();
                    FormBody oidFormBody = (FormBody) original.body();
                    //add old params
                    for (int i = 0;i<oidFormBody.size();i++){
                        newFormBody.addEncoded(oidFormBody.encodedName(i),oidFormBody.encodedValue(i));
                    }
                    //add new params
                    //newFormBody.add()

                    requestBuilder.method(original.method(),newFormBody.build());
                }
                Request request = requestBuilder.build();
                Response response = chain.proceed(request);
                return response;
            }
        });

        mRetrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(mBaseUrl)
                .build();

    }

}
