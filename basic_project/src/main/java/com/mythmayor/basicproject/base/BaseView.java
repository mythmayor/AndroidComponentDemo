package com.mythmayor.basicproject.base;

import com.mythmayor.basicproject.response.BaseResponse;

import autodispose2.AutoDisposeConverter;

/**
 * Created by mythmayor on 2020/6/30.
 * View基类
 */
public interface BaseView {

    /**
     * 显示加载中
     *
     * @param address 接口地址(解决同一页面调用多个接口时无法区分的问题)
     */
    void showLoading(String address);

    /**
     * 隐藏加载
     *
     * @param address 接口地址
     */
    void hideLoading(String address);

    /**
     * 数据获取失败
     *
     * @param address    接口地址
     * @param errMessage 错误信息
     */
    void onError(String address, String errMessage);

    /**
     * 数据获取成功
     *
     * @param address  接口地址
     * @param baseResp 响应体
     */
    void onSuccess(String address, BaseResponse baseResp);

    /**
     * 绑定Android生命周期 防止RxJava内存泄漏
     *
     * @param <T>
     * @return
     */
    <T> AutoDisposeConverter<T> bindAutoDispose();
}
