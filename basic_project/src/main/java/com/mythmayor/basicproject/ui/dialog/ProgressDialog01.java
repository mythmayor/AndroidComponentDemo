package com.mythmayor.basicproject.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import com.mythmayor.basicproject.R;
import com.mythmayor.basicproject.base.BaseDialog;
import com.mythmayor.basicproject.ui.view.ChrysanthemumView;

import java.lang.ref.WeakReference;

/**
 * Created by mythmayor on 2020/7/16.
 * 进度条弹窗1
 */
public class ProgressDialog01 extends BaseDialog {

    private ChrysanthemumView cvloading;
    private TextView tvcontent;
    private WeakReference<Context> mContext;
    private volatile static ProgressDialog01 sDialog;
    private CharSequence mContent = "加载中";

    private ProgressDialog01(Context context, CharSequence message) {
        super(context);
        mContext = new WeakReference<>(context);
        mContent = message;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.dialog_progress01;
    }

    @Override
    protected void initView() {
        cvloading = (ChrysanthemumView) findViewById(R.id.cv_loading);
        tvcontent = (TextView) findViewById(R.id.tv_content);
    }

    @Override
    protected void initEvent() {
        //不获取焦点
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        //不可取消
        //setCanceledOnTouchOutside(false);
        //setCancelable(false);
    }

    @Override
    protected void initData() {
        tvcontent.setText(mContent);
        cvloading.startAnimation();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (null != cvloading) {
            cvloading.stopAnimation();
            cvloading.detachView();
        }
    }

    public static void show(Context context) {
        show(context, null);
    }

    public static void show(Context context, CharSequence message) {
        show(context, message, false);
    }

    public static void show(Context context, CharSequence message, boolean cancelable) {
        if (sDialog != null && sDialog.isShowing()) {
            //sDialog.dismiss();
            return;
        }
        if (!(context instanceof Activity)) {
            return;
        }
        sDialog = new ProgressDialog01(context, message);
        sDialog.setCancelable(cancelable);
        if (sDialog != null && !sDialog.isShowing() && !((Activity) context).isFinishing()) {
            sDialog.show();
        }
    }

    public static void disappear() {
        if (sDialog != null && sDialog.isShowing()) {
            sDialog.dismiss();
        }
        sDialog = null;
    }
}
