package com.mythmayor.moduleb.viewmodel;

import androidx.lifecycle.ViewModel;

import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.request.UserInfoRequest;
import com.mythmayor.basicproject.response.UserInfoResponse;
import com.mythmayor.basicproject.utils.http.RxScheduler;
import com.mythmayor.moduleb.model.ModuleBFragmentModel;
import com.mythmayor.moduleb.ModuleBFragment;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * Created by mythmayor on 2020/8/10.
 */
public class ModuleBFragmentViewModel extends ViewModel {

    private ModuleBFragmentModel mModel;

    public ModuleBFragmentViewModel() {
        mModel = new ModuleBFragmentModel();
    }

    public void getUserInfo(ModuleBFragment view, UserInfoRequest request) {
        mModel.getUserInfo(request)
                .compose(RxScheduler.Obs_io_main())
                .to(view.bindAutoDispose())//解决内存泄漏
                .subscribe(new Observer<UserInfoResponse>() {//这里需要在build.gradle中指定jdk版本，否则会报错
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        view.showLoading(MyConstant.URL_LOGIN);
                    }

                    @Override
                    public void onNext(@NonNull UserInfoResponse resp) {
                        view.onSuccess(MyConstant.URL_LOGIN, resp);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.onError(MyConstant.URL_LOGIN, e.getMessage());
                        view.hideLoading(MyConstant.URL_LOGIN);
                    }

                    @Override
                    public void onComplete() {
                        view.hideLoading(MyConstant.URL_LOGIN);
                    }
                });
    }
}
