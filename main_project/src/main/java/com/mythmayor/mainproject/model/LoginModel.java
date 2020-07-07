package com.mythmayor.mainproject.model;

import com.mythmayor.basicproject.request.LoginRequest;
import com.mythmayor.basicproject.response.LoginResponse;
import com.mythmayor.basicproject.utils.net.RetrofitClient;
import com.mythmayor.mainproject.contract.LoginContract;

import io.reactivex.rxjava3.core.Observable;

/**
 * Created by mythmayor on 2020/6/30.
 * 登录Model
 */
public class LoginModel implements LoginContract.Model {
    @Override
    public Observable<LoginResponse> login(LoginRequest request) {
        return RetrofitClient.getInstance().getApi().login(request.getUsername(), request.getPassword());
    }
}
