package com.mythmayor.basicproject.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mythmayor.basicproject.R;
import com.mythmayor.basicproject.itype.OnMyClickListener;

/**
 * Created by mythmayor on 2020/6/30.
 * 下载更新弹窗
 */
public class DownloadDialog extends AlertDialog {
    private Context mContext;
    private TextView tvprogress;
    private ProgressBar mProgressBar;
    private View view;
    private TextView tvcancel;
    private OnMyClickListener mListener;
    private boolean mForceUpdate;

    public DownloadDialog(Context context, boolean force, OnMyClickListener listener) {
        super(context);
        this.mContext = context;
        this.mForceUpdate = force;
        this.mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置对话框样式
        setStyle();
        //初始化控件
        initView();
    }

    private void initView() {
        view = View.inflate(mContext, R.layout.dialog_download, null);
        tvprogress = (TextView) view.findViewById(R.id.tv_progress);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        tvcancel = (TextView) view.findViewById(R.id.tv_cancel);
        if (mForceUpdate) {//强制更新
            tvcancel.setVisibility(View.GONE);
        } else {
            tvcancel.setVisibility(View.VISIBLE);
        }
        tvcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onMyClick(tvcancel);
            }
        });
        setContentView(view);
    }

    private void setStyle() {
        //设置对话框不可取消  false
        this.setCancelable(false);
        //设置触摸对话框外面不可取消
        this.setCanceledOnTouchOutside(false);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        //获得应用窗口大小
        WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
        //设置对话框居中显示
        layoutParams.gravity = Gravity.CENTER;
        //设置对话框宽度为屏幕的6/7
        layoutParams.width = (displaymetrics.widthPixels / 7) * 6;
    }

    //设置进度条
    public void setProgress(int progress) {
        tvprogress.setText(progress + "%");
        mProgressBar.setProgress(progress);
    }
}
