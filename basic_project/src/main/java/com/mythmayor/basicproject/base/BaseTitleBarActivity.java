package com.mythmayor.basicproject.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.mythmayor.basicproject.R;
import com.mythmayor.basicproject.utils.CommonUtil;


/**
 * Created by mythmayor on 2020/6/30.
 * 顶部标题栏Activity基类
 */
public abstract class BaseTitleBarActivity extends BaseActivity {

    public ViewGroup titlebar;
    public LinearLayout llcontent;
    private TextView tvcenter;
    private TextView tvleft;
    private TextView tvright;
    private ImageButton ibleft;
    private ImageButton ibright;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_base_title_bar;
    }

    @Override
    protected void initView() {
        //ImmersionBar.with(this).statusBarDarkFont(true).titleBarMarginTop(R.id.view_blank).init();
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.color_white).fitsSystemWindows(true).init();
        titlebar = (ViewGroup) findViewById(R.id.title_bar_root);
        llcontent = (LinearLayout) findViewById(R.id.ll_content);
        tvcenter = (TextView) findViewById(R.id.tv_center);
        tvleft = (TextView) findViewById(R.id.tv_left);
        tvright = (TextView) findViewById(R.id.tv_right);
        ibleft = (ImageButton) findViewById(R.id.ib_left);
        ibright = (ImageButton) findViewById(R.id.ib_right);
        setTitleBar();
        int layoutResId = getSubLayoutResId();
        if (layoutResId != 0) {
            setContentLayout(layoutResId);
            initSubView(llcontent);
        }
    }

    @Override
    protected void initEvent() {
        ibleft.setOnClickListener(this);
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

    public abstract void setTitleBar();

    //隐藏TitleBar
    protected void hideTitleBar() {
        if (titlebar.getVisibility() == View.VISIBLE) {
            titlebar.setVisibility(View.GONE);
        }
    }

    //获取TitleBar
    protected ViewGroup getTitleBarLayout() {
        return titlebar;
    }

    protected void setTopTitle(boolean visible, String title) {
        tvcenter.setVisibility(visible ? View.VISIBLE : View.GONE);
        tvcenter.setText(title);
    }

    protected void setLeftImage(boolean visible, int resId) {
        ibleft.setVisibility(visible ? View.VISIBLE : View.GONE);
        ibleft.setImageResource(resId);
    }

    protected void setLeftText(boolean visible, String text) {
        tvleft.setVisibility(visible ? View.VISIBLE : View.GONE);
        tvleft.setText(text);
    }

    protected void setRightImage(boolean visible, int resId) {
        ibright.setVisibility(visible ? View.VISIBLE : View.GONE);
        ibright.setImageResource(resId);
    }

    protected void setRightText(boolean visible, String text) {
        tvright.setVisibility(visible ? View.VISIBLE : View.GONE);
        tvright.setText(text);
    }

    protected View getLeftImageButton() {
        return ibleft;
    }

    protected View getLeftTextView() {
        return tvleft;
    }

    protected View getRightImageButton() {
        return ibright;
    }

    protected View getRightTextView() {
        return tvright;
    }

    @Override
    public void onClick(View v) {
        if (v == ibleft) {
            finish();
        }
    }

    protected ProgressDialog mProgressDialog;

    protected void showLoadingDialog(String title) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setTitle(CommonUtil.getString(R.string.dialog_text));
        mProgressDialog.setMessage(title);
        mProgressDialog.show();
    }

    protected void dismissLoadingDialog() {
        mProgressDialog.dismiss();
    }
}
