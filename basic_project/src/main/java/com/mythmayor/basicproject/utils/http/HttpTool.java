package com.mythmayor.basicproject.utils.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.mythmayor.basicproject.BasicApplication;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.itype.HttpCallback;
import com.mythmayor.basicproject.utils.ToastUtil;
import com.mythmayor.basicproject.utils.UserInfoManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;

/**
 * Created by mythmayor on 2020/6/30.
 * 接口请求辅助工具类
 */
public class HttpTool {

    private static HttpTool ourInstance = new HttpTool();

    public static HttpTool getInstance() {
        return ourInstance;
    }

    private HttpTool() {
    }

    /**
     * POST提交表单
     *
     * @param url    请求地址
     * @param params 表单请求参数
     */
    public void postForm(String url, Map<String, String> params, StringCallback callback) {
        String token = UserInfoManager.getHeaderToken(BasicApplication.getInstance().getContext());
        if(TextUtils.isEmpty(token)) {
            OkHttpUtils.post().url(url).params(params).build().execute(callback);
        }else {
            OkHttpUtils.post().url(url).addHeader(MyConstant.HEADER_KEY, token).params(params).build().execute(callback);
        }
    }

    /**
     * POST提交Json
     *
     * @param url  请求地址
     * @param json Json请求参数
     */
    public void postJson(String url, String json, StringCallback callback) {
        String token = UserInfoManager.getHeaderToken(BasicApplication.getInstance().getContext());
        if(TextUtils.isEmpty(token)) {
            OkHttpUtils.postString().url(url).content(json).mediaType(MediaType.parse(MyConstant.MEDIA_TYPE)).build().execute(callback);
        }else {
            OkHttpUtils.postString().url(url).addHeader(MyConstant.HEADER_KEY, token).content(json).mediaType(MediaType.parse(MyConstant.MEDIA_TYPE)).build().execute(callback);
        }
    }

    /**
     * POST提交表单
     *
     * @param url    请求地址
     * @param params 表单请求参数
     */
    public void postForm(String url, Map<String, String> params, final HttpCallback callback) {
        RequestCall build;
        String token = UserInfoManager.getHeaderToken(BasicApplication.getInstance().getContext());
        if(TextUtils.isEmpty(token)) {
            build = OkHttpUtils.post().url(url).params(params).build();
        }else {
            build = OkHttpUtils.post().url(url).addHeader(MyConstant.HEADER_KEY, token).params(params).build();
        }
        build.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (!isNetworkAvailable(BasicApplication.getInstance().getContext())) {
                    ToastUtil.showToast(MyConstant.ERROR_NET);
                } else {
                    ToastUtil.showToast(MyConstant.ERROR_SERVER);
                }
                if (null != callback) {
                    callback.onFailed(call, e, id);
                }
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (null != callback) {
                        callback.onSuccess(response, id);
                    }
                } catch (Exception e) {
                    ToastUtil.showToast(MyConstant.ERROR_DATA);
                    e.printStackTrace();
                }
            }

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (null != callback) {
                    callback.onBefore(request, id);
                }
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (null != callback) {
                    callback.onAfter(id);
                }
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                if (null != callback) {
                    callback.inProgress(progress, total, id);
                }
            }
        });
    }

    /**
     * POST提交Json
     *
     * @param url  请求地址
     * @param json Json请求参数
     */
    public void postJson(String url, String json, final HttpCallback callback) {
        RequestCall build;
        String token = UserInfoManager.getHeaderToken(BasicApplication.getInstance().getContext());
        if(TextUtils.isEmpty(token)) {
            build = OkHttpUtils.postString().url(url).content(json).mediaType(MediaType.parse(MyConstant.MEDIA_TYPE)).build();
        }else {
            build = OkHttpUtils.postString().url(url).addHeader(MyConstant.HEADER_KEY, token).content(json).mediaType(MediaType.parse(MyConstant.MEDIA_TYPE)).build();
        }
        build.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (!isNetworkAvailable(BasicApplication.getInstance().getContext())) {
                    ToastUtil.showToast(MyConstant.ERROR_NET);
                } else {
                    ToastUtil.showToast(MyConstant.ERROR_SERVER);
                }
                if (null != callback) {
                    callback.onFailed(call, e, id);
                }
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (null != callback) {
                        callback.onSuccess(response, id);
                    }
                } catch (Exception e) {
                    ToastUtil.showToast(MyConstant.ERROR_DATA);
                    e.printStackTrace();
                }
            }

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (null != callback) {
                    callback.onBefore(request, id);
                }
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (null != callback) {
                    callback.onAfter(id);
                }
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                if (null != callback) {
                    callback.inProgress(progress, total, id);
                }
            }
        });
    }


    /**
     * GET请求
     *
     * @param url 请求地址
     */
    public void get(String url, StringCallback callback) {
        OkHttpUtils.get().url(url).build().execute(callback);
    }

    /**
     * GET请求
     *
     * @param url 请求地址
     */
    public void get(String url, final HttpCallback callback) {
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (!isNetworkAvailable(BasicApplication.getInstance().getContext())) {
                    ToastUtil.showToast(MyConstant.ERROR_NET);
                } else {
                    ToastUtil.showToast(MyConstant.ERROR_SERVER);
                }
                if (null != callback) {
                    callback.onFailed(call, e, id);
                }
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (null != callback) {
                        callback.onSuccess(response, id);
                    }
                } catch (Exception e) {
                    ToastUtil.showToast(MyConstant.ERROR_DATA);
                    e.printStackTrace();
                }
            }

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if (null != callback) {
                    callback.onBefore(request, id);
                }
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (null != callback) {
                    callback.onAfter(id);
                }
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                if (null != callback) {
                    callback.inProgress(progress, total, id);
                }
            }
        });
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != cm) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (null != networkInfo) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }
}
