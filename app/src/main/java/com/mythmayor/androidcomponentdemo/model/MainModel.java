package com.mythmayor.androidcomponentdemo.model;

import com.mythmayor.basicproject.request.UserInfoRequest;
import com.mythmayor.basicproject.response.UserInfoResponse;
import com.mythmayor.basicproject.utils.http.RetrofitClient;
import com.mythmayor.androidcomponentdemo.contract.MainContract;

import io.reactivex.rxjava3.core.Observable;

/**
 * Created by mythmayor on 2020/7/8.
 * 首页Model
 */
public class MainModel implements MainContract.Model {
    @Override
    public Observable<UserInfoResponse> getUserInfo(UserInfoRequest request) {
        return RetrofitClient.getInstance().getHttpService().getUserInfo(request.getUsername(), request.getPassword());
    }
}
