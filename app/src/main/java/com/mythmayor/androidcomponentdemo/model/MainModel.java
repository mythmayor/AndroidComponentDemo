package com.mythmayor.androidcomponentdemo.model;

import com.mythmayor.basicproject.request.UserInfoRequest;
import com.mythmayor.basicproject.response.UserInfoResponse;
import com.mythmayor.basicproject.utils.http.RetrofitClient;

import io.reactivex.rxjava3.core.Observable;

/**
 * Created by mythmayor on 2020/7/8.
 * 首页Model
 */
public class MainModel {

    public Observable<UserInfoResponse> getUserInfo(UserInfoRequest request) {
        return RetrofitClient.getInstance().getHttpService().getUserInfo(request.getUsername(), request.getPassword());
    }
}
