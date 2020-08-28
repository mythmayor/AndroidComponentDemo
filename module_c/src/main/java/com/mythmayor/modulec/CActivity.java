package com.mythmayor.modulec;

import android.content.Intent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BaseActivity;

@Route(path = MyConstant.AROUTER_CActivity)
public class CActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_c;
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
