package com.mythmayor.basicproject.utils.net;

import com.mythmayor.basicproject.response.LoginResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by mythmayor on 2020/6/30.
 * 接口请求方法类
 */
public interface APIService {

    /**
     * 登录
     *
     * @param username 账号
     * @param password 密码
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<LoginResponse> login(@Field("username") String username, @Field("password") String password);

}
