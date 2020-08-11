package com.mythmayor.mainproject.ui.fragment;

import com.gyf.immersionbar.ImmersionBar;
import com.mythmayor.basicproject.base.BaseMvvmFragment;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.utils.LogUtil;
import com.mythmayor.mainproject.R;
import com.mythmayor.mainproject.databinding.FragmentCBinding;
import com.mythmayor.mainproject.ui.activity.MainActivity;
import com.mythmayor.mainproject.viewmodel.CFragmentViewModel;

/**
 * Created by mythmayor on 2020/7/9.
 */
public class CFragment extends BaseMvvmFragment<CFragmentViewModel, FragmentCBinding> {

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
    protected int getMvvmLayoutResId() {
        isPrepared = true;
        return R.layout.fragment_c;
    }

    @Override
    protected void initMvvmEvent() {

    }

    @Override
    protected void initMvvmData() {
        mMainActivity = (MainActivity) getActivity();
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
