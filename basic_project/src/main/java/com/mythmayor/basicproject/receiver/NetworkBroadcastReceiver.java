package com.mythmayor.basicproject.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.mythmayor.basicproject.itype.BaseListener;
import com.mythmayor.basicproject.itype.ListenerList;

/**
 * Created by mythmayor on 2020/6/30.
 * 网络广播接收者
 */
public class NetworkBroadcastReceiver extends BroadcastReceiver {

    public static final String ACTION_CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";
    //无网络
    public static final int NETWORK_NONE = -1;
    //移动网络
    public static final int NETWORK_MOBILE = 0;
    //无线网络
    public static final int NETWORK_WIFI = 1;
    private static ListenerList mNetworkListenerList;

    public NetworkBroadcastReceiver() {
        if (mNetworkListenerList == null) {
            mNetworkListenerList = new ListenerList();
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
        if (null != networkInfo && networkInfo.isAvailable()) {
            switch (networkInfo.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    setNetworkListener(NETWORK_WIFI);
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    setNetworkListener(NETWORK_MOBILE);
                    break;
                default:
                    break;
            }
        } else {
            setNetworkListener(NETWORK_NONE);
        }
    }

    private void setNetworkListener(int networkWifi) {
        if (mNetworkListenerList != null) {
            BaseListener[] listeners = mNetworkListenerList.getAll();
            if (listeners != null && listeners.length > 0) {
                for (int i = 0; i < listeners.length; i++) {
                    BaseListener baseListener = listeners[i];
                    NetworkListener listener = (NetworkListener) baseListener;
                    listener.onNetworkListener(networkWifi);
                }
            }
        }
    }

    public void addNetworkListener(NetworkListener listener) {
        if (mNetworkListenerList != null) {
            mNetworkListenerList.add(listener);
        }
    }

    public void removeNetworkListener(NetworkListener listener) {
        if (mNetworkListenerList != null) {
            mNetworkListenerList.remove(listener);
        }
    }

    /**
     * 用于监听网络变化的接口
     */
    public interface NetworkListener extends BaseListener {
        void onNetworkListener(int status);
    }

    public int getNetworkState(Context context) {
        //得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //如果网络连接，判断该网络类型
        if (null != networkInfo && networkInfo.isAvailable()) {
            if (ConnectivityManager.TYPE_WIFI == networkInfo.getType()) {
                return NETWORK_WIFI;//wifi
            } else if ((ConnectivityManager.TYPE_MOBILE) == networkInfo.getType()) {
                return NETWORK_MOBILE;//mobile
            }
        } else {
            //网络异常
            return NETWORK_NONE;
        }
        return NETWORK_NONE;
    }
}
