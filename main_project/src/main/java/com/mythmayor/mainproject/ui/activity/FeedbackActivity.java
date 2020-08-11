package com.mythmayor.mainproject.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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
import com.mythmayor.mainproject.contract.FeedbackContract;
import com.mythmayor.mainproject.presenter.FeedbackPresenter;

/**
 * Created by mythmayor on 2020/7/13.
 * 意见反馈页面
 */
@Route(path = "/mainproject/FeedbackActivity")
public class FeedbackActivity extends BaseTitleBarMvpActivity<FeedbackPresenter> implements FeedbackContract.View {

    private InputBox inputboxname;
    private InputBox inputboxcontact;
    private InputBox inputboxtitle;
    private EditText etdetail;
    private TextView tvsubmit;

    @Override
    public int getSubLayoutResId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initSubView(View view) {
        mPresenter = new FeedbackPresenter();
        mPresenter.attachView(this);
        inputboxname = (InputBox) findViewById(R.id.inputbox_name);
        inputboxcontact = (InputBox) findViewById(R.id.inputbox_contact);
        inputboxtitle = (InputBox) findViewById(R.id.inputbox_title);
        etdetail = (EditText) findViewById(R.id.et_detail);
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
        topTitleBar.setTopTitle(true, "意见反馈");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        //在Android依赖库中switch-case语句访问资源ID时会报错，这是因为Android library中生成的R.java中的资源ID不是常数
        if (v == tvsubmit) {
            feedback();
        }
    }

    private void feedback() {
        String name = inputboxname.getInputContent();
        String contact = inputboxcontact.getInputContent();
        String title = inputboxtitle.getInputContent();
        String detail = etdetail.getText().toString().trim();
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
        ToastUtil.showToast("提交成功，感谢您的反馈！");
        finish();
    }
}
