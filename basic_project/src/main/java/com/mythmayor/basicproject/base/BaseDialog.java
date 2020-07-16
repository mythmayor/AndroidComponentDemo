package com.mythmayor.basicproject.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.mythmayor.basicproject.R;

/**
 * Created by mythmayor on 2020/6/30.
 * Dialog基类
 */
public abstract class BaseDialog extends Dialog {

    public onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    public onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器

    public BaseDialog(Context context) {
        super(context, R.style.dialog_base);
    }

    public BaseDialog(Context context,int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (0 != getLayoutResId()) {
            setContentView(getLayoutResId());
        }
        //初始化控件
        initView();
        //初始化事件
        initEvent();
        //初始化数据
        initData();
        //不获取焦点
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        //不可取消
        //setCanceledOnTouchOutside(false);
        //setCancelable(false);
    }

    /**
     * 获取布局资源ID
     */
    protected abstract int getLayoutResId();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initEvent();

    /**
     * 初始化事件
     */
    protected abstract void initData();

    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param onNoOnclickListener
     */
    public void setNoOnclickListener(onNoOnclickListener onNoOnclickListener) {
        this.noOnclickListener = onNoOnclickListener;
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param onYesOnclickListener
     */
    public void setYesOnclickListener(onYesOnclickListener onYesOnclickListener) {
        this.yesOnclickListener = onYesOnclickListener;
    }

    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onYesOnclickListener {
        void onYesClick(Object o);
    }

    public interface onNoOnclickListener {
        void onNoClick(Object o);
    }
}
