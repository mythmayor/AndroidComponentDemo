package com.mythmayor.mainproject.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mythmayor.basicproject.base.BaseTitleBarMvpActivity;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.utils.ProjectUtil;
import com.mythmayor.mainproject.R;
import com.mythmayor.mainproject.contract.AboutUsContract;
import com.mythmayor.mainproject.presenter.AboutUsPresenter;

/**
 * Created by mythmayor on 2020/7/13.
 * 关于我们页面
 */
@Route(path = "/mainproject/AboutUsActivity")
public class AboutUsActivity extends BaseTitleBarMvpActivity<AboutUsPresenter> implements AboutUsContract.View {

    private TextView tvversion;

    @Override
    public int getSubLayoutResId() {
        return R.layout.activity_about_us;
    }

    @Override
    public void initSubView(View view) {
        mPresenter = new AboutUsPresenter();
        mPresenter.attachView(this);
        tvversion = (TextView) findViewById(R.id.tv_version);
    }

    @Override
    public void initSubEvent() {

    }

    @Override
    public void initSubData(Intent intent) {
        tvversion.setText("v" + ProjectUtil.getVersion(this));
    }

    @Override
    public void setTitleBar() {
        setLeftImage(true, R.mipmap.arrow_left);
        setTopTitle(true, "关于我们");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        //在Android依赖库中switch-case语句访问资源ID时会报错，这是因为Android library中生成的R.java中的资源ID不是常数

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(String errMessage) {

    }

    @Override
    public void onSuccess(BaseResponse baseResp) {

    }
}
