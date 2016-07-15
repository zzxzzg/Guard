package com.zzxzzg.retrofitdemo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yxwang on 16/7/13.
 */
public interface MovieService {
    @GET("v2/movie/top250")
    Observable<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);
}
