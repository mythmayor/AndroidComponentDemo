package com.mythmayor.mainproject.ui.activity;

import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mythmayor.basicproject.base.BaseTitleBarMvpActivity;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.mainproject.R;
import com.mythmayor.mainproject.contract.PersonalInfoContract;
import com.mythmayor.mainproject.presenter.PersonalInfoPresenter;

/**
 * Created by mythmayor on 2020/7/13.
 * 个人信息页面
 */
@Route(path = "/mainproject/PersonalInfoActivity")
public class PersonalInfoActivity extends BaseTitleBarMvpActivity<PersonalInfoPresenter> implements PersonalInfoContract.View {

    @Override
    public int getSubLayoutResId() {
        return R.layout.activity_personal_info;
    }

    @Override
    public void initSubView(View view) {
        mPresenter = new PersonalInfoPresenter();
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
        setTopTitle(true, "个人信息");
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
