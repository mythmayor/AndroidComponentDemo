package com.mythmayor.basicproject.utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

/**
 * Created by mythmayor on 2020/6/30.
 * Socket工具类
 */
public class SocketUtil {

    private static final String TAG = SocketUtil.class.getSimpleName();
    private static volatile SocketUtil instance;

    private SocketUtil() {
    }

    public static SocketUtil getInstance() {
        if (instance == null) {
            synchronized (SocketUtil.class) {
                if (instance == null) {
                    instance = new SocketUtil();
                }
            }
        }
        return instance;
    }

    private static final int WHAT_SEND_MSG = 0;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_SEND_MSG:

                    break;
                default:
                    break;
            }
        }
    };

    public void deviceInfoReq() {

    }

    public void destroy() {
        mHandler.removeMessages(WHAT_SEND_MSG);
        mHandler = null;
    }
}
