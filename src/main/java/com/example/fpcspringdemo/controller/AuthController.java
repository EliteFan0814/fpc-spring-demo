package com.example.fpcspringdemo.controller;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    @Inject
    public AuthController(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/auth/login")
    @ResponseBody
    public Object auth() {
        return new Result("200", "哈哈哈", false);
    }

    @PostMapping("/auth/login")
    @ResponseBody
    public Result login(@RequestBody Map<String, Object> usernameAndPassword) {
        String username = (String) usernameAndPassword.get("username");
        String password = (String) usernameAndPassword.get("password");
        System.out.println(password);
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            String trueName = userDetails.getUsername();
            String truePassword = userDetails.getPassword();
            System.out.println(trueName);
            System.out.println(bCryptPasswordEncoder.matches(password,truePassword));
            if (bCryptPasswordEncoder.matches(password,truePassword)) {
                return new Result("200", "登录成功", true);
            } else {
                return new Result("400", "账号或密码不正确", false);
            }
        } catch (UsernameNotFoundException e) {
            return new Result("400", "用户不存在", false);
        }
//        UsernamePasswordAuthenticationToken token =
//                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    private static class Result {
        private String status;
        private String msg;
        private boolean login;

        public Result(String status, String msg, boolean login) {
            this.status = status;
            this.msg = msg;
            this.login = login;
        }

        public String getStatus() {
            return status;
        }

        public String getMsg() {
            return msg;
        }

        public boolean isLogin() {
            return login;
        }
    }
}
