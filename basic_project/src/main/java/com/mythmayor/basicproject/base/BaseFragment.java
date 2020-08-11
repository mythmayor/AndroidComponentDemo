package com.mythmayor.basicproject.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gyf.immersionbar.components.SimpleImmersionFragment;

/**
 * Created by mythmayor on 2020/6/30.
 * Fragment基类，实现了懒加载
 */
public abstract class BaseFragment extends SimpleImmersionFragment implements View.OnClickListener {

    //用于标记视图是否初始化
    protected boolean isVisible;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        initView(view);
        initEvent();
        initData();
        return view;
    }

    /**
     * 适用于ViewPager+Fragment
     * 在onCreate方法之前调用，用来判断Fragment的UI是否是可见的
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 适用于切换Fragment
     * Fragment隐藏与展示
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    //视图可见
    protected void onVisible() {
        lazyLoad();
    }

    //视图不可见
    protected void onInvisible() {
    }

    /**
     * 自定义抽象加载数据方法
     */
    protected abstract void lazyLoad();

    /**
     * 获取布局资源ID
     */
    @LayoutRes
    protected abstract int getLayoutResId();

    /**
     * 初始化控件
     */
    protected abstract void initView(View view);

    /**
     * 初始化事件
     */
    protected abstract void initEvent();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    public void onClick(View v) {
    }
}