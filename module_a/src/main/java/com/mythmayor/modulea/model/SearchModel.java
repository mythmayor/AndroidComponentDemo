package com.mythmayor.modulea.model;

import com.mythmayor.basicproject.request.UserInfoRequest;
import com.mythmayor.basicproject.response.UserInfoResponse;
import com.mythmayor.basicproject.utils.http.RetrofitClient;
import com.mythmayor.modulea.contract.SearchContract;

import io.reactivex.rxjava3.core.Observable;

/**
 * Created by mythmayor on 2020/7/15.
 */
public class SearchModel implements SearchContract.Model {
    @Override
    public Observable<UserInfoResponse> getUserInfo(UserInfoRequest request) {
        return RetrofitClient.getInstance().getHttpService().getUserInfo(request.getUsername(), request.getPassword());
    }
}
