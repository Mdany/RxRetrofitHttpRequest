package com.chenyu.monster.rxretrofithttprequest.framework;

import com.chenyu.monster.rxretrofithttprequest.Entity.Subject;
import com.chenyu.monster.rxretrofithttprequest.service.MovieService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenyu on 16/4/26.
 */
public class HttpRequest {
    private final int TIME_OUT = 10;
    private Retrofit retrofit;
    private static HttpRequest httpRequest;

    public HttpRequest() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(Urls.baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static HttpRequest getInstance() {
        if (httpRequest == null) {
            httpRequest = new HttpRequest();
        }
        return httpRequest;
    }

    /**
     * 获取top250的电影
     *
     * @param subscriber 类似callback，其实是个观察者对象，根据请求的结果回调不同方法
     * @param start      开始位置
     * @param count      数目
     */
    public void getTopMovie(int start, int count, Subscriber<List<Subject>> subscriber) {
        MovieService movieService = retrofit.create(MovieService.class);
//        movieService.getTopMovie(start, count)
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
        Observable<List<Subject>> observable = movieService.getTopMovie(start, count)
                .map(new HttpResultFunc<List<Subject>>());

        observable.compose(ScheduleCompat.<List<Subject>>applyIoSchedulers()).subscribe(subscriber);
        //toSubscribe(observable, subscriber);
    }

    private <T> void toSubscribe(Observable<T> observable, Subscriber<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
