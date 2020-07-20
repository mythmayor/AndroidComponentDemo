package com.mythmayor.mainproject.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.mythmayor.basicproject.base.BaseMvpFragment;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.response.LoginResponse;
import com.mythmayor.basicproject.ui.view.NavigationItemView;
import com.mythmayor.basicproject.utils.IntentUtil;
import com.mythmayor.basicproject.utils.UserInfoManager;
import com.mythmayor.mainproject.R;
import com.mythmayor.mainproject.contract.DFragmentContract;
import com.mythmayor.mainproject.presenter.DFragmentPresenter;
import com.mythmayor.mainproject.ui.activity.ChangePasswordActivity;
import com.mythmayor.mainproject.ui.activity.FeedbackActivity;
import com.mythmayor.mainproject.ui.activity.MainActivity;
import com.mythmayor.mainproject.ui.activity.NotificationActivity;
import com.mythmayor.mainproject.ui.activity.PersonalInfoActivity;
import com.mythmayor.mainproject.ui.activity.SettingActivity;

/**
 * Created by mythmayor on 2020/7/9.
 */
public class DFragment extends BaseMvpFragment<DFragmentPresenter> implements DFragmentContract.View {

    //标记Fragment是否初始化完成
    private boolean isPrepared;
    private View view;

    private ImageView ivavatar;
    private TextView tvname;
    private LinearLayout llpersonalinfo;
    private NavigationItemView nivmessage;
    private NavigationItemView nivfeedback;
    private NavigationItemView nivmodifypwd;
    private NavigationItemView nivsetting;
    private MainActivity mMainActivity;

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isPrepared = true;
        if (view == null) {
            view = super.onCreateView(inflater, container, savedInstanceState);
        }
        return view;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_d;
    }

    @Override
    protected void initView(View view) {
        ivavatar = (ImageView) view.findViewById(R.id.iv_avatar);
        tvname = (TextView) view.findViewById(R.id.tv_name);
        llpersonalinfo = (LinearLayout) view.findViewById(R.id.ll_personalinfo);
        nivmessage = (NavigationItemView) view.findViewById(R.id.niv_message);
        nivfeedback = (NavigationItemView) view.findViewById(R.id.niv_feedback);
        nivmodifypwd = (NavigationItemView) view.findViewById(R.id.niv_modifypwd);
        nivsetting = (NavigationItemView) view.findViewById(R.id.niv_setting);
        mMainActivity = (MainActivity) getActivity();
    }

    @Override
    protected void initEvent() {
        llpersonalinfo.setOnClickListener(this);
        nivmessage.setOnClickListener(this);
        nivfeedback.setOnClickListener(this);
        nivmodifypwd.setOnClickListener(this);
        nivsetting.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mPresenter = new DFragmentPresenter();
        mPresenter.attachView(this);
        LoginResponse.DataBean loginInfo = UserInfoManager.getLoginInfo(getActivity());
        if (loginInfo != null) {
            tvname.setText(loginInfo.getUsername());
        } else {
            tvname.setText("--");
        }
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true).titleBarMarginTop(R.id.view_blank).init();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == llpersonalinfo) {
            IntentUtil.startActivity(getActivity(), PersonalInfoActivity.class);
        } else if (v == nivmessage) {
            IntentUtil.startActivity(getActivity(), NotificationActivity.class);
        } else if (v == nivfeedback) {
            IntentUtil.startActivity(getActivity(), FeedbackActivity.class);
        } else if (v == nivmodifypwd) {
            IntentUtil.startActivity(getActivity(), ChangePasswordActivity.class);
        } else if (v == nivsetting) {
            IntentUtil.startActivity(getActivity(), SettingActivity.class);
        }
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
