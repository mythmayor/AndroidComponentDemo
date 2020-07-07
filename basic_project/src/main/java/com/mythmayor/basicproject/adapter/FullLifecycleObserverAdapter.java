package com.mythmayor.basicproject.adapter;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Created by mythmayor on 2020/6/30.
 */
public class FullLifecycleObserverAdapter implements LifecycleObserver {

    private final FullLifecycleObserver mObserver;

    private static final String TAG = "FullLifecycleObserverAd";
    private final LifecycleOwner mLifecycleOwner;

    public FullLifecycleObserverAdapter(LifecycleOwner lifecycleOwner, FullLifecycleObserver observer) {
        mLifecycleOwner = lifecycleOwner;
        mObserver = observer;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        Log.i(TAG, "onCreate: ");
        mObserver.onCreate(mLifecycleOwner);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        Log.i(TAG, "onStart: ");
        mObserver.onStart(mLifecycleOwner);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        Log.i(TAG, "onResume: ");
        mObserver.onResume(mLifecycleOwner);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        Log.i(TAG, "onPause: ");
        mObserver.onPause(mLifecycleOwner);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        Log.i(TAG, "onStop: ");
        mObserver.onStop(mLifecycleOwner);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        mObserver.onDestroy(mLifecycleOwner);
    }
}
