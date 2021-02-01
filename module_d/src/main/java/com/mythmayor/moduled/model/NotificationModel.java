package com.mythmayor.moduled.model;

import com.mythmayor.basicproject.request.UserInfoRequest;
import com.mythmayor.basicproject.response.UserInfoResponse;
import com.mythmayor.basicproject.utils.http.RetrofitClient;
import com.mythmayor.moduled.contract.NotificationContract;

import io.reactivex.rxjava3.core.Observable;

/**
 * Created by mythmayor on 2020/7/13.
 */
public class NotificationModel implements NotificationContract.Model {
    @Override
    public Observable<UserInfoResponse> getUserInfo(UserInfoRequest request) {
        return RetrofitClient.getInstance().getHttpService().getUserInfo(request.getUsername(), request.getPassword());
    }
}
