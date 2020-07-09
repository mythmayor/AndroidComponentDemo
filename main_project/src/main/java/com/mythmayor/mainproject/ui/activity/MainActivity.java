package com.mythmayor.mainproject.ui.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;
import com.mythmayor.basicproject.base.BaseDialog;
import com.mythmayor.basicproject.base.BaseMvpActivity;
import com.mythmayor.basicproject.receiver.NetworkBroadcastReceiver;
import com.mythmayor.basicproject.request.LoginRequest;
import com.mythmayor.basicproject.request.UserInfoRequest;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.response.UserInfoResponse;
import com.mythmayor.basicproject.ui.dialog.LogoutDialog;
import com.mythmayor.basicproject.utils.IntentUtil;
import com.mythmayor.basicproject.utils.LogUtil;
import com.mythmayor.basicproject.utils.PrefUtil;
import com.mythmayor.basicproject.utils.ToastUtil;
import com.mythmayor.basicproject.utils.UserInfoManager;
import com.mythmayor.basicproject.utils.http.HttpUtil;
import com.mythmayor.mainproject.R;
import com.mythmayor.mainproject.contract.MainContract;
import com.mythmayor.mainproject.presenter.MainPresenter;

/**
 * Created by mythmayor on 2020/6/30.
 * 主页面
 */
@Route(path = "/mainproject/MainActivity")
public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContract.View {

    private TextView tvlogout;
    private final long EXIT_APP_BACK_PRESSED_INTERVAL = 1500;
    private long mCurrBackPressTimeMillis;
    private long mPrevBackPressTimeMillis;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.color_white).fitsSystemWindows(true).init();
        tvlogout = (TextView) findViewById(R.id.tv_logout);
    }

    @Override
    protected void initEvent() {
        tvlogout.setOnClickListener(this);
    }

    @Override
    protected void initData(Intent intent) {
        PrefUtil.putBoolean(this, PrefUtil.SP_IS_USER_LOGIN, true);
        mPresenter = new MainPresenter();
        mPresenter.attachView(this);
        LoginRequest accountInfo = UserInfoManager.getAccountInfo(this);
        if (accountInfo != null) {
            UserInfoRequest request = new UserInfoRequest(accountInfo.getUsername(), accountInfo.getPassword());
            mPresenter.getUserInfo(request);
        }
    }

    @Override
    public void onClick(View v) {
        //在Android依赖库中switch-case语句访问资源ID时会报错，这是因为Android library中生成的R.java中的资源ID不是常数
        if (v.getId() == R.id.tv_logout) {
            showLogoutDialog();
        }
    }

    private void showLogoutDialog() {
        String content = "确定要退出登录么？";
        final LogoutDialog dialog = new LogoutDialog(this, content, "取消", "确定");
        dialog.setNoOnclickListener(new BaseDialog.onNoOnclickListener() {
            @Override
            public void onNoClick(Object o) {
                dialog.dismiss();
            }
        });
        dialog.setYesOnclickListener(new BaseDialog.onYesOnclickListener() {
            @Override
            public void onYesClick(Object o) {
                dialog.dismiss();
                String accountInfo = PrefUtil.getString(MainActivity.this, PrefUtil.SP_ACCOUNT, "");
                PrefUtil.clear(MainActivity.this);
                PrefUtil.putString(MainActivity.this, PrefUtil.SP_ACCOUNT, accountInfo);
                IntentUtil.startActivityClearTask(MainActivity.this, LoginActivity.class);
            }
        });
        dialog.show();
        //ProjectUtil.setDialogWindowAttr(dialog);
    }

    @Override
    public void showLoading() {
        ToastUtil.showToast(this, "正在获取用户信息...");
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void onError(String errMessage) {
        ToastUtil.showToast(this, "获取用户信息失败，原因：" + errMessage);
    }

    @Override
    public void onSuccess(BaseResponse baseResp) {
        UserInfoResponse resp = (UserInfoResponse) baseResp;
        ToastUtil.showToast(this, "获取用户信息成功！" + HttpUtil.mGson.toJson(resp));
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        exitApp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroy();
    }

    private void exitApp() {
        mCurrBackPressTimeMillis = System.currentTimeMillis();
        if (mCurrBackPressTimeMillis - mPrevBackPressTimeMillis < EXIT_APP_BACK_PRESSED_INTERVAL) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancelAll();
            finish();
        } else {
            mPrevBackPressTimeMillis = mCurrBackPressTimeMillis;
            ToastUtil.showToast(getApplicationContext(), "再按一次退出软件");
        }
    }

    private void destroy() {

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
