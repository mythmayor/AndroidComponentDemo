package com.mythmayor.androidcomponentdemo.contract;

import com.mythmayor.basicproject.base.BaseView;
import com.mythmayor.basicproject.request.UserInfoRequest;
import com.mythmayor.basicproject.response.UserInfoResponse;

import io.reactivex.rxjava3.core.Observable;

/**
 * Created by mythmayor on 2020/7/8.
 * MVP契约类
 */
public interface MainContract {
    interface Model {
        Observable<UserInfoResponse> getUserInfo(UserInfoRequest request);
    }

    interface View extends BaseView {
    }

    interface Presenter {
        void getUserInfo(UserInfoRequest request);
    }
}
