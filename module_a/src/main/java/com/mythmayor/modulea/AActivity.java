package com.mythmayor.modulea;

import android.content.Intent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BaseActivity;

@Route(path = MyConstant.AROUTER_AActivity)
public class AActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_a;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData(Intent intent) {

    }
}
