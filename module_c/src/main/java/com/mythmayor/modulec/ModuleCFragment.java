package com.mythmayor.modulec;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BaseMvpFragment;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.modulec.R;
import com.mythmayor.modulec.contract.ModuleCFragmentContract;
import com.mythmayor.modulec.presenter.ModuleCFragmentPresenter;

/**
 * Created by mythmayor on 2020/7/9.
 */
@Route(path = MyConstant.AROUTER_ModuleCFragment)
public class ModuleCFragment extends BaseMvpFragment<ModuleCFragmentPresenter> implements ModuleCFragmentContract.View {

    //标记Fragment是否初始化完成
    private boolean isPrepared;
    private View view;
    //private MainActivity mMainActivity;

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
        return R.layout.fragment_module_c;
    }

    @Override
    protected void initView(View view) {
        //mMainActivity = (MainActivity) getActivity();
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        mPresenter = new ModuleCFragmentPresenter();
        mPresenter.attachView(this);
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
