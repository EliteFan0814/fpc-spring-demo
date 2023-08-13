package com.example.fpcspringdemo.entity;


public class LoginResult<T> extends Result {

    public static LoginResult noLogin() {
        return new LoginResult("400", "用户没有登录", null);
    }

    public static LoginResult logout() {
        return new LoginResult("200", "退出登录", null);
    }

    public static LoginResult loginFailed(String msg) {
        return new LoginResult("400", msg, null);
    }

    public static LoginResult loginSuccess(String msg, User user) {
        return new LoginResult("ok", msg, user);
    }

    protected LoginResult(String status, String msg, T data) {
        super(status, msg, data);
    }
}
