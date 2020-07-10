package com.mythmayor.mainproject.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.textfield.TextInputEditText;
import com.gyf.immersionbar.ImmersionBar;
import com.mythmayor.basicproject.base.BaseMvpActivity;
import com.mythmayor.basicproject.receiver.NetworkBroadcastReceiver;
import com.mythmayor.basicproject.request.LoginRequest;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.response.LoginResponse;
import com.mythmayor.basicproject.utils.IntentUtil;
import com.mythmayor.basicproject.utils.LogUtil;
import com.mythmayor.basicproject.utils.ProgressDlgUtil;
import com.mythmayor.basicproject.utils.ToastUtil;
import com.mythmayor.basicproject.utils.UserInfoManager;
import com.mythmayor.basicproject.utils.http.HttpUtil;
import com.mythmayor.mainproject.R;
import com.mythmayor.mainproject.contract.LoginContract;
import com.mythmayor.mainproject.presenter.LoginPresenter;

/**
 * Created by mythmayor on 2020/6/30.
 * 登录页面
 */
@Route(path = "/mainproject/LoginActivity")
public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View {

    private TextInputEditText etusername;
    private TextInputEditText etpassword;
    private Button btnlogin;
    private Button btnregister;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).statusBarDarkFont(true).titleBarMarginTop(R.id.view_blank).init();
        etusername = (TextInputEditText) findViewById(R.id.et_username);
        etpassword = (TextInputEditText) findViewById(R.id.et_password);
        btnlogin = (Button) findViewById(R.id.btn_login);
        btnregister = (Button) findViewById(R.id.btn_register);
        getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                LogUtil.d(event.name());
            }
        });
    }

    @Override
    protected void initEvent() {
        btnlogin.setOnClickListener(this);
        btnregister.setOnClickListener(this);
    }

    @Override
    protected void initData(Intent intent) {
        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);
        LoginRequest accountInfo = UserInfoManager.getAccountInfo(this);
        if (accountInfo != null) {
            etusername.setText(accountInfo.getUsername());
            etpassword.setText(accountInfo.getPassword());
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        //在Android依赖库中switch-case语句访问资源ID时会报错，这是因为Android library中生成的R.java中的资源ID不是常数
        if (v.getId() == R.id.btn_login) {
            login();
        } else if (v.getId() == R.id.btn_register) {
            register();
        }
    }

    private void login() {
        String username = getUsername();
        String password = getPassword();
        LogUtil.i("username=" + username + ", password=" + password);
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            ToastUtil.showToast(this, "请输入账号和密码");
            return;
        }
        LoginRequest request = new LoginRequest(username, password);
        mPresenter.login(request);
    }

    private void register() {
        IntentUtil.startActivity(this, RegisterActivity.class);
    }

    //获取账号
    private String getUsername() {
        return etusername.getText().toString().trim();
    }

    //获取密码
    private String getPassword() {
        return etpassword.getText().toString().trim();
    }

    @Override
    public void showLoading() {
        ProgressDlgUtil.show(this, "正在登录，请稍后...");
    }

    @Override
    public void hideLoading() {
        ProgressDlgUtil.dismiss();
    }

    @Override
    public void onError(String errMessage) {
        ToastUtil.showToast(this, errMessage);
    }

    @Override
    public void onSuccess(BaseResponse baseResp) {
        LoginResponse resp = (LoginResponse) baseResp;
        LogUtil.i("response=" + HttpUtil.mGson.toJson(resp));
        if (resp.getErrorCode() == 0) {//登录成功
            UserInfoManager.setAccountInfo(this, new LoginRequest(getUsername(), getPassword()));
            UserInfoManager.setLoginInfo(this, HttpUtil.mGson.toJson(resp));
            ToastUtil.showToast(getApplicationContext(), "登录成功: " + resp.getData().getUsername());
            IntentUtil.startActivity(this, MainActivity.class);
            finish();
        } else {//登录失败
            ToastUtil.showToast(this, resp.getErrorMsg());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
}
