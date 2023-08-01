package com.example.fpcspringdemo.controller;


import com.example.fpcspringdemo.entity.LoginResult;
import com.example.fpcspringdemo.entity.User;
import com.example.fpcspringdemo.service.UserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private UserService userService;
    private AuthenticationManager authenticationManager;

    @Inject
    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/auth")
    @ResponseBody
    public Object auth() {
//        String userName = .getName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.getUserByUserName(authentication == null?null:authentication.getName());
        if (loggedUser == null) {
            return LoginResult.loginFailed("用户没有登录");
        } else {
            return LoginResult.loginSuccess("用户已登录", loggedUser);
        }
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
        return new LoginResult("300", "ddd", false);
    }

    @PostMapping("/auth/register")
    @ResponseBody
    public LoginResult register(@RequestBody Map<String, String> usernameAndPassword) {
        String username = usernameAndPassword.get("username");
        String password = usernameAndPassword.get("password");
        if (username == null || password == null) {
            return LoginResult.loginFailed("请输入用户名和密码");
        }
        if (username.length() < 1 || username.length() > 15) {
            return LoginResult.loginFailed("用户名长度在1-15个字符");
        }
        if (password.length() < 1 || password.length() > 15) {
            return LoginResult.loginFailed("密码长度在1-15个字符");
        }
        try {
            userService.save(username, password);

        } catch (DuplicateKeyException e) {
            return LoginResult.loginFailed("用户已存在");
        }
        return new LoginResult("200", "注册成功", false);
//        User user = userService.getUserByUserName(username);
//        if (user == null) {
//            userService.save(username, password);
//            return new Result("200", "注册成功", false);
//        } else {
//            return new Result("400", "用户已存在", false);
//        }
    }

    @PostMapping("/auth/login")
    @ResponseBody
    public LoginResult login(@RequestBody Map<String, Object> usernameAndPassword) {
        String username = (String) usernameAndPassword.get("username");
        String password = (String) usernameAndPassword.get("password");
        UserDetails userDetails = null;
        try {
            userDetails = userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return new LoginResult("400", "用户不存在", false);
        }
        var token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        try {
            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);
            return new LoginResult("200", "登录成功", true, userService.getUserByUserName(username));
        } catch (Exception e) {
            System.out.println(e.toString());
            return new LoginResult("400", "错误", false);
        }
    }

    @PostMapping("/auth/logout")
    @ResponseBody
    public LoginResult logout() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User LoggedInUser = userService.getUserByUserName(username);
        if (LoggedInUser == null) {
            return LoginResult.loginFailed("用户未登录");
        } else {
            SecurityContextHolder.clearContext();
            return new LoginResult("200", "成功退出", false);
        }
    }

}
