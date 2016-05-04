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
import rx.functions.Func1;

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
    public void getTopMovie(int start, int count, Subscriber<Subject> subscriber) {
        MovieService movieService = retrofit.create(MovieService.class);
//compose去除重复动作
//        movieService.getTopMovie(start, count)
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
        movieService.getTopMovie(start, count)
                .flatMap(new Func1<HttpResult<List<Subject>>, Observable<List<Subject>>>() {
                    @Override
                    public Observable<List<Subject>> call(HttpResult<List<Subject>> httpResult) {
                        if (httpResult.getCount() != 0) {
                            return Observable.error(new ApiException(100));
                        } else {
                            return Observable.just(httpResult.getSubjects());
                        }
                    }
                })
                .flatMap(new Func1<List<Subject>, Observable<Subject>>() {
                    @Override
                    public Observable<Subject> call(List<Subject> subjects) {
                        return Observable.from(subjects);
                    }
                })
                .compose(ScheduleCompat.<Subject>applyIoSchedulers())
                .subscribe(subscriber);
//统一处理result code情况，这里是替代了HttpResultFunc，已Observable.flatMap替代，内部交由OnError方法中断接下来的调用，并显示自定义错误信息
//                .map(new Func1<HttpResult<List<Subject>>, List<Subject>>() {
//                    @Override
//                    public List<Subject> call(HttpResult<List<Subject>> httpResult) {
//                        return httpResult.getSubjects();
//                    }
//                })
//                .map(new HttpResultFunc<List<Subject>>());
//toSubscribe(observable, subscriber);
    }
//
//    private <T> void toSubscribe(Observable<T> observable, Subscriber<T> subscriber) {
//        observable.subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
//    }
}
