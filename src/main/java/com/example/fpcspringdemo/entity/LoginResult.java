package com.example.fpcspringdemo.entity;


public class LoginResult {
    private String status;
    private String msg;
    private boolean isLogin;
    private User data;

    public static LoginResult loginFailed(String message) {
        return new LoginResult("400", message, false);
    }

    public static LoginResult loginSuccess(String message, User UserInfo) {
        return new LoginResult("200", message, true, UserInfo);
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isIsLogin() {
        return isLogin;
    }

    public User getData() {
        return data;
    }

    public LoginResult(String status, String msg, boolean isLogin) {
        this.status = status;
        this.msg = msg;
        this.isLogin = isLogin;
    }

    public LoginResult(String status, String msg, boolean isLogin, User data) {
        this.status = status;
        this.msg = msg;
        this.isLogin = isLogin;
        this.data = data;
    }
}
