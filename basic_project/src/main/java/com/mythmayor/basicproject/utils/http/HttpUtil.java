package com.mythmayor.basicproject.utils.http;

import com.google.gson.Gson;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.itype.HttpCallback;
import com.mythmayor.basicproject.request.LoginRequest;
import com.mythmayor.basicproject.utils.FileUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mythmayor on 2020/6/30.
 * 接口请求工具类
 */
public class HttpUtil {

    //Gson实例
    public static Gson mGson = new Gson();

    //POST提交表单的基础参数params
    private static Map<String, String> getBaseParams() {
        Map<String, String> params = new HashMap<>();
        //params.put("param1", "value1");
        return params;
    }

    //POST提交Json的content
    private static String getContent(String json) {
        return json;
    }

    //POST提交表单
    private static void requestNetData(String url, Map<String, String> params, StringCallback callback) {
        HttpTool.getInstance().postForm(url, params, callback);
    }

    //POST提交Json
    private static void requestNetData(String url, String json, StringCallback callback) {
        HttpTool.getInstance().postJson(url, json, callback);
    }

    //POST提交表单
    private static void requestNetData(String url, Map<String, String> params, HttpCallback callback) {
        HttpTool.getInstance().postForm(url, params, callback);
    }

    //POST提交Json
    private static void requestNetData(String url, String json, HttpCallback callback) {
        HttpTool.getInstance().postJson(url, json, callback);
    }

    //GET请求
    private static void requestNetData(String url, StringCallback callback) {
        HttpTool.getInstance().get(url, callback);
    }

    //GET请求
    private static void requestNetData(String url, HttpCallback callback) {
        HttpTool.getInstance().get(url, callback);
    }

    //登录接口-Json
    public static void login(LoginRequest request, HttpCallback callback) {
        requestNetData(MyConstant.URL_ABSOLUTE_LOGIN, mGson.toJson(request), callback);
    }

    //登录接口-表单
    public static void login2(LoginRequest request, HttpCallback callback) {
        Map<String, String> params = getBaseParams();
        params.put("username", request.getUsername());
        params.put("password", request.getPassword());
        requestNetData(MyConstant.URL_ABSOLUTE_LOGIN, params, callback);
    }

    /**
     * 下载文件
     *
     * @param url      文件下载路径
     * @param filePath 文件保存路径
     * @param callBack 下载回调方法
     * @return RequestCall，可拿到该对象进行取消单个请求：call.cancel();
     */
    public static RequestCall downloadFile(String url, String filePath, FileCallBack callBack) {
        File file = new File(filePath);
        if (file.exists()) {
            FileUtil.deleteFile(file);
        }
        RequestCall call = OkHttpUtils.get().url(url).build();
        call.execute(callBack);
        return call;
    }
}
