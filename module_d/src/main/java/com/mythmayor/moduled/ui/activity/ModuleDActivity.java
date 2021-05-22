package com.mythmayor.moduled.ui.activity;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BaseModuleMainActivity;
import com.mythmayor.moduled.ModuleDFragment;

@Route(path = MyConstant.AROUTER_ModuleDActivity)
public class ModuleDActivity extends BaseModuleMainActivity {

    @Override
    public Fragment getFragment() {
        return new ModuleDFragment();
    }
}
