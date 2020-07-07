package com.mythmayor.basicproject;

/**
 * Created by mythmayor on 2020/6/30.
 * 全局常量
 */
public interface MyConstant {

    String ERROR_SERVER = "服务器异常";
    String ERROR_NET = "网络异常";
    String ERROR_DATA = "数据异常";

    String INTENT_EXTRA_TITLE = "INTENT_EXTRA_TITLE";
    String INTENT_EXTRA_URL = "INTENT_EXTRA_URL";

    //网络请求
    String URL_BASE = "https://www.wanandroid.com";
    String URL_LOGIN = URL_BASE + "/user/login";//登录接口
}
