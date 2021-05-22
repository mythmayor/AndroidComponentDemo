package com.mythmayor.moduleb.ui.activity;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BaseModuleMainActivity;
import com.mythmayor.moduleb.ModuleBFragment;

@Route(path = MyConstant.AROUTER_ModuleBActivity)
public class ModuleBActivity extends BaseModuleMainActivity {

    @Override
    public Fragment getFragment() {
        return new ModuleBFragment();
    }
}
