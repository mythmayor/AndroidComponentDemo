package com.mythmayor.basicproject.base;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;

import com.mythmayor.basicproject.adapter.FullLifecycleObserver;
import com.mythmayor.basicproject.adapter.FullLifecycleObserverAdapter;

/**
 * Created by mythmayor on 2020/6/30.
 * 自定义Handler，实现LifecycleObserver进行生命周期监听，会自动在onDestroy里移除消息，避免产生内存泄漏
 */

public class LifecycleHandler extends Handler implements FullLifecycleObserver {

    private LifecycleOwner mLifecycleOwner;

    public LifecycleHandler(LifecycleOwner lifecycleOwner) {
        mLifecycleOwner = lifecycleOwner;
        addObserver();
    }

    public LifecycleHandler(LifecycleOwner lifecycleOwner, Callback callback) {
        super(callback);
        mLifecycleOwner = lifecycleOwner;
        addObserver();
    }

    public LifecycleHandler(LifecycleOwner lifecycleOwner, Looper looper) {
        super(looper);
        mLifecycleOwner = lifecycleOwner;
        addObserver();
    }

    public LifecycleHandler(LifecycleOwner lifecycleOwner, Looper looper, Callback callback) {
        super(looper, callback);
        mLifecycleOwner = lifecycleOwner;
        addObserver();
    }

    private void addObserver() {
        if (mLifecycleOwner != null) {
            mLifecycleOwner.getLifecycle().addObserver(new FullLifecycleObserverAdapter(mLifecycleOwner, this));
        }
    }


    @Override
    public void onCreate(LifecycleOwner owner) {

    }

    @Override
    public void onStart(LifecycleOwner owner) {

    }

    @Override
    public void onResume(LifecycleOwner owner) {

    }

    @Override
    public void onPause(LifecycleOwner owner) {

    }

    @Override
    public void onStop(LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(LifecycleOwner owner) {
        removeCallbacksAndMessages(null);
    }
}
