package com.mythmayor.mainproject.ui.fragment;

import android.view.View;

import com.gyf.immersionbar.ImmersionBar;
import com.mythmayor.basicproject.base.BaseMvvmFragment;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.utils.LogUtil;
import com.mythmayor.mainproject.R;
import com.mythmayor.mainproject.databinding.FragmentBBinding;
import com.mythmayor.mainproject.ui.activity.MainActivity;
import com.mythmayor.mainproject.viewmodel.BFragmentViewModel;

/**
 * Created by mythmayor on 2020/7/9.
 */
public class BFragment extends BaseMvvmFragment<BFragmentViewModel, FragmentBBinding> {

    //标记Fragment是否初始化完成
    private boolean isPrepared;
    private MainActivity mMainActivity;

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        LogUtil.d("");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_b;
    }

    @Override
    protected int getMvvmLayoutResId() {
        isPrepared = true;
        return R.layout.fragment_b;
    }

    @Override
    protected void initMvvmEvent() {
        mViewDataBinding.ivDrawerLeft.setOnClickListener(this);
        mViewDataBinding.ivDrawerRight.setOnClickListener(this);
    }

    @Override
    protected void initMvvmData() {
        mMainActivity = (MainActivity) getActivity();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == mViewDataBinding.ivDrawerLeft) {
            mMainActivity.openLeftDrawer();
        } else if (v ==  mViewDataBinding.ivDrawerRight) {
            mMainActivity.openRightDrawer();
        }
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(false).titleBarMarginTop(R.id.view_blank).init();
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
