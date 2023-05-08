package com.school.uurun.base;

import android.app.Application;
import android.content.Context;

/**
 * 自定义类BaseApplication继承类Application主要实现全局Context共享
 */
public class BaseApplication extends Application {
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getBaseContext();
    }


    public static Context getAppContext() {
        return appContext;
    }
}
