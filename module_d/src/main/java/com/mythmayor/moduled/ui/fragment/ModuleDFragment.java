package com.mythmayor.moduled.ui.fragment;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BaseMvvmFragment;
import com.mythmayor.basicproject.bean.EventBusBean;
import com.mythmayor.basicproject.request.UserInfoRequest;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.response.LoginResponse;
import com.mythmayor.basicproject.response.UserInfoResponse;
import com.mythmayor.basicproject.utils.ARouterUtil;
import com.mythmayor.basicproject.utils.LogUtil;
import com.mythmayor.basicproject.utils.ToastUtil;
import com.mythmayor.basicproject.utils.UserInfoManager;
import com.mythmayor.basicproject.utils.http.HttpUtil;
import com.mythmayor.moduled.R;
import com.mythmayor.moduled.databinding.FragmentDBinding;
import com.mythmayor.moduled.viewmodel.ModuleDFragmentViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by mythmayor on 2020/7/9.
 */
@Route(path = MyConstant.AROUTER_ModuleDFragment)
public class ModuleDFragment extends BaseMvvmFragment<ModuleDFragmentViewModel, FragmentDBinding> {

    //标记Fragment是否初始化完成
    private boolean isPrepared;

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
    }

    @Override
    protected int getMvvmLayoutResId() {
        EventBus.getDefault().register(this);//注册EventBus
        isPrepared = true;
        return R.layout.fragment_d;
    }

    @Override
    protected void initMvvmEvent() {
        mViewDataBinding.llPersonalinfo.setOnClickListener(this);
        mViewDataBinding.nivMessage.setOnClickListener(this);
        mViewDataBinding.nivFeedback.setOnClickListener(this);
        mViewDataBinding.nivModifypwd.setOnClickListener(this);
        mViewDataBinding.nivSetting.setOnClickListener(this);
    }

    @Override
    protected void initMvvmData() {
        LoginResponse.DataBean loginInfo = UserInfoManager.getLoginInfo(getActivity());
        if (loginInfo != null) {
            mViewDataBinding.tvName.setText(loginInfo.getUsername());
        } else {
            mViewDataBinding.tvName.setText("--");
        }
        getUserInfo();
    }

    private void getUserInfo() {
        UserInfoRequest request = new UserInfoRequest("", "");
        mViewModel.getUserInfo(this, request);
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
            mViewDataBinding.nivMessage.isShowTips(false);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == mViewDataBinding.llPersonalinfo) {
            ARouterUtil.navigation(MyConstant.AROUTER_PersonalInfoActivity);
        } else if (v == mViewDataBinding.nivMessage) {
            ARouterUtil.navigation(MyConstant.AROUTER_NotificationActivity);
        } else if (v == mViewDataBinding.nivFeedback) {
            ARouterUtil.navigation(MyConstant.AROUTER_FeedbackActivity);
        } else if (v == mViewDataBinding.nivModifypwd) {
            ARouterUtil.navigation(MyConstant.AROUTER_ChangePasswordActivity);
        } else if (v == mViewDataBinding.nivSetting) {
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
        ToastUtil.showToast(errMessage);
    }

    @Override
    public void onSuccess(String address, BaseResponse baseResp) {
        if (MyConstant.URL_LOGIN.equals(address)) {
            UserInfoResponse resp = (UserInfoResponse) baseResp;
            LogUtil.d(HttpUtil.mGson.toJson(resp));
        }
    }
}
