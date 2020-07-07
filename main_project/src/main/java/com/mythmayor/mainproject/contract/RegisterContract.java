package com.mythmayor.mainproject.contract;

import com.mythmayor.basicproject.base.BaseView;
import com.mythmayor.basicproject.request.RegisterRequest;
import com.mythmayor.basicproject.response.RegisterResponse;

import io.reactivex.rxjava3.core.Observable;

/**
 * Created by mythmayor on 2020/7/7.
 */
public interface RegisterContract {
    interface Model {
        Observable<RegisterResponse> register(RegisterRequest request);

    }

    interface View extends BaseView {

    }

    interface Presenter {
        void register(RegisterRequest request);
    }
}
