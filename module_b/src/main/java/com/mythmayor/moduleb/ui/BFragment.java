package com.mythmayor.moduleb.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.gyf.immersionbar.ImmersionBar;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BaseMvpFragment;
import com.mythmayor.basicproject.response.BaseResponse;
import com.mythmayor.moduleb.R;
import com.mythmayor.moduleb.contract.BFragmentContract;
import com.mythmayor.moduleb.presenter.BFragmentPresenter;

/**
 * Created by mythmayor on 2020/7/9.
 */
public class BFragment extends BaseMvpFragment<BFragmentPresenter> implements BFragmentContract.View {

    //标记Fragment是否初始化完成
    private boolean isPrepared;
    private View view;
    private TextView textView;
    private ImageView ivdrawerleft;
    private ImageView ivdrawerright;
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
        return R.layout.fragment_b;
    }

    @Override
    protected void initView(View view) {
        textView = (TextView) view.findViewById(R.id.textView);
        ivdrawerleft = (ImageView) view.findViewById(R.id.iv_drawer_left);
        ivdrawerright = (ImageView) view.findViewById(R.id.iv_drawer_right);
        //mMainActivity = (MainActivity) getActivity();
    }

    @Override
    protected void initEvent() {
        textView.setOnClickListener(this);
        ivdrawerleft.setOnClickListener(this);
        ivdrawerright.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mPresenter = new BFragmentPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == textView) {
            ARouter.getInstance().build(MyConstant.AROUTER_CActivity).navigation();
        }
        /*if (v == ivdrawerleft) {
            mMainActivity.openLeftDrawer();
        } else if (v == ivdrawerright) {
            mMainActivity.openRightDrawer();
        }*/
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
