package com.mythmayor.mainproject.presenter;

import com.mythmayor.basicproject.base.BasePresenter;
import com.mythmayor.mainproject.contract.SettingContract;
import com.mythmayor.mainproject.model.SettingModel;

/**
 * Created by mythmayor on 2020/7/10.
 */
public class SettingPresenter extends BasePresenter<SettingContract.View> implements SettingContract.Presenter {

    private SettingContract.Model mModel;

    public SettingPresenter() {
        mModel = new SettingModel();
    }
}
