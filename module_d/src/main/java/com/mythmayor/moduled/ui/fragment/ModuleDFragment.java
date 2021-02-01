package com.mythmayor.moduled.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BaseMvpFragment;
import com.mythmayor.basicproject.bean.EventBusBean;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.response.LoginResponse;
import com.mythmayor.basicproject.ui.view.NavigationItemView;
import com.mythmayor.basicproject.utils.ARouterUtil;
import com.mythmayor.basicproject.utils.PermissionManager;
import com.mythmayor.basicproject.utils.UserInfoManager;
import com.mythmayor.moduled.R;
import com.mythmayor.moduled.contract.ModuleDFragmentContract;
import com.mythmayor.moduled.presenter.ModuleDFragmentPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by mythmayor on 2020/7/9.
 */
@Route(path = MyConstant.AROUTER_ModuleDFragment)
public class ModuleDFragment extends BaseMvpFragment<ModuleDFragmentPresenter> implements ModuleDFragmentContract.View {

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
    //private MainActivity mMainActivity;
    private String[] mPermissionArray = new String[]{
            PermissionManager.PERMISSION_LOCATION
    };

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);//注册EventBus
        isPrepared = true;
        if (view == null) {
            view = super.onCreateView(inflater, container, savedInstanceState);
        }
        return view;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_module_d;
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
        //mMainActivity = (MainActivity) getActivity();
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
        mPresenter = new ModuleDFragmentPresenter();
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
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);//取消注册EventBus
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBusBean bean) {//EventBus订阅方法，当接收到事件的时候，会调用该方法
        if (MyConstant.EVENT_KEY_NOTIFICATION.equals(bean.getKey())) {
            nivmessage.isShowTips(false);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == llpersonalinfo) {
            ARouterUtil.navigation(MyConstant.AROUTER_PersonalInfoActivity);
        } else if (v == nivmessage) {
            ARouterUtil.navigation(MyConstant.AROUTER_NotificationActivity);
        } else if (v == nivfeedback) {
            ARouterUtil.navigation(MyConstant.AROUTER_FeedbackActivity);
        } else if (v == nivmodifypwd) {
            ARouterUtil.navigation(MyConstant.AROUTER_ChangePasswordActivity);
        } else if (v == nivsetting) {
            ARouterUtil.navigation(MyConstant.AROUTER_SettingActivity);
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
