package com.mythmayor.basicproject.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.request.LoginRequest;
import com.mythmayor.basicproject.response.LoginResponse;
import com.mythmayor.basicproject.utils.http.HttpUtil;

/**
 * Created by mythmayor on 2020/7/8.
 */
public class UserInfoManager {

    public static void saveAccountInfo(Context context, LoginRequest account) {
        if (account == null || TextUtils.isEmpty(account.getUsername()) || TextUtils.isEmpty(account.getPassword())) {
            return;
        }
        String accountInfo = HttpUtil.mGson.toJson(account);
        String encryptAccountInfo = AESUtil.encrypt(MyConstant.AES_KEY, accountInfo);
        if (!TextUtils.isEmpty(encryptAccountInfo)) {
            PrefUtil.putString(context, PrefUtil.SP_ACCOUNT, encryptAccountInfo);
        }
    }

    public static LoginRequest getAccountInfo(Context context) {
        try {
            String accountInfo = PrefUtil.getString(context, PrefUtil.SP_ACCOUNT, "");
            if (TextUtils.isEmpty(accountInfo)) {
                return null;
            }
            String decryptAccountInfo = AESUtil.decrypt(MyConstant.AES_KEY, accountInfo);
            LoginRequest account = HttpUtil.mGson.fromJson(decryptAccountInfo, LoginRequest.class);
            return account;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取登录信息
     */
    public static LoginResponse.DataBean getLoginInfo(Context context) {
        LoginResponse bean = HttpUtil.mGson.fromJson(PrefUtil.getString(context, PrefUtil.SP_LOGIN_INFO, ""), LoginResponse.class);
        if (null != bean) {
            return bean.getData();
        } else {
            return null;
        }
    }
}
