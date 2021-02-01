package com.mythmayor.androidcomponentdemo;

import android.app.Application;

import com.mythmayor.basicproject.BasicApplication;

/**
 * Created by mythmayor on 2020/6/30.
 * App模块Application
 */
public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BasicApplication.getInstance().init(this);
    }

    /**
     * 退出应用程序
     */
    public void quitApplication() {
        BasicApplication.getInstance().clearAllActivity();
        System.exit(0);
    }
}
