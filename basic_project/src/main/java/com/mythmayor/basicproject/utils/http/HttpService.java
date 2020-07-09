package com.mythmayor.basicproject.utils.http;

import com.mythmayor.basicproject.response.LoginResponse;
import com.mythmayor.basicproject.response.RegisterResponse;
import com.mythmayor.basicproject.response.UserInfoResponse;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by mythmayor on 2020/6/30.
 * 接口请求方法类
 */
public interface HttpService {

    /**
     * 登录接口-POST FORM
     *
     * @param username 账号
     * @param password 密码
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<LoginResponse> login(@Field("username") String username, @Field("password") String password);

    /**
     * 登录接口-POST Json
     *
     * @param body
     * @return
     */
    @POST("user/login")
    Observable<LoginResponse> login2(@Body RequestBody body);

    /**
     * 登录接口-POST FORM
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<LoginResponse> login3(@FieldMap Map<String, Object> params);

    /**
     * 注册接口
     *
     * @param username 账号
     * @param password 密码
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<RegisterResponse> register(@Field("username") String username, @Field("password") String password);

    /**
     * 获取用户信息接口
     *
     * @param username 账号
     * @param password 密码
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<UserInfoResponse> getUserInfo(@Field("username") String username, @Field("password") String password);

}
