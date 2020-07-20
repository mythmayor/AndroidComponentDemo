package com.mythmayor.mainproject.ui.activity;

import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mythmayor.basicproject.base.BaseTitleBarMvpActivity;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.mainproject.R;
import com.mythmayor.mainproject.contract.NotificationContract;
import com.mythmayor.mainproject.presenter.NotificationPresenter;

/**
 * Created by mythmayor on 2020/7/13.
 * 消息通知页面
 */
@Route(path = "/mainproject/NotificationActivity")
public class NotificationActivity extends BaseTitleBarMvpActivity<NotificationPresenter> implements NotificationContract.View {

    @Override
    public int getSubLayoutResId() {
        return R.layout.activity_notification;
    }

    @Override
    public void initSubView(View view) {
        mPresenter = new NotificationPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void initSubEvent() {

    }

    @Override
    public void initSubData(Intent intent) {

    }

    @Override
    public void setTitleBar() {
        setLeftImage(true, R.mipmap.arrow_left);
        setTopTitle(true, "消息通知");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        //在Android依赖库中switch-case语句访问资源ID时会报错，这是因为Android library中生成的R.java中的资源ID不是常数

    }

    @Override
    public void showLoading(String address) {
    }

    @Override
    public void hideLoading(String address) {
    }

    @Override
    public void onError(String address, String errMessage) {
    }

    @Override
    public void onSuccess(String address, BaseResponse baseResp) {
    }
}
