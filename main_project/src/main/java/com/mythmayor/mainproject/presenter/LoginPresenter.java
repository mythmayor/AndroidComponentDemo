package com.mythmayor.mainproject.presenter;

import com.mythmayor.basicproject.base.BasePresenter;
import com.mythmayor.basicproject.itype.NetCallback;
import com.mythmayor.basicproject.request.LoginRequest;
import com.mythmayor.basicproject.response.LoginResponse;
import com.mythmayor.basicproject.utils.LogUtil;
import com.mythmayor.basicproject.utils.net.NetUtil;
import com.mythmayor.basicproject.utils.net.RxScheduler;
import com.mythmayor.mainproject.contract.LoginContract;
import com.mythmayor.mainproject.model.LoginModel;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import okhttp3.Call;

/**
 * Created by mythmayor on 2020/6/30.
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
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(@NonNull LoginResponse resp) {
                        mView.onSuccess(resp);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onError(e.getMessage());
                        mView.hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });
    }

    private void useOkHttpUtils(LoginRequest request) {
        mView.showLoading();
        NetUtil.login2(request, new NetCallback() {
            @Override
            public void onSuccess(String response, int id) {
                mView.hideLoading();
                LogUtil.i(response);
                LoginResponse resp = NetUtil.mGson.fromJson(response, LoginResponse.class);
                mView.onSuccess(resp);
            }

            @Override
            public void onFailed(Call call, Exception e, int id) {
                mView.hideLoading();
                mView.onError(e.getMessage());
            }
        });
    }
}
