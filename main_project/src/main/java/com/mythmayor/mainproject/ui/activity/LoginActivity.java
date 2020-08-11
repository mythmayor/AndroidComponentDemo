package com.mythmayor.mainproject.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;
import com.mythmayor.basicproject.base.BaseMvvmActivity;
import com.mythmayor.basicproject.receiver.NetworkBroadcastReceiver;
import com.mythmayor.basicproject.request.LoginRequest;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.response.LoginResponse;
import com.mythmayor.basicproject.ui.dialog.ProgressDialog01;
import com.mythmayor.basicproject.utils.IntentUtil;
import com.mythmayor.basicproject.utils.LogUtil;
import com.mythmayor.basicproject.utils.ToastUtil;
import com.mythmayor.basicproject.utils.UserInfoManager;
import com.mythmayor.basicproject.utils.http.HttpUtil;
import com.mythmayor.mainproject.R;
import com.mythmayor.mainproject.databinding.ActivityLoginBinding;
import com.mythmayor.mainproject.viewmodel.LoginViewModel;

/**
 * Created by mythmayor on 2020/6/30.
 * 登录页面
 */
@Route(path = "/mainproject/LoginActivity")
public class LoginActivity extends BaseMvvmActivity<LoginViewModel, ActivityLoginBinding> {

    private LoginRequest mLoginRequest;

    @Override
    protected int getMvvmLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initMvvmEvent() {
        mViewDataBinding.setLoginActivity(this);
        mViewDataBinding.cbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            }
        });
    }

    @Override
    protected void initMvvmData(Intent intent) {
        mLoginRequest = new LoginRequest();
        mViewDataBinding.setLoginRequest(mLoginRequest);
        ImmersionBar.with(this).statusBarDarkFont(true).titleBarMarginTop(R.id.view_blank).init();
        LoginRequest accountInfo = UserInfoManager.getAccountInfo(this);
        if (accountInfo != null) {
            mViewDataBinding.cbRemember.setChecked(true);
            mLoginRequest.setUsername(accountInfo.getUsername());
            mLoginRequest.setPassword(accountInfo.getPassword());
            //etusername.setText(accountInfo.getUsername());
            //etpassword.setText(accountInfo.getPassword());
        } else {
            mViewDataBinding.cbRemember.setChecked(false);
            //etusername.setText("");
            //etpassword.setText("");
            mLoginRequest.setUsername("");
            mLoginRequest.setPassword("");
        }
        //注册Lifecycle
        getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                LogUtil.d(event.name());
            }
        });
    }

    public void login(View view) {
        String username = mLoginRequest.getUsername();
        String password = mLoginRequest.getPassword();
        LogUtil.i("username=" + username + ", password=" + password);
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            ToastUtil.showToast("请输入账号和密码");
            return;
        }
        mViewModel.login(this, mLoginRequest);
    }

    public void register(View view) {
        IntentUtil.startActivity(this, RegisterActivity.class);
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

    @Override
    public void showLoading(String address) {
        ProgressDialog01.show(this, "正在登录，请稍后...");
    }

    @Override
    public void hideLoading(String address) {
        ProgressDialog01.disappear();
    }

    @Override
    public void onError(String address, String errMessage) {
        ToastUtil.showToast(errMessage);
    }

    @Override
    public void onSuccess(String address, BaseResponse baseResp) {
        LoginResponse resp = (LoginResponse) baseResp;
        LogUtil.i("response=" + HttpUtil.mGson.toJson(resp));
        if (resp.getErrorCode() == 0) {//登录成功
            if (mViewDataBinding.cbRemember.isChecked()) {
                UserInfoManager.setAccountInfo(this, new LoginRequest(mLoginRequest.getUsername(), mLoginRequest.getPassword()));
            } else {
                UserInfoManager.clearAccountInfo(this);
            }
            UserInfoManager.setLoginInfo(this, HttpUtil.mGson.toJson(resp));
            ToastUtil.showToast("登录成功: " + resp.getData().getUsername());
            IntentUtil.startActivity(this, MainActivity.class);
            finish();
        } else {//登录失败
            ToastUtil.showToast(resp.getErrorMsg());
        }
    }
}
