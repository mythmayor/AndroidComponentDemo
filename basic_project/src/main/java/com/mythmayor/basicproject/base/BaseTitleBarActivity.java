package com.mythmayor.basicproject.base;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.gyf.immersionbar.ImmersionBar;
import com.mythmayor.basicproject.R;
import com.mythmayor.basicproject.ui.view.TopTitleBar;
import com.mythmayor.basicproject.utils.CommonUtil;


/**
 * Created by mythmayor on 2020/6/30.
 * 顶部标题栏Activity基类
 */
public abstract class BaseTitleBarActivity extends BaseActivity {

    public LinearLayout llroot;
    public TopTitleBar mTopTitleBar;
    public LinearLayout llcontent;

    public static final int BACKGROUND_STYLE_DEFAULT = 1;
    public static final int BACKGROUND_STYLE_WHITE = 2;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_base_title_bar;
    }

    @Override
    protected void initView() {
        //ImmersionBar.with(this).statusBarDarkFont(true).titleBarMarginTop(R.id.view_blank).init();
        //ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.color_white).fitsSystemWindows(true).init();
        llroot = (LinearLayout) findViewById(R.id.ll_root);
        mTopTitleBar = (TopTitleBar) findViewById(R.id.topTitleBar);
        llcontent = (LinearLayout) findViewById(R.id.ll_content);
        if (getBackgroundType() == BACKGROUND_STYLE_WHITE) {//白色背景
            ImmersionBar.with(this).statusBarDarkFont(true).titleBar(R.id.topTitleBar).init();
            llroot.setBackgroundResource(R.drawable.bg_titlebar_white);
            mTopTitleBar.setBackgroundResId(R.drawable.bg_titlebar_white);
            mTopTitleBar.tvleft.setTextColor(CommonUtil.getColor(R.color.color_1F2428));
            mTopTitleBar.tvcenter.setTextColor(CommonUtil.getColor(R.color.color_1F2428));
            mTopTitleBar.tvright.setTextColor(CommonUtil.getColor(R.color.color_1F2428));
        } else {//默认背景
            ImmersionBar.with(this).statusBarDarkFont(false).titleBar(R.id.topTitleBar).init();
            llroot.setBackgroundResource(R.drawable.bg_titlebar_default);
            mTopTitleBar.setBackgroundResId(R.drawable.bg_titlebar_default);
            mTopTitleBar.tvleft.setTextColor(CommonUtil.getColor(R.color.color_white));
            mTopTitleBar.tvcenter.setTextColor(CommonUtil.getColor(R.color.color_white));
            mTopTitleBar.tvright.setTextColor(CommonUtil.getColor(R.color.color_white));
        }
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

    public int getBackgroundType() {
        return BACKGROUND_STYLE_DEFAULT;
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
