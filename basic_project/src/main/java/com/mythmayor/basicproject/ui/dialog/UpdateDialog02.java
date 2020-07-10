package com.mythmayor.basicproject.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mythmayor.basicproject.R;


/**
 * Created by mythm on 2020/6/30.
 */
public class UpdateDialog02 extends Dialog {

    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器
    private TextView tvYes;
    private TextView tvNo;
    private String mContent;
    private boolean mCancel;
    private TextView tvDialogMy;

    public UpdateDialog02(Context context, String content, boolean cancel) {
        super(context, R.style.dialog_update_theme);
        mContent = content;
        mCancel = cancel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update02);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(mCancel);
        setCancelable(mCancel);
        /** 初始化控件 */
        initView();
        /** 处理用户输入 */
        handleUserInput();
    }

    private void initView() {
        tvDialogMy = (TextView) findViewById(R.id.tv_dialog_my);
        tvYes = (TextView) findViewById(R.id.tv_yes);
        tvNo = (TextView) findViewById(R.id.tv_no);
        tvDialogMy.setText(mContent);
    }

    private void handleUserInput() {
        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                }
            }
        });
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noOnclickListener != null) {
                    noOnclickListener.onNoClick();
                }
            }
        });
    }

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
        void onYesClick();
    }

    public interface onNoOnclickListener {
        void onNoClick();
    }
}
