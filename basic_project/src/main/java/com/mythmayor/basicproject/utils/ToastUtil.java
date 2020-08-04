package com.mythmayor.basicproject.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.mythmayor.basicproject.BasicApplication;

/**
 * Created by mythmayor on 2020/6/30.
 * Toast工具类
 */

public class ToastUtil {

    protected static Toast mToast = null;
    private static Context mContext = BasicApplication.getInstance().getContext();

    private static String oldMsg;
    private static long oneTime = 0;
    private static long twoTime = 0;

    public static final int DURATION_SHORT = Toast.LENGTH_SHORT;//短的时长(默认)
    public static final int DURATION_LONG = Toast.LENGTH_LONG;//长的时长
    public static final int GRAVITY_BOTTOM = Gravity.BOTTOM;//位置居下(默认)
    public static final int GRAVITY_CENTER = Gravity.CENTER;//位置居中
    public static final int GRAVITY_TOP = Gravity.TOP;//位置居上

    /**
     * 过时方法，存在Bug: 连续多次点击同一个会不展示
     *
     * @deprecated Use {@link #showToast(String message)} instead.
     */
    @Deprecated
    public static void showToast(Context context, String s) {
        if (null == mToast) {
            mToast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
            mToast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    mToast.show();
                }
            } else {
                oldMsg = s;
                mToast.setText(s);
                mToast.show();
            }
        }
        oneTime = twoTime;
    }

    /**
     * 过时方法，存在Bug: 连续多次点击同一个会不展示
     *
     * @deprecated Use {@link #showToast(int msgResId)} instead.
     */
    @Deprecated
    public static void showToast(Context context, int resId) {
        showToast(context, context.getString(resId));
    }


    public static void showToast(String message) {
        showToast(message, GRAVITY_BOTTOM, DURATION_SHORT);
    }

    public static void showToast(int msgResId) {
        showToast(msgResId, GRAVITY_BOTTOM, DURATION_SHORT);
    }

    public static void showToast(String message, int duration) {
        showToast(message, GRAVITY_BOTTOM, duration);
    }

    public static void showToast(int msgResId, int duration) {
        showToast(msgResId, GRAVITY_BOTTOM, duration);
    }


    public static void showToastAtPosition(String message, int gravity) {
        showToast(message, gravity, DURATION_SHORT);
    }

    public static void showToastAtPosition(int msgResId, int gravity) {
        showToast(msgResId, gravity, DURATION_SHORT);
    }

    public static void showToastAtPosition(String message, int duration, int gravity) {
        showToast(message, gravity, duration);
    }

    public static void showToastAtPosition(int msgResId, int duration, int gravity) {
        showToast(msgResId, gravity, duration);
    }

    private static void showToast(String message, int gravity, int duration) {
        if (mContext != null) {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(mContext, message, duration);
            if (gravity == GRAVITY_CENTER) {
                mToast.setGravity(gravity, 0, 0);
            } else {
                mToast.setGravity(gravity, 0, 200);
            }
            mToast.show();
        }
    }

    private static void showToast(int msgResId, int gravity, int duration) {
        if (mContext != null) {
            showToast(mContext.getString(msgResId), gravity, duration);
        }
    }
}
