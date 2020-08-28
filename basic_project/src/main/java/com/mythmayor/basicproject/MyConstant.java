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
    //basicproject模块
    String AROUTER_WebViewActivity = "/basicproject/WebViewActivity";
    //mainproject模块
    String AROUTER_AboutUsActivity = "/mainproject/AboutUsActivity";
    String AROUTER_ChangePasswordActivity = "/mainproject/ChangePasswordActivity";
    String AROUTER_FeedbackActivity = "/mainproject/FeedbackActivity";
    String AROUTER_LoginActivity = "/mainproject/LoginActivity";
    String AROUTER_MainActivity = "/mainproject/MainActivity";
    String AROUTER_MapTestActivity = "/mainproject/MapTestActivity";
    String AROUTER_NotificationActivity = "/mainproject/NotificationActivity";
    String AROUTER_PersonalInfoActivity = "/mainproject/PersonalInfoActivity";
    String AROUTER_RegisterActivity = "/mainproject/RegisterActivity";
    String AROUTER_SearchActivity = "/mainproject/SearchActivity";
    String AROUTER_SettingActivity = "/mainproject/SettingActivity";
    String AROUTER_TestRoomDatabaseActivity = "/mainproject/TestRoomDatabaseActivity";
    //modulea模块
    String AROUTER_AActivity = "/modulea/AActivity";
    //moduleb模块
    String AROUTER_BActivity = "/moduleb/BActivity";
    //modulec模块
    String AROUTER_CActivity = "/modulec/CActivity";
    //moduled模块
    String AROUTER_DActivity = "/moduled/DActivity";

}
