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
import com.mythmayor.basicproject.ui.dialog.NumberPickerDialog;
import com.mythmayor.basicproject.ui.dialog.ProgressDialog01;
import com.mythmayor.basicproject.ui.view.InputBox;
import com.mythmayor.basicproject.ui.view.TopTitleBar;
import com.mythmayor.basicproject.utils.ToastUtil;
import com.mythmayor.mainproject.R;
import com.mythmayor.mainproject.contract.PersonalInfoContract;
import com.mythmayor.mainproject.presenter.PersonalInfoPresenter;

/**
 * Created by mythmayor on 2020/7/13.
 * 个人信息页面
 */
@Route(path = "/mainproject/PersonalInfoActivity")
public class PersonalInfoActivity extends BaseTitleBarMvpActivity<PersonalInfoPresenter> implements PersonalInfoContract.View {

    private InputBox inputboxname;
    private InputBox inputboxgender;
    private InputBox inputboxage;
    private InputBox inputboxphone;
    private TextView tvedit;

    private boolean isEditable;//是否是可编辑状态
    private int mGenderValue;

    @Override
    public int getSubLayoutResId() {
        return R.layout.activity_personal_info;
    }

    @Override
    public void initSubView(View view) {
        mPresenter = new PersonalInfoPresenter();
        mPresenter.attachView(this);
        inputboxname = (InputBox) findViewById(R.id.inputbox_name);
        inputboxgender = (InputBox) findViewById(R.id.inputbox_gender);
        inputboxage = (InputBox) findViewById(R.id.inputbox_age);
        inputboxphone = (InputBox) findViewById(R.id.inputbox_phone);
        tvedit = (TextView) findViewById(R.id.tv_edit);
    }

    @Override
    public void initSubEvent() {
        inputboxgender.setOnClickListener(this);
        tvedit.setOnClickListener(this);
    }

    @Override
    public void initSubData(Intent intent) {
        inputboxname.setTvcontentText("马云");
        inputboxage.setTvcontentText("50");
    }

    @Override
    public void setTitleBar(TopTitleBar topTitleBar) {
        topTitleBar.setLeftImage(true, R.mipmap.arrow_left);
        topTitleBar.setTopTitle(true, "个人信息");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        //在Android依赖库中switch-case语句访问资源ID时会报错，这是因为Android library中生成的R.java中的资源ID不是常数
        if (v == inputboxgender) {
            if (isEditable) {
                String[] genders = {"男", "女"};
                NumberPickerDialog.show(this, genders, mGenderValue, new NumberPickerDialog.OnSelectListener() {
                    @Override
                    public void onSelected(int type, int value, String result) {
                        mGenderValue = value;
                        if (type == NumberPickerDialog.CLICK_TYPE_SURE && !TextUtils.isEmpty(result)) {
                            inputboxgender.setTvcontentText(result);
                        }
                    }
                });
            }
        } else if (v == tvedit) {
            edit();
        }
    }

    private void edit() {
        if (isEditable) {
            inputboxname.setType(InputBox.TYPE_DISPLAY);
            inputboxgender.setTvcontentDrawableRight(0);
            inputboxgender.setTvcontentHint("");
            inputboxage.setType(InputBox.TYPE_DISPLAY);
            inputboxphone.setType(InputBox.TYPE_DISPLAY);
            tvedit.setText("编辑");
            mPresenter.getUserInfo(new UserInfoRequest("test", "test"));
        } else {
            inputboxname.setType(InputBox.TYPE_EDIT);
            inputboxgender.setTvcontentDrawableRight(R.mipmap.arrow_right3);
            inputboxgender.setTvcontentHint("请选择");
            inputboxage.setType(InputBox.TYPE_EDIT);
            inputboxphone.setType(InputBox.TYPE_EDIT);
            tvedit.setText("完成");
        }
        isEditable = !isEditable;
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
        ToastUtil.showToast("Success");
    }
}
