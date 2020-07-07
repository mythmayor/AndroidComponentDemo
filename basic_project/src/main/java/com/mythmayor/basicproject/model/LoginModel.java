package com.mythmayor.basicproject.model;

import com.mythmayor.basicproject.contract.LoginContract;
import com.mythmayor.basicproject.request.LoginRequest;
import com.mythmayor.basicproject.response.LoginResponse;
import com.mythmayor.basicproject.utils.net.RetrofitClient;

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
