package com.mythmayor.mainproject.viewmodel;

import androidx.lifecycle.ViewModel;

import com.mythmayor.mainproject.model.SettingModel;

/**
 * Created by mythmayor on 2020/8/10.
 */
public class SettingViewModel extends ViewModel {

    private SettingModel mModel;

    public SettingViewModel() {
        mModel = new SettingModel();
    }
}
