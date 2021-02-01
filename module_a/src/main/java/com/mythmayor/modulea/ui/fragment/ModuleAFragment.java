package com.mythmayor.modulea.ui.fragment;

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
import com.mythmayor.basicproject.ui.view.SearchBar;
import com.mythmayor.basicproject.utils.ARouterUtil;
import com.mythmayor.basicproject.utils.ToastUtil;
import com.mythmayor.modulea.R;
import com.mythmayor.modulea.contract.ModuleAFragmentContract;
import com.mythmayor.modulea.presenter.ModuleAFragmentPresenter;

/**
 * Created by mythmayor on 2020/7/9.
 */
@Route(path = MyConstant.AROUTER_ModuleAFragment)
public class ModuleAFragment extends BaseMvpFragment<ModuleAFragmentPresenter> implements ModuleAFragmentContract.View {

    //标记Fragment是否初始化完成
    private boolean isPrepared;
    private View view;
    private SearchBar mSearchBar;
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
        return R.layout.fragment_module_a;
    }

    @Override
    protected void initView(View view) {
        mSearchBar = (SearchBar) view.findViewById(R.id.search_bar);
        //mMainActivity = (MainActivity) getActivity();
    }

    @Override
    protected void initEvent() {
        mSearchBar.setOnSearchBarClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouterUtil.navigation(MyConstant.AROUTER_SearchActivity);
            }
        });
        mSearchBar.setOnSearchListener(new SearchBar.OnSearchListener() {
            @Override
            public void onInputFinished(String input) {
                ToastUtil.showToast("input=" + input);
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter = new ModuleAFragmentPresenter();
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
