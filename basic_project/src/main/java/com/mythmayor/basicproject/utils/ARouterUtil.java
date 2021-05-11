package com.mythmayor.basicproject.utils;

import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.template.ILogger;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by mythmayor on 2021/1/29.
 */
public class ARouterUtil {

    /**
     * 页面跳转，通过路径path解析
     *
     * @param path
     */
    public static void navigation(String path) {
        ARouter.getInstance().build(path).navigation();
    }

    /**
     * 页面跳转，通过Uri解析
     *
     * @param uri
     */
    public static void navigation(Uri uri) {
        ARouter.getInstance().build(uri).navigation();
    }

    /**
     * 页面跳转，使用绿色通道(跳过所有的拦截器)
     *
     * @param path
     */
    public static void navigationWithGreenChannel(String path) {
        ARouter.getInstance().build(path).greenChannel().navigation();
    }

    /**
     * 页面跳转，使用绿色通道(跳过所有的拦截器)
     *
     * @param uri
     */
    public static void navigationWithGreenChannel(Uri uri) {
        ARouter.getInstance().build(uri).greenChannel().navigation();
    }

    /**
     * 携带参数的页面跳转
     *
     * @param path
     * @param params
     */
    public static void navigationWithBundle(String path, Bundle params) {
        ARouter.getInstance().build(path).with(params).navigation();
    }

    /**
     * 携带参数的页面跳转
     *
     * @param uri
     * @param bundle
     */
    public static void navigationWithBundle(Uri uri, Bundle bundle) {
        ARouter.getInstance().build(uri).with(bundle).navigation();
    }

    /**
     * 携带对象的页面跳转
     *
     * @param path
     * @param key
     * @param value
     */
    public static void navigationWithObject(String path, String key, Object value) {
        ARouter.getInstance().build(path).withObject(key, value).navigation();
    }

    /**
     * 携带对象的页面跳转
     *
     * @param uri
     * @param key
     * @param value
     */
    public static void navigationWithObject(Uri uri, String key, Object value) {
        ARouter.getInstance().build(uri).withObject(key, value).navigation();
    }

    /**
     * 页面跳转，指定Flags
     *
     * @param path
     * @param flags
     */
    public static void navigationwithFlags(String path, int flags) {
        ARouter.getInstance().build(path).withFlags(flags).navigation();
    }

    /**
     * 页面跳转，指定Flags
     *
     * @param uri
     * @param flags
     */
    public static void navigationwithFlags(Uri uri, int flags) {
        ARouter.getInstance().build(uri).withFlags(flags).navigation();
    }

    /**
     * 带转场动画的页面跳转
     *
     * @param path
     * @param enterAnim
     * @param exitAnim
     */
    public static void navigationWithTransition(String path, int enterAnim, int exitAnim) {
        ARouter.getInstance().build(path).withTransition(enterAnim, exitAnim).navigation();
    }

    /**
     * 带转场动画的页面跳转
     *
     * @param uri
     * @param enterAnim
     * @param exitAnim
     */
    public static void navigationWithTransition(Uri uri, int enterAnim, int exitAnim) {
        ARouter.getInstance().build(uri).withTransition(enterAnim, exitAnim).navigation();
    }

    /**
     * 带转场动画(API 16+)的页面跳转
     *
     * @param path
     * @param compat
     */
    public static void navigationWithTransition(String path, ActivityOptionsCompat compat) {
        //makeSceneTransitionAnimation使用共享元素的时候，需要在navigation方法中传入当前Activity
        /*View view = null;
        compat = ActivityOptionsCompat.
                makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);*/
        ARouter.getInstance().build(path).withOptionsCompat(compat).navigation();
    }

    /**
     * 带转场动画(API 16+)的页面跳转
     *
     * @param uri
     * @param compat
     */
    public static void navigationWithTransition(Uri uri, ActivityOptionsCompat compat) {
        //makeSceneTransitionAnimation使用共享元素的时候，需要在navigation方法中传入当前Activity
        /*View view = null;
        compat = ActivityOptionsCompat.
                makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);*/
        ARouter.getInstance().build(uri).withOptionsCompat(compat).navigation();
    }

    /**
     * 获取Fragment
     *
     * @param path
     * @return
     */
    public static Fragment getFragment(String path) {
        Fragment fragment = (Fragment) ARouter.getInstance().build(path).navigation();
        return fragment;
    }

    /**
     * 使用自己的日志工具打印日志
     *
     * @param logger
     */
    public static void setLogger(ILogger logger) {
        ARouter.setLogger(logger);
    }
}
