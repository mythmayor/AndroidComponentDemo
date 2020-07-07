package com.mythmayor.basicproject.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by mythmayor on 2020/6/30.
 * ProgressDialog工具类
 */
public class ProgressDlgUtil {

    private static ProgressDialog progressDlg = null;

    /**
     * 启动进度条
     * @param ctx 当前的activity
     * @param content 进度条显示的信息
     */
    public static void show(Context ctx, String content) {
        try {
            if (null == progressDlg) {
                progressDlg = new ProgressDialog(ctx);
                progressDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
                //progressDlg.setTitle("提示");//设置进度条标题
                progressDlg.setMessage(content);//提示的消息
                progressDlg.setIndeterminate(false);
                progressDlg.setCancelable(false);
                //progressDlg.setIcon(R.drawable.ic_launcher_scale);
                progressDlg.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 结束进度条
     */
    public static void dismiss() {
        try {
            if (null != progressDlg) {
                progressDlg.dismiss();
                progressDlg = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
