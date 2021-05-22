package com.mythmayor.moduled.ui.activity;

import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BaseTitleBarMvvmActivity;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.ui.view.TopTitleBar;
import com.mythmayor.basicproject.utils.GlideUtil;
import com.mythmayor.basicproject.utils.ProjectUtil;
import com.mythmayor.moduled.R;
import com.mythmayor.moduled.databinding.ActivityAboutUsBinding;
import com.mythmayor.moduled.viewmodel.AboutUsViewModel;

/**
 * Created by mythmayor on 2020/7/13.
 * 关于我们页面
 */
@Route(path = MyConstant.AROUTER_AboutUsActivity)
public class AboutUsActivity extends BaseTitleBarMvvmActivity<AboutUsViewModel, ActivityAboutUsBinding> {

    @Override
    protected int getMvvmLayoutResId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initMvvmEvent() {
    }

    @Override
    protected void initMvvmData(Intent intent) {
        mViewDataBinding.tvVersion.setText("v" + ProjectUtil.getVersion(this));
        String imgUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595912793549&di=d566c1cc863b5d34d803535359d93f10&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F14%2F75%2F01300000164186121366756803686.jpg";
        GlideUtil.loadImage(imgUrl, R.mipmap.icon_main_tab_selected, mViewDataBinding.ivIcon);
    }

    @Override
    public void setTitleBar(TopTitleBar topTitleBar) {
        topTitleBar.setLeftImage(true, R.mipmap.img_arrow_left_white);
        topTitleBar.setTopTitle(true, "关于我们");
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
