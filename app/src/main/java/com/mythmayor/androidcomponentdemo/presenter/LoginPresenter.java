package com.mythmayor.androidcomponentdemo.presenter;

import com.mythmayor.androidcomponentdemo.contract.LoginContract;
import com.mythmayor.androidcomponentdemo.model.LoginModel;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BasePresenter;
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
 * 登录Presenter
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private LoginContract.Model mModel;

    public LoginPresenter() {
        mModel = new LoginModel();
    }

    @Override
    public void login(LoginRequest request) {
        //View是否绑定，如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        useRetrofit(request);
        //useOkHttpUtils(request);
    }

    private void useRetrofit(LoginRequest request) {
        mModel.login(request)
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
        mModel.login2(request)
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
        mModel.login3(request)
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
