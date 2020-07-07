package com.mythmayor.basicproject.utils;

import android.util.Log;

import java.util.Locale;

/**
 * Created by mythmayor on 2020/6/30.
 * 日志工具类
 */
public class LogUtil {

    private static boolean LOGALL = true;
    private static boolean LOGV = LOGALL;
    private static boolean LOGD = LOGALL;
    private static boolean LOGI = LOGALL;
    private static boolean LOGW = LOGALL;
    private static boolean LOGE = LOGALL;
    private static boolean LOGLONGMSG = LOGALL;
    private static final String DEFAULT_TAG = "❤驭霖·骏泊☆Myth.Mayor❤";

    public static void v(String msg) {
        if (LOGV) {
            Log.v(getTag(DEFAULT_TAG), buildMessage(msg));
        }
    }

    public static void d(String msg) {
        if (LOGD) {
            Log.d(getTag(DEFAULT_TAG), buildMessage(msg));
        }
    }

    public static void i(String msg) {
        if (LOGI) {
            Log.i(getTag(DEFAULT_TAG), buildMessage(msg));
        }
    }

    public static void w(String msg) {
        if (LOGW) {
            Log.w(getTag(DEFAULT_TAG), buildMessage(msg));
        }
    }

    public static void e(String msg) {
        if (LOGE) {
            Log.e(getTag(DEFAULT_TAG), buildMessage(msg));
        }
    }

    public static void v(String logTag, String msg) {
        if (LOGV) {
            Log.v(getTag(logTag), buildMessage(msg));
        }
    }

    public static void d(String logTag, String msg) {
        if (LOGD) {
            Log.d(getTag(logTag), buildMessage(msg));
        }
    }

    public static void i(String logTag, String msg) {
        if (LOGI) {
            Log.i(getTag(logTag), buildMessage(msg));
        }
    }

    public static void w(String logTag, String msg) {
        if (LOGW) {
            Log.w(getTag(logTag), buildMessage(msg));
        }
    }

    public static void e(String logTag, String msg) {
        if (LOGE) {
            Log.e(getTag(logTag), buildMessage(msg));
        }
    }

    /**
     * 分段打印出较长log文本
     *
     * @param log       原log文本
     * @param showCount 规定每段显示的长度（最好不要超过eclipse限制长度）
     */
    public static void showLogCompletion(String log, int showCount) {
        if (LOGLONGMSG) {
            if (log.length() > showCount) {
                String show = log.substring(0, showCount);
                //System.out.println(show);
                Log.i(DEFAULT_TAG, show + "");
                if ((log.length() - showCount) > showCount) {//剩下的文本还是大于规定长度
                    String partLog = log.substring(showCount, log.length());
                    showLogCompletion(partLog, showCount);
                } else {
                    String surplusLog = log.substring(showCount, log.length());
                    Log.i(DEFAULT_TAG, surplusLog + "");
                }

            } else {
                Log.i(DEFAULT_TAG, log + "");
            }
        }
    }

    /**
     * 分段打印出较长log文本
     *
     * @param log       原log文本
     * @param showCount 规定每段显示的长度（最好不要超过eclipse限制长度）
     */
    public static void showLogCompletion(String logTag, String log, int showCount) {
        if (LOGLONGMSG) {
            if (log.length() > showCount) {
                String show = log.substring(0, showCount);
                //System.out.println(show);
                Log.i(logTag, show + "");
                if ((log.length() - showCount) > showCount) {//剩下的文本还是大于规定长度
                    String partLog = log.substring(showCount, log.length());
                    showLogCompletion(partLog, showCount);
                } else {
                    String surplusLog = log.substring(showCount, log.length());
                    Log.i(logTag, surplusLog + "");
                }

            } else {
                Log.i(logTag, log + "");
            }
        }
    }

    private static String getTag(String logTag) {
        StackTraceElement[] trace = new Throwable().fillInStackTrace()
                .getStackTrace();
        String callingClass = "";
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(LogUtil.class)) {
                callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
                break;
            }
        }
        return logTag + callingClass;
    }

    private static String buildMessage(String msg) {
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();
        String caller = "";
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(LogUtil.class)) {
                caller = trace[i].getMethodName();
                break;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s", Thread.currentThread().getId(), caller, msg);
    }
}
