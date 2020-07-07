package com.mythmayor.basicproject.itype;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by mythmayor on 2020/6/30.
 * 自定义网络请求回调
 */
public abstract class NetCallback {

    public abstract void onSuccess(String response, int id);

    public void onFailed(Call call, Exception e, int id) {
    }

    public void onBefore(Request request, int id) {
    }

    public void onAfter(int id) {
    }

    public void inProgress(float progress, long total, int id) {
    }
}
