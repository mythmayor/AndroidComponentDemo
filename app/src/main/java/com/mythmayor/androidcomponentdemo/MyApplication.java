package com.mythmayor.androidcomponentdemo;

import android.app.Application;

import androidx.multidex.MultiDex;

import com.mythmayor.basicproject.BasicProjectApplication;

import me.jessyan.autosize.AutoSize;

/**
 * Created by mythmayor on 2020/6/30.
 * 全局Application
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //当App中出现多进程, 并且您需要适配所有的进程, 就需要在App初始化时调用initCompatMultiProcess()
        AutoSize.initCompatMultiProcess(this);
        //解决方法数超过65535的报错问题
        MultiDex.install(this);
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
