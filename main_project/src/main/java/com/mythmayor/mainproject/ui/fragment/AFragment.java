package com.mythmayor.mainproject.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.mythmayor.basicproject.base.BaseMvpFragment;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.basicproject.ui.view.SearchBar;
import com.mythmayor.basicproject.utils.IntentUtil;
import com.mythmayor.basicproject.utils.ToastUtil;
import com.mythmayor.mainproject.R;
import com.mythmayor.mainproject.contract.AFragmentContract;
import com.mythmayor.mainproject.presenter.AFragmentPresenter;
import com.mythmayor.mainproject.ui.activity.MainActivity;
import com.mythmayor.mainproject.ui.activity.SearchActivity;

/**
 * Created by mythmayor on 2020/7/9.
 */
public class AFragment extends BaseMvpFragment<AFragmentPresenter> implements AFragmentContract.View {

    //标记Fragment是否初始化完成
    private boolean isPrepared;
    private View view;
    private SearchBar mSearchBar;
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
        return R.layout.fragment_a;
    }

    @Override
    protected void initView(View view) {
        mSearchBar = (SearchBar) view.findViewById(R.id.search_bar);
        mMainActivity = (MainActivity) getActivity();
    }

    @Override
    protected void initEvent() {
        mSearchBar.setOnSearchBarClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.startActivity(getActivity(), SearchActivity.class);
            }
        });
        mSearchBar.setOnSearchListener(new SearchBar.OnSearchListener() {
            @Override
            public void onInputFinished(String input) {
                ToastUtil.showToast(getActivity(), "input=" + input);
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter = new AFragmentPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void onError(String errMessage) {
    }

    @Override
    public void onSuccess(BaseResponse baseResp) {
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(false).titleBarMarginTop(R.id.view_blank).init();
    }
}
