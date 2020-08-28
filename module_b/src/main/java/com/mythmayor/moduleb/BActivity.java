package com.mythmayor.moduleb;

import android.content.Intent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BaseActivity;

@Route(path = MyConstant.AROUTER_BActivity)
public class BActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_b;
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
