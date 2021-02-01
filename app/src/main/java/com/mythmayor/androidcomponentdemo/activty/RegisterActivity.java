package com.mythmayor.androidcomponentdemo.activty;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.textfield.TextInputEditText;
import com.gyf.immersionbar.ImmersionBar;
import com.mythmayor.androidcomponentdemo.R;
import com.mythmayor.androidcomponentdemo.contract.RegisterContract;
import com.mythmayor.androidcomponentdemo.presenter.RegisterPresenter;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BaseMvpActivity;
import com.mythmayor.basicproject.receiver.NetworkBroadcastReceiver;
import com.mythmayor.basicproject.request.RegisterRequest;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.response.RegisterResponse;
import com.mythmayor.basicproject.ui.dialog.ProgressDialog02;
import com.mythmayor.basicproject.utils.CommonUtil;
import com.mythmayor.basicproject.utils.LogUtil;
import com.mythmayor.basicproject.utils.MyCountDownTimer;
import com.mythmayor.basicproject.utils.ToastUtil;

/**
 * Created by mythmayor on 2020/6/30.
 * 注册页面
 */
@Route(path = MyConstant.AROUTER_RegisterActivity)
public class RegisterActivity extends BaseMvpActivity<RegisterPresenter> implements RegisterContract.View {

    private TextInputEditText etusername;
    private TextInputEditText etverifycode;
    private TextView tvverifycode;
    private TextInputEditText etpassword;
    private Button btnregister;
    private MyCountDownTimer mCountDownTimer;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        mPresenter = new RegisterPresenter();
        mPresenter.attachView(this);
        ImmersionBar.with(this).statusBarDarkFont(true).titleBarMarginTop(R.id.view_blank).init();
        etusername = (TextInputEditText) findViewById(R.id.et_username);
        etverifycode = (TextInputEditText) findViewById(R.id.et_verifycode);
        tvverifycode = (TextView) findViewById(R.id.tv_verifycode);
        etpassword = (TextInputEditText) findViewById(R.id.et_password);
        btnregister = (Button) findViewById(R.id.btn_register);
    }

    @Override
    protected void initEvent() {
        tvverifycode.setOnClickListener(this);
        btnregister.setOnClickListener(this);
    }

    @Override
    protected void initData(Intent intent) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        //在Android依赖库中switch-case语句访问资源ID时会报错，这是因为Android library中生成的R.java中的资源ID不是常数
        if (v == tvverifycode) {
            sendVerifyCode();
        } else if (v.getId() == R.id.btn_register) {
            register();
        }
    }

    private void sendVerifyCode() {
        mCountDownTimer = new MyCountDownTimer(60000, 1000, tvverifycode, CommonUtil.getColor(R.color.color_white), CommonUtil.getColor(R.color.color_white));
        mCountDownTimer.start();
    }


    private void register() {
        String username = getUsername();
        String password = getPassword();
        LogUtil.i("username=" + username + ", password=" + password);
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            ToastUtil.showToast("请输入账号和密码");
            return;
        }
        RegisterRequest request = new RegisterRequest(username, password);
        mPresenter.register(request);
    }

    //获取账号
    private String getUsername() {
        return etusername.getText().toString().trim();
    }

    //获取验证码
    private String getVerifyCode() {
        return etverifycode.getText().toString().trim();
    }

    //获取密码
    private String getPassword() {
        return etpassword.getText().toString().trim();
    }

    private void cancelCountDownTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer.onFinish();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelCountDownTimer();
    }

    @Override
    public void showLoading(String address) {
        ProgressDialog02.show(this, "正在注册，请稍后...");
    }

    @Override
    public void hideLoading(String address) {
        ProgressDialog02.disappear();
    }

    @Override
    public void onError(String address, String errMessage) {
        ToastUtil.showToast(errMessage);
    }

    @Override
    public void onSuccess(String address, BaseResponse baseResp) {
        RegisterResponse resp = (RegisterResponse) baseResp;
        if (resp.getErrorCode() == 0) {//注册成功
            ToastUtil.showToast("注册成功: " + resp.getData().getUsername());
            finish();
        } else {//注册失败
            ToastUtil.showToast(resp.getErrorMsg());
        }
    }
}
