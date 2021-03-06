package com.mythmayor.androidcomponentdemo.presenter;

import com.mythmayor.androidcomponentdemo.contract.RegisterContract;
import com.mythmayor.androidcomponentdemo.model.RegisterModel;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BasePresenter;
import com.mythmayor.basicproject.request.RegisterRequest;
import com.mythmayor.basicproject.response.RegisterResponse;
import com.mythmayor.basicproject.utils.http.RxScheduler;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * Created by mythmayor on 2020/7/7.
 * 注册Presenter
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {

    private RegisterContract.Model mModel;

    public RegisterPresenter() {
        mModel = new RegisterModel();
    }

    @Override
    public void register(RegisterRequest request) {
        //View是否绑定，如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        useRetrofit(request);
    }

    private void useRetrofit(RegisterRequest request) {
        mModel.register(request)
                .compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())//解决内存泄漏
                .subscribe(new Observer<RegisterResponse>() {//这里需要在build.gradle中指定jdk版本，否则会报错
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mView.showLoading(MyConstant.URL_LOGIN);
                    }

                    @Override
                    public void onNext(@NonNull RegisterResponse resp) {
                        mView.onSuccess(MyConstant.URL_LOGIN, resp);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onError(MyConstant.URL_LOGIN, e.getMessage());
                        mView.hideLoading(MyConstant.URL_LOGIN);
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading(MyConstant.URL_LOGIN);
                    }
                });
    }
}
