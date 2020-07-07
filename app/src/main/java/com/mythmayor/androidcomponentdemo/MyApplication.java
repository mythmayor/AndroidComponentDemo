package com.mythmayor.androidcomponentdemo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Process;

import com.mythmayor.basicproject.BasicProjectApplication;
import com.mythmayor.basicproject.base.BaseActivity;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

/**
 * Created by mythmayor on 2020/6/30.
 * 全局Application
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BasicProjectApplication.getInstance().init(this);
    }

    /**
     * 退出应用程序
     */
    public void quitApplication() {
        BasicProjectApplication.getInstance().clearAllActivity();
        System.exit(0);
    }
}
