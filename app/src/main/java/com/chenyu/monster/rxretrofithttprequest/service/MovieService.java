package com.chenyu.monster.rxretrofithttprequest.service;

import com.chenyu.monster.rxretrofithttprequest.Entity.Subject;
import com.chenyu.monster.rxretrofithttprequest.framework.HttpResult;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by chenyu on 16/4/26.
 */
public interface MovieService {
    @GET("top250")
    Observable<Response<HttpResult<List<Subject>>>> getTopMovie(@Query("start") int start, @Query("count") int count);
}
