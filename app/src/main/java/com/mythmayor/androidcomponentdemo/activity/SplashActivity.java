package com.mythmayor.androidcomponentdemo.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mythmayor.androidcomponentdemo.R;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BaseActivity;
import com.mythmayor.basicproject.base.LifecycleHandler;
import com.mythmayor.basicproject.receiver.NetworkBroadcastReceiver;
import com.mythmayor.basicproject.request.LoginRequest;
import com.mythmayor.basicproject.utils.ARouterUtil;
import com.mythmayor.basicproject.utils.LogUtil;
import com.mythmayor.basicproject.utils.PermissionManager;
import com.mythmayor.basicproject.utils.PrefUtil;

/**
 * Created by mythmayor on 2020/6/30.
 * 启动页面
 */
@Route(path = MyConstant.AROUTER_SplashActivity)
public class SplashActivity extends BaseActivity {

    // 用于判断是否从后台返回或者是否到后台
    private static boolean isAppWentToBg = false;
    private static boolean isWindowFocused = false;
    private static boolean isBackPressed = false;

    private LifecycleHandler mHandler;
    private boolean isPermissionRequestFinished;
    private String[] mPermissionArray = new String[]{
            PermissionManager.PERMISSION_LOCATION,
            PermissionManager.PERMISSION_PHONE,
            PermissionManager.PERMISSION_CAMERA,
            PermissionManager.PERMISSION_STORAGE
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionManager.REQUEST_CODE_CUSTOM) {
            if (permissions.length > 0 && grantResults.length > 0 && permissions.length == grantResults.length) {
                enterMyApp();
                for (int i = 0; i < permissions.length; i++) {
                    LogUtil.d("【权限申请】permission=" + permissions[i] + ", grantResult=" + grantResults[i] + ", " + (grantResults[i] == PackageManager.PERMISSION_GRANTED ? "用户授予了此权限" : "用户拒绝了此权限"));
                }
            }
        }
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData(Intent intent) {
        mHandler = new LifecycleHandler(new LifecycleOwner() {
            @NonNull
            @Override
            public Lifecycle getLifecycle() {
                return SplashActivity.this.getLifecycle();
            }
        });
    }

    @Override
    public void onNetworkListener(int status) {
        if (status == NetworkBroadcastReceiver.NETWORK_NONE) {//无网络连接
            LogUtil.d("无网络连接");
        } else if (status == NetworkBroadcastReceiver.NETWORK_MOBILE) {//移动网络连接
            LogUtil.d("移动网络连接");
        } else if (status == NetworkBroadcastReceiver.NETWORK_WIFI) {//无线网络连接
            LogUtil.d("无线网络连接");
        }
    }

    private void enterMyApp() {
        boolean isUserLogin = PrefUtil.getBoolean(mContext, PrefUtil.SP_IS_USER_LOGIN, false);
        if (isUserLogin) {
            if (isAppWentToBg) {
                //不带参数跳转
                //ARouterUtil.navigation(MyConstant.AROUTER_MainActivity);
                //携带参数跳转
                Bundle params = new Bundle();
                params.putInt(MyConstant.ID, 36);
                params.putString(MyConstant.NAME, "admin");
                LoginRequest object = new LoginRequest("admin", "123456");
                params.putSerializable(MyConstant.OBJECT, object);
                ARouterUtil.navigationWithBundle(MyConstant.AROUTER_MainActivity,params);
                finish();
            } else {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ARouterUtil.navigation(MyConstant.AROUTER_MainActivity);
                        finish();
                        overridePendingTransition(R.anim.anim_static, R.anim.anim_static);
                    }
                }, 1500);
            }
        } else {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ARouterUtil.navigation(MyConstant.AROUTER_LoginActivity);
                    finish();
                    overridePendingTransition(R.anim.anim_static, R.anim.anim_static);
                }
            }, 1500);
        }
        if (isAppWentToBg) {
            isAppWentToBg = false;
            // 从后台返回
            //ToastUtil.showToast(this, "从后台返回");
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //isBackPressed = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        isPermissionRequestFinished = PermissionManager.getInstance().checkCustomPermission(this, mPermissionArray);
        if (!isPermissionRequestFinished) {
            PermissionManager.getInstance().getCustomPermission(this, mPermissionArray);
        } else {
            enterMyApp();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isWindowFocused) {
            isAppWentToBg = true;
            // 进入后台
            //ToastUtil.showToast(this, "进入后台");
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        isWindowFocused = hasFocus;
        if (isBackPressed && !hasFocus) {
            isBackPressed = false;
            isWindowFocused = true;
        }
        super.onWindowFocusChanged(hasFocus);
    }
}
