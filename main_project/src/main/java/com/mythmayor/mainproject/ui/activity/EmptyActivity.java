package com.mythmayor.mainproject.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mythmayor.mainproject.R;

/**
 * Created by mythmayor on 2020/7/10.
 * 用来生成initView方法
 */
public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search_bar);
    }

}
