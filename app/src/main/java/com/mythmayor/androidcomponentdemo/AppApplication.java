package com.mythmayor.androidcomponentdemo;

import android.app.Application;

import com.mythmayor.basicproject.BasicApplication;

import me.jessyan.autosize.AutoSize;

/**
 * Created by mythmayor on 2020/6/30.
 * App模块Application
 */
public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //当App中出现多进程, 并且您需要适配所有的进程, 就需要在App初始化时调用initCompatMultiProcess()
        AutoSize.initCompatMultiProcess(this);
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
