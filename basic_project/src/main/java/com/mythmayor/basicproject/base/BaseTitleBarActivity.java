package com.mythmayor.basicproject.base;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.gyf.immersionbar.ImmersionBar;
import com.mythmayor.basicproject.R;
import com.mythmayor.basicproject.ui.view.TopTitleBar;


/**
 * Created by mythmayor on 2020/6/30.
 * 顶部标题栏Activity基类
 */
public abstract class BaseTitleBarActivity extends BaseActivity {

    public TopTitleBar mTopTitleBar;
    public LinearLayout llcontent;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_base_title_bar;
    }

    @Override
    protected void initView() {
        //ImmersionBar.with(this).statusBarDarkFont(true).titleBarMarginTop(R.id.view_blank).init();
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.color_white).fitsSystemWindows(true).init();
        mTopTitleBar = (TopTitleBar) findViewById(R.id.topTitleBar);
        llcontent = (LinearLayout) findViewById(R.id.ll_content);
        setTitleBar(mTopTitleBar);
        int layoutResId = getSubLayoutResId();
        if (layoutResId != 0) {
            setContentLayout(layoutResId);
            initSubView(llcontent);
        }
    }

    @Override
    protected void initEvent() {
        mTopTitleBar.ibleft.setOnClickListener(this);
        initSubEvent();
    }

    @Override
    protected void initData(Intent intent) {
        if (intent != null) {
            initSubData(intent);
        }
    }

    //设置内容布局
    private void setContentLayout(int layoutResId) {
        View view = LayoutInflater.from(this).inflate(layoutResId, llcontent, false);
        llcontent.addView(view);
    }

    public abstract int getSubLayoutResId();

    public abstract void initSubView(View view);

    public abstract void initSubEvent();

    public abstract void initSubData(Intent intent);

    public abstract void setTitleBar(TopTitleBar topTitleBar);

    @Override
    public void onClick(View v) {
        if (v == mTopTitleBar.ibleft) {
            finish();
        }
    }
}
