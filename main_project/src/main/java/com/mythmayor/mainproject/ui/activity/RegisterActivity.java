package com.mythmayor.mainproject.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.textfield.TextInputEditText;
import com.gyf.immersionbar.ImmersionBar;
import com.mythmayor.basicproject.base.BaseMvpActivity;
import com.mythmayor.basicproject.receiver.NetworkBroadcastReceiver;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.utils.LogUtil;
import com.mythmayor.basicproject.utils.ProgressDlgUtil;
import com.mythmayor.basicproject.utils.ToastUtil;
import com.mythmayor.basicproject.utils.http.HttpUtil;
import com.mythmayor.mainproject.R;
import com.mythmayor.mainproject.contract.RegisterContract;
import com.mythmayor.mainproject.presenter.RegisterPresenter;
import com.mythmayor.basicproject.request.RegisterRequest;
import com.mythmayor.basicproject.response.RegisterResponse;

/**
 * Created by mythmayor on 2020/6/30.
 * 注册页面
 */
@Route(path = "/mainproject/RegisterActivity")
public class RegisterActivity extends BaseMvpActivity<RegisterPresenter> implements RegisterContract.View {

    private TextInputEditText etusername;
    private TextInputEditText etpassword;
    private Button btnregister;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).statusBarDarkFont(true).titleBarMarginTop(R.id.view_blank).init();
        etusername = (TextInputEditText) findViewById(R.id.et_username);
        etpassword = (TextInputEditText) findViewById(R.id.et_password);
        btnregister = (Button) findViewById(R.id.btn_register);
    }

    @Override
    protected void initEvent() {
        btnregister.setOnClickListener(this);
    }

    @Override
    protected void initData(Intent intent) {
        mPresenter = new RegisterPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        //在Android依赖库中switch-case语句访问资源ID时会报错，这是因为Android library中生成的R.java中的资源ID不是常数
        if (v.getId() == R.id.btn_register) {
            register();
        }
    }

    private void register() {
        String username = getUsername();
        String password = getPassword();
        LogUtil.i("username=" + username + ", password=" + password);
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            ToastUtil.showToast(this, "请输入账号和密码");
            return;
        }
        RegisterRequest request = new RegisterRequest(username, password);
        mPresenter.register(request);
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
        ProgressDlgUtil.show(this, "正在注册，请稍后...");
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
        RegisterResponse resp = (RegisterResponse) baseResp;
        Log.i("❤驭霖·骏泊☆Myth.Mayor❤", "RegisterActivity - onSuccess: " + HttpUtil.mGson.toJson(resp));
        if (resp.getErrorCode() == 0) {//注册成功
            ToastUtil.showToast(getApplicationContext(), "注册成功: " + resp.getData().getUsername());
            finish();
        } else {//注册失败
            ToastUtil.showToast(this, resp.getErrorMsg());
        }
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
