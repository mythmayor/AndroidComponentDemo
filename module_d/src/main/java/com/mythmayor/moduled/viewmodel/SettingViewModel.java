package com.mythmayor.moduled.viewmodel;

import androidx.lifecycle.ViewModel;

import com.mythmayor.moduled.model.SettingModel;

/**
 * Created by mythmayor on 2020/8/10.
 */
public class SettingViewModel extends ViewModel {

    private SettingModel mModel;

    public SettingViewModel() {
        mModel = new SettingModel();
    }
}
