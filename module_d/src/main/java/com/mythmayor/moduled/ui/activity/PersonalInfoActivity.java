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
import com.mythmayor.basicproject.ui.dialog.NumberPickerDialog;
import com.mythmayor.basicproject.ui.dialog.ProgressDialog01;
import com.mythmayor.basicproject.ui.view.InputBox;
import com.mythmayor.basicproject.ui.view.TopTitleBar;
import com.mythmayor.basicproject.utils.ToastUtil;
import com.mythmayor.moduled.R;
import com.mythmayor.moduled.databinding.ActivityPersonalInfoBinding;
import com.mythmayor.moduled.viewmodel.PersonalInfoViewModel;

/**
 * Created by mythmayor on 2020/7/13.
 * 个人信息页面
 */
@Route(path = MyConstant.AROUTER_PersonalInfoActivity)
public class PersonalInfoActivity extends BaseTitleBarMvvmActivity<PersonalInfoViewModel, ActivityPersonalInfoBinding> {

    private boolean isEditable;//是否是可编辑状态
    private int mGenderValue;

    @Override
    protected int getMvvmLayoutResId() {
        return R.layout.activity_personal_info;
    }

    @Override
    protected void initMvvmEvent() {
        mViewDataBinding.inputboxGender.setOnClickListener(this);
        mViewDataBinding.tvEdit.setOnClickListener(this);
    }

    @Override
    protected void initMvvmData(Intent intent) {
        mViewDataBinding.inputboxName.setTvcontentText("马云");
        mViewDataBinding.inputboxAge.setTvcontentText("50");
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
        if (v == mViewDataBinding.inputboxGender) {
            if (isEditable) {
                String[] genders = {"男", "女"};
                NumberPickerDialog.show(this, genders, mGenderValue, new NumberPickerDialog.OnSelectListener() {
                    @Override
                    public void onSelected(int type, int value, String result) {
                        mGenderValue = value;
                        if (type == NumberPickerDialog.CLICK_TYPE_SURE && !TextUtils.isEmpty(result)) {
                            mViewDataBinding.inputboxGender.setTvcontentText(result);
                        }
                    }
                });
            }
        } else if (v == mViewDataBinding.tvEdit) {
            edit();
        }
    }

    private void edit() {
        if (isEditable) {
            mViewDataBinding.inputboxName.setType(InputBox.TYPE_DISPLAY);
            mViewDataBinding.inputboxGender.setTvcontentDrawableRight(0);
            mViewDataBinding.inputboxGender.setTvcontentHint("");
            mViewDataBinding.inputboxAge.setType(InputBox.TYPE_DISPLAY);
            mViewDataBinding.inputboxPhone.setType(InputBox.TYPE_DISPLAY);
            mViewDataBinding.tvEdit.setText("编辑");
            mViewModel.getUserInfo(this,new UserInfoRequest("test", "test"));
        } else {
            mViewDataBinding.inputboxName.setType(InputBox.TYPE_EDIT);
            mViewDataBinding.inputboxGender.setTvcontentDrawableRight(R.mipmap.arrow_right3);
            mViewDataBinding.inputboxGender.setTvcontentHint("请选择");
            mViewDataBinding.inputboxAge.setType(InputBox.TYPE_EDIT);
            mViewDataBinding.inputboxPhone.setType(InputBox.TYPE_EDIT);
            mViewDataBinding.tvEdit.setText("完成");
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
