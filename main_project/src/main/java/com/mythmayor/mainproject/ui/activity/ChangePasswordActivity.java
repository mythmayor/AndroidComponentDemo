package com.mythmayor.mainproject.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mythmayor.basicproject.base.BaseTitleBarMvpActivity;
import com.mythmayor.basicproject.request.UserInfoRequest;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.response.UserInfoResponse;
import com.mythmayor.basicproject.ui.dialog.ProgressDialog01;
import com.mythmayor.basicproject.ui.view.InputBox;
import com.mythmayor.basicproject.ui.view.TopTitleBar;
import com.mythmayor.basicproject.utils.ToastUtil;
import com.mythmayor.mainproject.R;
import com.mythmayor.mainproject.contract.ChangePasswordContract;
import com.mythmayor.mainproject.presenter.ChangePasswordPresenter;

/**
 * Created by mythmayor on 2020/7/13.
 * 修改密码页面
 */
@Route(path = "/mainproject/ChangePasswordActivity")
public class ChangePasswordActivity extends BaseTitleBarMvpActivity<ChangePasswordPresenter> implements ChangePasswordContract.View {

    private InputBox inputboxoldpwd;
    private InputBox inputboxnewpwd;
    private InputBox inputboxnewpwd2;
    private TextView tvsubmit;

    @Override
    public int getSubLayoutResId() {
        return R.layout.activity_change_password;
    }

    @Override
    public void initSubView(View view) {
        mPresenter = new ChangePasswordPresenter();
        mPresenter.attachView(this);
        inputboxoldpwd = (InputBox) findViewById(R.id.inputbox_oldpwd);
        inputboxnewpwd = (InputBox) findViewById(R.id.inputbox_newpwd);
        inputboxnewpwd2 = (InputBox) findViewById(R.id.inputbox_newpwd2);
        tvsubmit = (TextView) findViewById(R.id.tv_submit);
    }

    @Override
    public void initSubEvent() {
        tvsubmit.setOnClickListener(this);
    }

    @Override
    public void initSubData(Intent intent) {
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
        if (v == tvsubmit) {
            changePassword();
        }
    }

    private void changePassword() {
        String oldpwd = inputboxoldpwd.getInputContent();
        String newpwd = inputboxnewpwd.getInputContent();
        String newpwd2 = inputboxnewpwd2.getInputContent();
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
        mPresenter.getUserInfo(new UserInfoRequest("test", "test"));
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
        ToastUtil.showToast("Success! 新密码：" + inputboxnewpwd.getInputContent());
        finish();
    }
}
