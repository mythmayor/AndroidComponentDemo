package com.mythmayor.basicproject.request;

/**
 * Created by mythmayor on 2020/6/30.
 * 注册接口请求实体类
 */
public class RegisterRequest extends BaseRequest {

    private String username;
    private String password;

    public RegisterRequest() {
    }

    public RegisterRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
