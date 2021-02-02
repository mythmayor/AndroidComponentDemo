package com.mythmayor.androidcomponentdemo.viewmodel;

import androidx.lifecycle.ViewModel;

import com.mythmayor.androidcomponentdemo.activity.RegisterActivity;
import com.mythmayor.androidcomponentdemo.model.RegisterModel;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.request.RegisterRequest;
import com.mythmayor.basicproject.response.RegisterResponse;
import com.mythmayor.basicproject.utils.http.RxScheduler;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * Created by mythmayor on 2020/8/10.
 */
public class RegisterViewModel extends ViewModel {

    private RegisterModel mModel;

    public RegisterViewModel() {
        mModel = new RegisterModel();
    }

    public void register(RegisterActivity view, RegisterRequest request) {
        mModel.register(request)
                .compose(RxScheduler.Obs_io_main())
                .to(view.bindAutoDispose())//解决内存泄漏
                .subscribe(new Observer<RegisterResponse>() {//这里需要在build.gradle中指定jdk版本，否则会报错
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        view.showLoading(MyConstant.URL_LOGIN);
                    }

                    @Override
                    public void onNext(@NonNull RegisterResponse resp) {
                        view.onSuccess(MyConstant.URL_LOGIN,resp);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.onError(MyConstant.URL_LOGIN,e.getMessage());
                        view.hideLoading(MyConstant.URL_LOGIN);
                    }

                    @Override
                    public void onComplete() {
                        view.hideLoading(MyConstant.URL_LOGIN);
                    }
                });
    }
}
