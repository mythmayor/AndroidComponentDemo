package com.mythmayor.moduled;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BaseActivity;

@Route(path = MyConstant.AROUTER_DActivity)
public class DActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_d;
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
