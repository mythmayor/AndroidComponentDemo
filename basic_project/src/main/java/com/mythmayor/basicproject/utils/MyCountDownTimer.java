package com.mythmayor.basicproject.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.mythmayor.basicproject.R;

/**
 * Created by mythmayor on 2020/6/30.
 * 倒计时Timer工具类
 */
public class MyCountDownTimer extends CountDownTimer {

    private TextView mTextView;
    private int mEnable;
    private int mDisable;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public MyCountDownTimer(long millisInFuture, long countDownInterval, TextView textView, int enable, int disable) {
        super(millisInFuture, countDownInterval);
        mTextView = textView;
        mEnable = enable;
        mDisable = disable;
    }

    //计时过程
    @Override
    public void onTick(long l) {
        long seconds = l / 1000;
        if (l % 1000 > 500) {
            seconds += 1;
        }
        mTextView.setText(seconds + CommonUtil.getString(R.string.retrieve_verify_code2));
        //防止计时过程中重复点击
        mTextView.setClickable(false);
        //设置字体颜色
        mTextView.setTextColor(mDisable);
    }

    //计时完毕的方法
    @Override
    public void onFinish() {
        //重新给Button设置文字
        mTextView.setText(CommonUtil.getString(R.string.retrieve_verify_code));
        //设置可点击
        mTextView.setClickable(true);
        //设置字体颜色
        mTextView.setTextColor(mEnable);
    }
}
