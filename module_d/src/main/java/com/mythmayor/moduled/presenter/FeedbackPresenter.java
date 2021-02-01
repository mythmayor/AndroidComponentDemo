package com.mythmayor.moduled.presenter;

import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BasePresenter;
import com.mythmayor.basicproject.request.UserInfoRequest;
import com.mythmayor.basicproject.response.UserInfoResponse;
import com.mythmayor.basicproject.utils.http.RxScheduler;
import com.mythmayor.moduled.contract.FeedbackContract;
import com.mythmayor.moduled.model.FeedbackModel;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * Created by mythmayor on 2020/7/13.
 */
public class FeedbackPresenter extends BasePresenter<FeedbackContract.View> implements FeedbackContract.Presenter {

    private FeedbackContract.Model mModel;

    public FeedbackPresenter() {
        mModel = new FeedbackModel();
    }

    @Override
    public void getUserInfo(UserInfoRequest request) {
        if (!isViewAttached()) {//View是否绑定，如果没有绑定，就不执行网络请求
            return;
        }
        userRetrofit(request);
    }

    private void userRetrofit(UserInfoRequest request) {
        mModel.getUserInfo(request)
                .compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())//解决内存泄漏
                .subscribe(new Observer<UserInfoResponse>() {//这里需要在build.gradle中指定jdk版本，否则会报错
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mView.showLoading(MyConstant.URL_LOGIN);
                    }

                    @Override
                    public void onNext(@NonNull UserInfoResponse userInfoResponse) {
                        mView.onSuccess(MyConstant.URL_LOGIN, userInfoResponse);
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
