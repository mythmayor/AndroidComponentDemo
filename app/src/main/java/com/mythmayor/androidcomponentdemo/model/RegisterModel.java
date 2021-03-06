package com.mythmayor.androidcomponentdemo.model;

import com.mythmayor.androidcomponentdemo.contract.RegisterContract;
import com.mythmayor.basicproject.request.RegisterRequest;
import com.mythmayor.basicproject.response.RegisterResponse;
import com.mythmayor.basicproject.utils.http.RetrofitClient;

import io.reactivex.rxjava3.core.Observable;

/**
 * Created by mythmayor on 2020/7/7.
 * 注册Model
 */
public class RegisterModel implements RegisterContract.Model {
    @Override
    public Observable<RegisterResponse> register(RegisterRequest request) {
        return RetrofitClient.getInstance().getHttpService().register(request.getUsername(),request.getPassword());
    }
}
