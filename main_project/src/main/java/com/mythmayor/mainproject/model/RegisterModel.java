package com.mythmayor.mainproject.model;

import com.mythmayor.basicproject.request.RegisterRequest;
import com.mythmayor.basicproject.response.RegisterResponse;
import com.mythmayor.basicproject.utils.http.RetrofitClient;
import com.mythmayor.mainproject.contract.RegisterContract;

import io.reactivex.rxjava3.core.Observable;

/**
 * Created by mythmayor on 2020/7/7.
 */
public class RegisterModel implements RegisterContract.Model {
    @Override
    public Observable<RegisterResponse> register(RegisterRequest request) {
        return RetrofitClient.getInstance().getHttpService().register(request.getUsername(),request.getPassword());
    }
}
