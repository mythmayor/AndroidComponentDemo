package com.mythmayor.modulea.ui.activity;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BaseModuleMainActivity;
import com.mythmayor.modulea.ModuleAFragment;

@Route(path = MyConstant.AROUTER_ModuleAActivity)
public class ModuleAActivity extends BaseModuleMainActivity {

    @Override
    public Fragment getFragment() {
        return new ModuleAFragment();
    }
}
