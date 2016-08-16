package com.zzxzzg.retrofitdemo;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yxwang on 16/8/15.
 */
public enum PostJsonRetrofit implements IHttpMethods{
    INSTANCE;

    private static MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    private String mBaseUrl;
    private Retrofit mRetrofit;
    private OkHttpClient mClient;

    PostJsonRetrofit(){
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(new Interceptor(){
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original=chain.request();

                //add common params
                Request.Builder requestBuilder = original.newBuilder();
                if(original.body() instanceof FormBody){

                    TreeMap<String,Object> params=new TreeMap<>();

                    FormBody oidFormBody = (FormBody) original.body();
                    //add old params
                    for (int i = 0;i<oidFormBody.size();i++){
                        params.put(oidFormBody.encodedName(i),oidFormBody.encodedValue(i));
                    }

                    //add new params
                    //params.put(...);


                    Gson gson=new Gson();
                    RequestBody body1=RequestBody.create(JSON_MEDIA_TYPE,gson.toJson(params));
                    requestBuilder.post(body1);
                }
                Request request = requestBuilder.build();
                Response response = chain.proceed(request);
                return response;
            }
        });
        mClient=httpClientBuilder.build();
    }

    public Retrofit getRetrofit(String baseUrl){
        if(baseUrl==mBaseUrl && mRetrofit!=null){
            return mRetrofit;
        }
        mBaseUrl = baseUrl;
        mRetrofit = new Retrofit.Builder()
                .client(mClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(mBaseUrl)
                .build();
        return mRetrofit;
    }

}
