package com.mythmayor.basicproject.base;

import autodispose2.AutoDisposeConverter;

/**
 * Created by mythmayor on 2020/6/30.
 * View基类
 */
public interface BaseView {

    /**
     * 显示加载中
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 数据获取失败
     *
     * @param errMessage
     */
    void onError(String errMessage);

    /**
     * 绑定Android生命周期 防止RxJava内存泄漏
     *
     * @param <T>
     * @return
     */
    <T> AutoDisposeConverter<T> bindAutoDispose();
}
