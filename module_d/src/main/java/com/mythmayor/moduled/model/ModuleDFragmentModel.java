package com.mythmayor.moduled.model;

import com.mythmayor.basicproject.request.UserInfoRequest;
import com.mythmayor.basicproject.response.UserInfoResponse;
import com.mythmayor.basicproject.utils.http.RetrofitClient;
import com.mythmayor.moduled.contract.ModuleDFragmentContract;

import io.reactivex.rxjava3.core.Observable;

/**
 * Created by mythmayor on 2020/7/9.
 */
public class ModuleDFragmentModel implements ModuleDFragmentContract.Model {
    @Override
    public Observable<UserInfoResponse> getUserInfo(UserInfoRequest request) {
        return RetrofitClient.getInstance().getHttpService().getUserInfo(request.getUsername(), request.getPassword());
    }
}
