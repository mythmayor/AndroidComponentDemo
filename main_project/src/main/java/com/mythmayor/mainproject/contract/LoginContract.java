package com.mythmayor.mainproject.contract;

import com.mythmayor.basicproject.base.BaseView;
import com.mythmayor.basicproject.request.LoginRequest;
import com.mythmayor.basicproject.response.LoginResponse;

import io.reactivex.rxjava3.core.Observable;

/**
 * Created by mythmayor on 2020/6/30.
 * MVP契约类
 */
public interface LoginContract {

    interface Model {
        Observable<LoginResponse> login(LoginRequest request);
    }

    interface View extends BaseView {

    }

    interface Presenter {
        void login(LoginRequest request);
    }
}
