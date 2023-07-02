package com.example.fpcspringdemo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthController {
    @GetMapping("/auth")
    @ResponseBody
    public Object auth() {
        return new Result();
    }

    private static class Result {
        public String getStatus() {
            return "ok";
        }

        public Boolean isLogin() {
            return true;
        }
    }
}
