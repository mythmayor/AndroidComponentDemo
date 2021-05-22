package com.mythmayor.modulec.ui.activity;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BaseModuleMainActivity;
import com.mythmayor.modulec.ModuleCFragment;

@Route(path = MyConstant.AROUTER_ModuleCActivity)
public class ModuleCActivity extends BaseModuleMainActivity {

    @Override
    public Fragment getFragment() {
        return new ModuleCFragment();
    }
}
