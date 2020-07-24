package com.mythmayor.basicproject.base;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mythmayor.basicproject.receiver.NetworkBroadcastReceiver;
import com.mythmayor.basicproject.utils.ActivityCollector;
import com.umeng.message.PushAgent;

/**
 * Created by mythmayor on 2020/6/30.
 * Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, NetworkBroadcastReceiver.NetworkListener {

    protected final String TAG = BaseActivity.class.getSimpleName();
    public static BaseActivity mActivity;
    public static Context mContext;
    private NetworkBroadcastReceiver mNetworkBroadcastReceiver;//网络连接状态监听

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //屏幕常亮
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ActivityCollector.addActivity(this);
        if (0 != getLayoutResId()) {
            setContentView(getLayoutResId());
        }
        mActivity = this;
        mContext = this;
        mNetworkBroadcastReceiver = new NetworkBroadcastReceiver();
        //注册网络连状态监听
        IntentFilter networkFilter = new IntentFilter();
        networkFilter.addAction(NetworkBroadcastReceiver.ACTION_CONNECTIVITY_CHANGE);
        registerReceiver(mNetworkBroadcastReceiver, networkFilter);
        mNetworkBroadcastReceiver.addNetworkListener(this);
        //初始化控件
        initView();
        //初始化事件
        initEvent();
        //初始化数据
        initData(getIntent());
        //开启友盟应用数据统计
        PushAgent.getInstance(this).onAppStart();
    }

    /**
     * 获取布局资源ID
     */
    @LayoutRes
    protected abstract int getLayoutResId();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化事件
     */
    protected abstract void initEvent();

    /**
     * 初始化数据
     */
    protected abstract void initData(Intent intent);

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        mActivity = this;
        mContext = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivity = null;
        mContext = null;
        ActivityCollector.removeActivity(this);
        unregisterReceiver(mNetworkBroadcastReceiver);
        mNetworkBroadcastReceiver.removeNetworkListener(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            if (null != getCurrentFocus() && null != getCurrentFocus().getWindowToken()) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onNetworkListener(int status) {
    }
}
