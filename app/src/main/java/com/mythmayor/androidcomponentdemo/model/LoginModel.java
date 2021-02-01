package com.mythmayor.androidcomponentdemo.model;

import com.mythmayor.androidcomponentdemo.contract.LoginContract;
import com.mythmayor.basicproject.request.LoginRequest;
import com.mythmayor.basicproject.response.LoginResponse;
import com.mythmayor.basicproject.utils.http.HttpUtil;
import com.mythmayor.basicproject.utils.http.RetrofitClient;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by mythmayor on 2020/6/30.
 * 登录Model
 */
public class LoginModel implements LoginContract.Model {
    @Override
    public Observable<LoginResponse> login(LoginRequest request) {//POST FORM
        return RetrofitClient.getInstance().getHttpService().login(request.getUsername(), request.getPassword());
    }

    @Override
    public Observable<LoginResponse> login2(LoginRequest request) {//POST JSON
        RequestBody body = FormBody.create(HttpUtil.mGson.toJson(request), MediaType.parse("application/json; charset=utf-8"));
        return RetrofitClient.getInstance().getHttpService().login2(body);
    }

    @Override
    public Observable<LoginResponse> login3(LoginRequest request) {//POST FORM
        Map<String, Object> params = new HashMap<>();
        params.put("username", request.getUsername());
        params.put("password", request.getPassword());
        return RetrofitClient.getInstance().getHttpService().login3(params);
    }
}