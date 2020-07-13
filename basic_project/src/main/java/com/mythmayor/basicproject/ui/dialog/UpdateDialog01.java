package com.mythmayor.basicproject.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mythmayor.basicproject.R;
import com.mythmayor.basicproject.adapter.UpdateAdapter;

import java.util.List;

/**
 * Created by mythmayor on 2020/6/30.
 * 更新弹窗
 */
public class UpdateDialog01 extends Dialog {

    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器
    private ImageView ivclose;
    private TextView tvversion;
    private TextView tvupdate;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private String mVersion;
    private boolean mForceUpdate;
    private List<String> mContent;
    private UpdateAdapter mAdapter;

    public UpdateDialog01(Context context, String version, boolean force, List<String> content) {
        super(context, R.style.dialog_update_theme);
        mContext = context;
        mVersion = version;
        mForceUpdate = force;
        mContent = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update01);
        //按空白处不能取消动画
        //setCanceledOnTouchOutside(false);
        /** 初始化控件 */
        initView();
        /** 初始化数据 */
        initData();
        /** 处理用户输入 */
        handleUserInput();
    }

    private void initView() {
        ivclose = (ImageView) findViewById(R.id.iv_close);
        tvversion = (TextView) findViewById(R.id.tv_version);
        tvupdate = (TextView) findViewById(R.id.tv_update);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        if (!mForceUpdate) {//不强制更新
            ivclose.setVisibility(View.VISIBLE);
            setCancelable(true);
        } else {
            ivclose.setVisibility(View.INVISIBLE);
            setCancelable(false);
        }
    }

    private void initData() {
        tvversion.setText(mVersion);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new UpdateAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.refreshData(mContent);
    }

    private void handleUserInput() {
        tvupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                }
            }
        });
        ivclose.setOnClickListener(new View.OnClickListener() {
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
