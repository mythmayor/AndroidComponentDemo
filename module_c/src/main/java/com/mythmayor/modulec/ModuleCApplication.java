package com.mythmayor.modulec;

import android.app.Application;

import com.mythmayor.basicproject.BasicApplication;

import me.jessyan.autosize.AutoSize;

/**
 * Created by mythmayor on 2021/1/29.
 * module_c模块Application
 * 如果是作为依赖库则需要在<application>中配置(android:name=".ModuleCApplication")；
 * 如果是作为单独的应用程序则需要取消<application-name>的配置。
 */
public class ModuleCApplication extends Application {

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
