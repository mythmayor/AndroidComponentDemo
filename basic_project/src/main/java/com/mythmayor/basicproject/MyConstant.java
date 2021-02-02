package com.mythmayor.basicproject;

import com.mythmayor.basicproject.utils.CommonUtil;
import com.mythmayor.basicproject.utils.ProjectUtil;

/**
 * Created by mythmayor on 2020/6/30.
 * 全局常量
 */
public interface MyConstant {

    String ERROR_SERVER = "服务器异常";
    String ERROR_NET = "网络异常";
    String ERROR_DATA = "数据异常";

    String ID = "ID";
    String NAME = "NAME";
    String OBJECT = "OBJECT";
    String INTENT_EXTRA_TITLE = "INTENT_EXTRA_TITLE";
    String INTENT_EXTRA_URL = "INTENT_EXTRA_URL";

    String EVENT_KEY_NOTIFICATION = "EVENT_KEY_NOTIFICATION";

    String APK_NAME = "MyApp_prod";

    //网络请求信息
    String MEDIA_TYPE = "application/json; charset=utf-8";
    String HEADER_KEY = "Authorization";
    //网络请求地址
    //接口基础地址
    String URL_BASE = "https://www.wanandroid.com";
    //接口相对地址
    String URL_LOGIN = "/user/login";//登录接口
    //接口绝对地址
    String URL_ABSOLUTE_LOGIN = URL_BASE + URL_LOGIN;//登录接口

    //AES密钥
    //String AES_KEY = "1234567890!@#$%^";
    String AES_KEY = ProjectUtil.getAESKeyPart1(3, 4)
            + BuildConfig.AES_KEY_PART2
            + CommonUtil.getString(R.string.AES_KEY_PART3)
            + MyConstant.AES_KEY_PART4;
    String AES_KEY_PART4 = "!@#$%^";

    //友盟消息推送信息
    String UMENG_APP_KAY = "5f1922b39ec54a20b7efd239";
    String UMENG_MESSAGE_SECRET = "fa5badcf610c40183dc4028d98202f84";
    String UMENG_APP_MASTER_SECRET = "xdbrxrej3buojocrzdayc5fx2zxhskgd";

    //ARouter路由常量
    //app模块
    String AROUTER_SplashActivity = "/app/SplashActivity";
    String AROUTER_RegisterActivity = "/app/RegisterActivity";
    String AROUTER_LoginActivity = "/app/LoginActivity";
    String AROUTER_MainActivity = "/app/MainActivity";
    //basicproject模块
    String AROUTER_WebViewActivity = "/basicproject/WebViewActivity";
    String AROUTER_UmengClickActivity = "/basicproject/UmengClickActivity";
    //modulea模块
    String AROUTER_ModuleAActivity = "/modulea/ModuleAActivity";
    String AROUTER_SearchActivity = "/modulea/SearchActivity";
    String AROUTER_ModuleAFragment = "/modulea/ModuleAFragment";
    //moduleb模块
    String AROUTER_ModuleBActivity = "/moduleb/ModuleBActivity";
    String AROUTER_ModuleBFragment = "/moduleb/ModuleBFragment";
    //modulec模块
    String AROUTER_ModuleCActivity = "/modulec/ModuleCActivity";
    String AROUTER_ModuleCFragment = "/modulec/ModuleCFragment";
    //moduled模块
    String AROUTER_ModuleDActivity = "/moduled/ModuleDActivity";
    String AROUTER_SettingActivity = "/moduled/SettingActivity";
    String AROUTER_AboutUsActivity = "/moduled/AboutUsActivity";
    String AROUTER_ChangePasswordActivity = "/moduled/ChangePasswordActivity";
    String AROUTER_FeedbackActivity = "/moduled/FeedbackActivity";
    String AROUTER_NotificationActivity = "/moduled/NotificationActivity";
    String AROUTER_PersonalInfoActivity = "/moduled/PersonalInfoActivity";
    String AROUTER_TestRoomDatabaseActivity = "/moduled/TestRoomDatabaseActivity";
    String AROUTER_ModuleDFragment = "/moduled/ModuleDFragment";
}
