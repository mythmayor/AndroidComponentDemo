package com.mythmayor.basicproject.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mythmayor.basicproject.R;

import java.lang.ref.WeakReference;

/**
 * Created by mythmayor on 2020/7/16.
 * 进度条弹窗2
 */
public class ProgressDialog02 extends Dialog implements DialogInterface.OnCancelListener {

    private WeakReference<Context> mContext;
    private volatile static ProgressDialog02 sDialog;

    private ProgressDialog02(Context context, CharSequence message) {
        super(context, R.style.ProgressDialogStyle);
        mContext = new WeakReference<>(context);
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_progress02, null);
        TextView tvmessage = view.findViewById(R.id.tv_message);
        if (!TextUtils.isEmpty(message)) {
            tvmessage.setText(message);
        }
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(view, lp);
        setOnCancelListener(this);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        // 点手机返回键等触发Dialog消失，应该取消正在进行的网络请求等
        Context context = mContext.get();
        if (context != null) {
            //ToastUtil.showToast(context,"cancel");
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
        sDialog = new ProgressDialog02(context, message);
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
