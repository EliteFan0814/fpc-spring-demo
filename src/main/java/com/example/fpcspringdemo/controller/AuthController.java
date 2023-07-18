package com.example.fpcspringdemo.controller;


import com.example.fpcspringdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.Map;

@Controller
public class AuthController {
    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthenticationManager authenticationManager;

    @Inject
    public AuthController(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/auth")
    @ResponseBody
    public Object auth() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("username:" + userName);
        return new Result("200", "用户没有登录", false);
    }

    @GetMapping("/auth/1")
    @ResponseBody
    public String auth1() {
        return "<div>123123123</div>";
    }

    @GetMapping("/auth/2")
    @ResponseBody
    public String auth2() {
        return "123123123";
    }

    @GetMapping("/doLogin")
    @ResponseBody
    public Object doLogin() {
        return new Result("300", "ddd", false);
    }

    @PostMapping("/auth/login")
    @ResponseBody
    public Result login(@RequestBody Map<String, Object> usernameAndPassword) {
        String username = (String) usernameAndPassword.get("username");
        String password = (String) usernameAndPassword.get("password");
        UserDetails userDetails = null;
        try {
            userDetails = userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return new Result("400", "用户不存在", false);
        }
        var token = new UsernamePasswordAuthenticationToken(userDetails, bCryptPasswordEncoder.encode(password), userDetails.getAuthorities());
        try {
            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);
            User user = new User(1, "fpc", "hahaha");
            return new Result("200", "登录成功", true, user);
        } catch (Exception e) {
            return new Result("400", "错误", false);
        }


//        String trueName = userDetails.getUsername();
//        String truePassword = userDetails.getPassword();
//        if (bCryptPasswordEncoder.matches(password, truePassword)) {
//            var token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
//            customAuthenticationManager.authenticate(token);
//            SecurityContextHolder.getContext().setAuthentication(token);
//            User user = new User(1, "fpc", "hahaha");
//            return new Result("200", "登录成功", true, user);
//        } else {
//            return new Result("400", "账号或密码不正确", false);
//        }
    }

    private static class Result {
        private String status;
        private String msg;
        private boolean isLogin;
        private User data;

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

        public Result(String status, String msg, boolean isLogin) {
            this.status = status;
            this.msg = msg;
            this.isLogin = isLogin;
        }

        public Result(String status, String msg, boolean isLogin, User data) {
            this.status = status;
            this.msg = msg;
            this.isLogin = isLogin;
            this.data = data;
        }
    }
}
