package com.example.fpcspringdemo.controller;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private AuthenticationManager authenticationManager;

    @Inject
    public AuthController(UserDetailsService userDetailsService,AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/auth")
    @ResponseBody
    public Object auth() {
        return new Result("哈哈get",true);
    }
    @PostMapping("/auth/login")
    public void login(@RequestBody Map<String, Object> usernameAndPassword) {
        String username = (String) usernameAndPassword.get("username");
        String password = (String) usernameAndPassword.get("password");
        try{
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        }catch(UsernameNotFoundException e){

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
