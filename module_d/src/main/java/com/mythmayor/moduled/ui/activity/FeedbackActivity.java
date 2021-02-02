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
import com.mythmayor.moduled.databinding.ActivityFeedbackBinding;
import com.mythmayor.moduled.viewmodel.FeedbackViewModel;

/**
 * Created by mythmayor on 2020/7/13.
 * 意见反馈页面
 */
@Route(path = MyConstant.AROUTER_FeedbackActivity)
public class FeedbackActivity extends BaseTitleBarMvvmActivity<FeedbackViewModel, ActivityFeedbackBinding> {

    @Override
    protected int getMvvmLayoutResId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initMvvmEvent() {
        mViewDataBinding.setFeedbackActivity(this);
    }

    @Override
    protected void initMvvmData(Intent intent) {

    }

    @Override
    public void setTitleBar(TopTitleBar topTitleBar) {
        topTitleBar.setLeftImage(true, R.mipmap.arrow_left);
        topTitleBar.setTopTitle(true, "意见反馈");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        //在Android依赖库中switch-case语句访问资源ID时会报错，这是因为Android library中生成的R.java中的资源ID不是常数
    }

    public void feedback(View view) {
        String name = mViewDataBinding.inputboxName.getInputContent();
        String contact = mViewDataBinding.inputboxContact.getInputContent();
        String title = mViewDataBinding.inputboxTitle.getInputContent();
        String detail = mViewDataBinding.etDetail.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showToast("请输入姓名");
            return;
        }
        if (TextUtils.isEmpty(contact)) {
            ToastUtil.showToast("请输入联系方式");
            return;
        }
        if (TextUtils.isEmpty(title)) {
            ToastUtil.showToast("请输入标题");
            return;
        }
        if (TextUtils.isEmpty(detail)) {
            ToastUtil.showToast("请输入详细说明");
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
        ToastUtil.showToast("提交成功，感谢您的反馈！");
        finish();
    }
}
