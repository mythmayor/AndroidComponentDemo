package com.mythmayor.basicproject.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import io.reactivex.rxjava3.annotations.NonNull;

/**
 * Created by mythmayor on 2020/6/30.
 */
public abstract class BaseMvvmFragment<VM extends ViewModel, VDB extends ViewDataBinding> extends BaseFragment implements BaseView {

    protected VM mViewModel;
    protected VDB mViewDataBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getMvvmLayoutResId(), container, false);
        mViewDataBinding.setLifecycleOwner(this);
        //获得泛型参数的实际类型
        Class<VM> vmClass = (Class<VM>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        mViewModel = ViewModelProviders.of(this).get(vmClass);
        return mViewDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //初始化事件
        initMvvmEvent();
        //初始化数据
        initMvvmData();
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void initView(View view) {
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void initData() {
    }

    protected abstract @LayoutRes
    int getMvvmLayoutResId();

    protected abstract void initMvvmEvent();

    protected abstract void initMvvmData();

    /**
     * 绑定生命周期 防止MVP内存泄漏
     *
     * @param <T>
     * @return
     */
    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY));
    }
}
