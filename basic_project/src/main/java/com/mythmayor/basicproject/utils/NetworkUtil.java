package com.mythmayor.basicproject.utils;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import com.mythmayor.basicproject.receiver.NetworkBroadcastReceiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by mythmayor on 2020/6/30.
 * 网络状态工具类
 * <p>
 * 使用说明：
 * 1.getNetworkState方法用来获取当前的网络状态：NETWORK_NONE、NETWORK_MOBILE、NETWORK_WIFI
 * 2.initNetworkAvailableListener与initNetworkStateListener方法用来注册网络监听的广播，
 * 同时会返回当前网络是否可用和当前网络状态
 * 3.最后记得调用release方法进行资源释放（一般在Activity的onDestroy方法中调用）
 */
public class NetworkUtil {

    public static NetworkChangeListener mListener;
    private static NetworkBroadcastReceiver netBroadcastReceiver;
    private static boolean mReceiverRegisted = false;//判断广播是否注册
    /**
     * 没有网络
     */
    public static final int NETWORK_NONE = -1;
    /**
     * 移动网络
     */
    public static final int NETWORK_MOBILE = 0;
    /**
     * 无线网络
     */
    public static final int NETWORK_WIFI = 1;

    public static int getNetworkState(Context context) {
        //得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        //如果网络连接，判断该网络类型
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return NETWORK_WIFI;//wifi
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return NETWORK_MOBILE;//mobile
            }
        } else {
            //网络异常
            return NETWORK_NONE;
        }
        return NETWORK_NONE;
    }

    /**
     * 初始化网络监听
     *
     * @param activity
     * @param listener
     * @return 当前有无网络，true有网络 false无网络
     */
    public static boolean initNetworkAvailableListener(Activity activity, NetworkChangeListener listener) {
        mListener = listener;
        //Android 7.0以上需要动态注册
        if (!mReceiverRegisted && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //实例化IntentFilter对象
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            netBroadcastReceiver = new NetworkBroadcastReceiver();
            //注册广播接收
            activity.registerReceiver(netBroadcastReceiver, filter);
            mReceiverRegisted = true;
        }
        return isNetConnect(getNetworkState(activity));
    }

    /**
     * 初始化网络监听
     *
     * @param activity
     * @param listener
     * @return 当前网络状态值
     */
    public static int initNetworkStateListener(Context activity, NetworkChangeListener listener) {
        mListener = listener;
        //Android 7.0以上需要动态注册
        if (!mReceiverRegisted && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //实例化IntentFilter对象
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            netBroadcastReceiver = new NetworkBroadcastReceiver();
            //注册广播接收
            activity.registerReceiver(netBroadcastReceiver, filter);
            mReceiverRegisted = true;
        }
        return getNetworkState(activity);
    }

    public static void release(Context activity) {
        try {
            if (mReceiverRegisted && netBroadcastReceiver != null) {
                activity.unregisterReceiver(netBroadcastReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断有无网络
     *
     * @return true有网络，false无网络
     */
    public static boolean isNetConnect(int netType) {
        if (netType == NETWORK_WIFI) {
            return true;
        } else if (netType == NETWORK_MOBILE) {
            return true;
        } else if (netType == NETWORK_NONE) {
            return false;
        }
        return false;
    }

    /**
     * 判断是否有外网连接（普通方法不能判断外网的网络是否连接，比如连接上局域网）
     *
     * @return
     */
    public static final boolean ping(String ip) {// ping 的地址，可以换成任何一种可靠的外网
        String result = null;
        try {
            Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);// ping网址3次
            // 读取ping的内容，可以不加
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer stringBuffer = new StringBuffer();
            String content = "";
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
            }
            LogUtil.i("------ping-----originIP: " + ip + ", result content: " + stringBuffer.toString());
            // ping的状态
            int status = p.waitFor();
            if (status == 0) {
                result = "success";
                return true;
            } else {
                result = "failed";
            }
        } catch (IOException e) {
            result = "IOException";
        } catch (InterruptedException e) {
            result = "InterruptedException";
        } finally {
            LogUtil.i("------result-----result: " + result);
        }
        return false;
    }

    /**
     * 用于监听网络变化的接口
     */
    public interface NetworkChangeListener {
        void onChangeListener(int status);
    }

}
