package com.mythmayor.basicproject.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import java.lang.reflect.ParameterizedType;

import autodispose2.AutoDispose;
import autodispose2.AutoDisposeConverter;
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider;

/**
 * Created by mythmayor on 2020/6/30.
 * MvvmActivity基类
 */
public abstract class BaseMvvmActivity<VM extends ViewModel, VDB extends ViewDataBinding> extends BaseActivity implements BaseView {

    protected VM mViewModel;
    protected VDB mViewDataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewDataBinding = DataBindingUtil.setContentView(this, getMvvmLayoutResId());
        mViewDataBinding.setLifecycleOwner(this);
        //获得泛型参数的实际类型
        Class<VM> vmClass = (Class<VM>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        mViewModel = ViewModelProviders.of(this).get(vmClass);
        //初始化事件
        initMvvmEvent();
        //初始化数据
        initMvvmData(getIntent());
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void initData(Intent intent) {
    }

    protected abstract @LayoutRes
    int getMvvmLayoutResId();

    //初始化事件
    protected abstract void initMvvmEvent();

    //初始化数据
    protected abstract void initMvvmData(Intent intent);

    @Override
    public void onClick(View v) {
    }

    /**
     * 绑定生命周期 防止内存泄漏
     *
     * @param <T>
     * @return
     */
    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY));
    }
}
