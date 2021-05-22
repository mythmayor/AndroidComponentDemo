package com.mythmayor.moduled.ui.activity;

import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BaseTitleBarMvvmActivity;
import com.mythmayor.basicproject.bean.EventBusBean;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.ui.view.TopTitleBar;
import com.mythmayor.moduled.R;
import com.mythmayor.moduled.databinding.ActivityNotificationBinding;
import com.mythmayor.moduled.viewmodel.NotificationViewModel;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by mythmayor on 2020/7/13.
 * 消息通知页面
 */
@Route(path = MyConstant.AROUTER_NotificationActivity)
public class NotificationActivity extends BaseTitleBarMvvmActivity<NotificationViewModel, ActivityNotificationBinding> {

    @Override
    protected int getMvvmLayoutResId() {
        return R.layout.activity_notification;
    }

    @Override
    protected void initMvvmEvent() {

    }

    @Override
    protected void initMvvmData(Intent intent) {

    }

    @Override
    public void setTitleBar(TopTitleBar topTitleBar) {
        topTitleBar.setLeftImage(true, R.mipmap.img_arrow_left_white);
        topTitleBar.setTopTitle(true, "消息通知");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post(new EventBusBean(MyConstant.EVENT_KEY_NOTIFICATION));
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
