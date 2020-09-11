package com.java.taotianhua.covidnews;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class COVIDNewsApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public void onCreate() {
        super.onCreate();
        COVIDNewsApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return COVIDNewsApplication.context;
    }
}
