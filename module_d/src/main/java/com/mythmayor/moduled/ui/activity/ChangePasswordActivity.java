package com.mythmayor.moduled.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BaseTitleBarMvvmActivity;
import com.mythmayor.basicproject.request.UserInfoRequest;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.response.UserInfoResponse;
import com.mythmayor.basicproject.ui.dialog.ProgressDialog01;
import com.mythmayor.basicproject.ui.view.TopTitleBar;
import com.mythmayor.basicproject.utils.ToastUtil;
import com.mythmayor.moduled.R;
import com.mythmayor.moduled.databinding.ActivityChangePasswordBinding;
import com.mythmayor.moduled.viewmodel.ChangePasswordViewModel;

/**
 * Created by mythmayor on 2020/7/13.
 * 修改密码页面
 */
@Route(path = MyConstant.AROUTER_ChangePasswordActivity)
public class ChangePasswordActivity extends BaseTitleBarMvvmActivity<ChangePasswordViewModel, ActivityChangePasswordBinding> {

    @Override
    protected int getMvvmLayoutResId() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void initMvvmEvent() {
        mViewDataBinding.setChangePasswordActivity(this);
    }

    @Override
    protected void initMvvmData(Intent intent) {
    }

    @Override
    public void setTitleBar(TopTitleBar topTitleBar) {
        topTitleBar.setLeftImage(true, R.mipmap.arrow_left);
        topTitleBar.setTopTitle(true, "修改密码");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        //在Android依赖库中switch-case语句访问资源ID时会报错，这是因为Android library中生成的R.java中的资源ID不是常数
    }

    public void changePassword(View view) {
        String oldpwd = mViewDataBinding.inputboxOldpwd.getInputContent();
        String newpwd = mViewDataBinding.inputboxNewpwd.getInputContent();
        String newpwd2 = mViewDataBinding.inputboxNewpwd2.getInputContent();
        if (TextUtils.isEmpty(oldpwd)) {
            ToastUtil.showToast("请输入旧密码");
            return;
        }
        if (TextUtils.isEmpty(newpwd)) {
            ToastUtil.showToast("请输入新密码");
            return;
        }
        if (TextUtils.isEmpty(newpwd2)) {
            ToastUtil.showToast("请确认新密码");
            return;
        }
        if (!newpwd.equals(newpwd2)) {
            ToastUtil.showToast("两次输入的密码不一致");
            return;
        }
        mViewModel.getUserInfo(this, new UserInfoRequest("test", "test"));
    }

    @Override
    public void showLoading(String address) {
        ProgressDialog01.show(this, "正在提交，请稍后...");
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
        UserInfoResponse resp = (UserInfoResponse) baseResp;
        ToastUtil.showToast("Success! 新密码：" + mViewDataBinding.inputboxNewpwd.getInputContent());
        finish();
    }
}
