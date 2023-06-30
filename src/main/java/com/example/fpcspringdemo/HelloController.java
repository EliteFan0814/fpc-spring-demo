package com.example.fpcspringdemo;

import com.example.fpcspringdemo.service.OrderService;
import com.example.fpcspringdemo.service.User;
import com.example.fpcspringdemo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
public class HelloController {
    private UserService userService;

    @Inject
    public HelloController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/")
    public User index() {
        return this.userService.getUserById(1);
    }
}
