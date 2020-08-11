package com.mythmayor.basicproject.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mythmayor.basicproject.R;

/**
 * Created by mythmayor on 2020/8/11.
 */
public class TopTitleBar extends FrameLayout {

    public TextView tvleft;
    public ImageButton ibleft;
    public TextView tvright;
    public ImageButton ibright;
    public TextView tvcenter;
    public RelativeLayout titlebarroot;

    public TopTitleBar(@NonNull Context context) {
        this(context, null);
    }

    public TopTitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopTitleBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //加载布局
        LayoutInflater.from(context).inflate(R.layout.layout_top_title_bar, this);
        initView();//初始化View
        initAttrs(context, attrs);
        initEvent();//初始化事件
    }

    private void initView() {
        tvleft = (TextView) findViewById(R.id.tv_left);
        ibleft = (ImageButton) findViewById(R.id.ib_left);
        tvright = (TextView) findViewById(R.id.tv_right);
        ibright = (ImageButton) findViewById(R.id.ib_right);
        tvcenter = (TextView) findViewById(R.id.tv_center);
        titlebarroot = (RelativeLayout) findViewById(R.id.title_bar_root);
    }

    private void initAttrs(Context context, AttributeSet attrs) {

    }

    private void initEvent() {

    }

    //展示TopTitleBar
    public void showTitleBar() {
        titlebarroot.setVisibility(View.VISIBLE);
    }

    //隐藏TopTitleBar
    public void hideTitleBar() {
        titlebarroot.setVisibility(View.GONE);

    }

    public void setTopTitle(boolean visible, String title) {
        tvcenter.setVisibility(visible ? View.VISIBLE : View.GONE);
        tvcenter.setText(title);
    }

    public void setLeftImage(boolean visible, int resId) {
        ibleft.setVisibility(visible ? View.VISIBLE : View.GONE);
        ibleft.setImageResource(resId);
    }

    public void setLeftText(boolean visible, String text) {
        tvleft.setVisibility(visible ? View.VISIBLE : View.GONE);
        tvleft.setText(text);
    }

    public void setRightImage(boolean visible, int resId) {
        ibright.setVisibility(visible ? View.VISIBLE : View.GONE);
        ibright.setImageResource(resId);
    }

    public void setRightText(boolean visible, String text) {
        tvright.setVisibility(visible ? View.VISIBLE : View.GONE);
        tvright.setText(text);
    }

    //获取TitleBar
    public RelativeLayout getTitleBarLayout() {
        return titlebarroot;
    }

    public TextView getLeftTextView() {
        return tvleft;
    }

    public ImageButton getLeftImageButton() {
        return ibleft;
    }

    public TextView getRightTextView() {
        return tvright;
    }

    public ImageButton getRightImageButton() {
        return ibright;
    }

    public TextView getCenterTextView() {
        return tvcenter;
    }
}
