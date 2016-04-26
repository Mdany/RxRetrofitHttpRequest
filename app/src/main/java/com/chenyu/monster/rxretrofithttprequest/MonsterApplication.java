package com.chenyu.monster.rxretrofithttprequest;

import android.app.Application;

/**
 * Created by chenyu on 16/4/26.
 */
public class MonsterApplication extends Application {
    public static MonsterApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static MonsterApplication getApplication() {
        return application;
    }
}
