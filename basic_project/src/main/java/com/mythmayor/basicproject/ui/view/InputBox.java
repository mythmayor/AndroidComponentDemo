package com.mythmayor.basicproject.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mythmayor.basicproject.R;

/**
 * Created by mythmayor on 2020/7/15.
 * 输入框
 */
public class InputBox extends FrameLayout {

    public InputBox(@NonNull Context context) {
        this(context, null);
    }

    public InputBox(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputBox(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //加载布局
        LayoutInflater.from(context).inflate(R.layout.layout_input_box, this);
        initView();//初始化View
        initAttrs(context, attrs);
        initEvent();//初始化事件
    }

    private void initView() {

    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavigationItemView);

    }

    private void initEvent() {

    }
}
