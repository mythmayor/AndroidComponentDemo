package com.mythmayor.androidcomponentdemo.viewmodel;

import androidx.lifecycle.ViewModel;

import com.mythmayor.androidcomponentdemo.activity.LoginActivity;
import com.mythmayor.androidcomponentdemo.model.LoginModel;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.itype.HttpCallback;
import com.mythmayor.basicproject.request.LoginRequest;
import com.mythmayor.basicproject.response.LoginResponse;
import com.mythmayor.basicproject.utils.LogUtil;
import com.mythmayor.basicproject.utils.http.HttpUtil;
import com.mythmayor.basicproject.utils.http.RxScheduler;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import okhttp3.Call;

/**
 * Created by mythmayor on 2020/6/30.
 */
public class LoginViewModel extends ViewModel {

    private LoginModel mLoginModel;
    private LoginActivity mView;

    public LoginViewModel() {
        mLoginModel = new LoginModel();
    }

    public void login(LoginActivity view, LoginRequest request) {
        mView = view;
        useRetrofit(request);
        //useOkHttpUtils(request);
    }

    private void useRetrofit(LoginRequest request) {
        mLoginModel.login(request)
                .compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())//解决内存泄漏
                .subscribe(new Observer<LoginResponse>() {//这里需要在build.gradle中指定jdk版本，否则会报错
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mView.showLoading(MyConstant.URL_LOGIN);
                    }

                    @Override
                    public void onNext(@NonNull LoginResponse resp) {
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
        mLoginModel.login2(request)
                .compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())//解决内存泄漏
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull LoginResponse resp) {
                        LogUtil.d("login2 - " + HttpUtil.mGson.toJson(resp));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogUtil.d("login2 - " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
        mLoginModel.login3(request)
                .compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())//解决内存泄漏
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull LoginResponse resp) {
                        LogUtil.d("login3 - " + HttpUtil.mGson.toJson(resp));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogUtil.d("login3 - " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void useOkHttpUtils(LoginRequest request) {
        mView.showLoading(MyConstant.URL_LOGIN);
        HttpUtil.login2(request, new HttpCallback() {
            @Override
            public void onSuccess(String response, int id) {
                mView.hideLoading(MyConstant.URL_LOGIN);
                LogUtil.i(response);
                LoginResponse resp = HttpUtil.mGson.fromJson(response, LoginResponse.class);
                mView.onSuccess(MyConstant.URL_LOGIN, resp);
            }

            @Override
            public void onFailed(Call call, Exception e, int id) {
                mView.hideLoading(MyConstant.URL_LOGIN);
                mView.onError(MyConstant.URL_LOGIN, e.getMessage());
            }
        });
    }
}
