package com.chenyu.monster.rxretrofithttprequest.framework;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenyu on 16/4/27.
 *
 * compose 组件替换对Observable 反复subscribe的重复动作,也避免封装的命运以符合rx的特性
 */
public class ScheduleCompat {
    private static final Observable.Transformer ioTransformer = new Observable.Transformer() {
        @Override
        public Object call(Object o) {
            return ((Observable) o).subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    public static <T> Observable.Transformer<T,T> applyIoSchedulers(){
        return (Observable.Transformer<T, T>) ioTransformer;
    }
}
