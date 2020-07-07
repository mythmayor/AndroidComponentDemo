package com.mythmayor.basicproject.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mythmayor.basicproject.R;
import com.mythmayor.basicproject.base.BaseDialog;

/**
 * Created by mythmayor on 2020/6/30.
 * 退出登录Dialog弹窗
 */
public class LogoutDialog extends BaseDialog {

    private TextView tvcontent;
    private TextView tvleft;
    private TextView tvright;
    private String mContent;
    private String mLeftText;
    private String mRightText;
    private boolean mCancelable = true;

    public LogoutDialog(Context context, String content, String leftText, String rightText) {
        super(context);
        mContent = content;
        mLeftText = leftText;
        mRightText = rightText;
    }

    public LogoutDialog(Context context, String content, String leftText, String rightText, boolean cancelable) {
        super(context);
        mContent = content;
        mLeftText = leftText;
        mRightText = rightText;
        mCancelable = cancelable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //不获取焦点
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        //不可取消
        setCanceledOnTouchOutside(mCancelable);
        setCancelable(mCancelable);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.dialog_logout;
    }

    @Override
    protected void initView() {
        tvcontent = (TextView) findViewById(R.id.tv_content);
        tvleft = (TextView) findViewById(R.id.tv_left);
        tvright = (TextView) findViewById(R.id.tv_right);
    }

    @Override
    protected void initEvent() {
        tvleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != noOnclickListener) {
                    noOnclickListener.onNoClick(null);
                }
            }
        });
        tvright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != yesOnclickListener) {
                    yesOnclickListener.onYesClick(null);
                }
            }
        });
    }

    @Override
    protected void initData() {
        tvcontent.setText(mContent);
        tvleft.setText(mLeftText);
        tvright.setText(mRightText);
    }
}
