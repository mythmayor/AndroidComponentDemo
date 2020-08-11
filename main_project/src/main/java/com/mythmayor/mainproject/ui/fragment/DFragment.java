package com.mythmayor.mainproject.ui.fragment;

import android.view.View;

import androidx.annotation.NonNull;

import com.gyf.immersionbar.ImmersionBar;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BaseMvvmFragment;
import com.mythmayor.basicproject.bean.EventBusBean;
import com.mythmayor.basicproject.request.UserInfoRequest;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.response.LoginResponse;
import com.mythmayor.basicproject.response.UserInfoResponse;
import com.mythmayor.basicproject.utils.IntentUtil;
import com.mythmayor.basicproject.utils.LogUtil;
import com.mythmayor.basicproject.utils.PermissionManager;
import com.mythmayor.basicproject.utils.ToastUtil;
import com.mythmayor.basicproject.utils.UserInfoManager;
import com.mythmayor.basicproject.utils.http.HttpUtil;
import com.mythmayor.mainproject.R;
import com.mythmayor.mainproject.databinding.FragmentDBinding;
import com.mythmayor.mainproject.ui.activity.ChangePasswordActivity;
import com.mythmayor.mainproject.ui.activity.FeedbackActivity;
import com.mythmayor.mainproject.ui.activity.MainActivity;
import com.mythmayor.mainproject.ui.activity.MapTestActivity;
import com.mythmayor.mainproject.ui.activity.NotificationActivity;
import com.mythmayor.mainproject.ui.activity.PersonalInfoActivity;
import com.mythmayor.mainproject.ui.activity.SettingActivity;
import com.mythmayor.mainproject.viewmodel.DFragmentViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by mythmayor on 2020/7/9.
 */
public class DFragment extends BaseMvvmFragment<DFragmentViewModel, FragmentDBinding> {

    //标记Fragment是否初始化完成
    private boolean isPrepared;
    private MainActivity mMainActivity;

    private String[] mPermissionArray = new String[]{
            PermissionManager.PERMISSION_LOCATION
    };

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        LogUtil.d("");
    }

    @Override
    protected int getMvvmLayoutResId() {
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
        mViewDataBinding.nivMaptest.setOnClickListener(this);
    }

    @Override
    protected void initMvvmData() {
        mMainActivity = (MainActivity) getActivity();
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionManager.REQUEST_CODE_CUSTOM) {
            if (!PermissionManager.getInstance().checkCustomPermission(getContext(), mPermissionArray)) {
                ToastUtil.showToast("用户拒绝开启权限");
            } else {
                IntentUtil.startActivity(getActivity(), MapTestActivity.class);
            }
        }
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
            IntentUtil.startActivity(getActivity(), PersonalInfoActivity.class);
        } else if (v == mViewDataBinding.nivMessage) {
            IntentUtil.startActivity(getActivity(), NotificationActivity.class);
        } else if (v == mViewDataBinding.nivFeedback) {
            IntentUtil.startActivity(getActivity(), FeedbackActivity.class);
        } else if (v == mViewDataBinding.nivModifypwd) {
            IntentUtil.startActivity(getActivity(), ChangePasswordActivity.class);
        } else if (v == mViewDataBinding.nivSetting) {
            IntentUtil.startActivity(getActivity(), SettingActivity.class);
        } else if (v == mViewDataBinding.nivMaptest) {
            if (!PermissionManager.getInstance().checkCustomPermission(getContext(), mPermissionArray)) {
                PermissionManager.getInstance().getCustomPermission(this, mPermissionArray);
            } else {
                IntentUtil.startActivity(getActivity(), MapTestActivity.class);
            }
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
