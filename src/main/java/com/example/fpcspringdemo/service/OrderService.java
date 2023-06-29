package com.example.fpcspringdemo.service;

import org.springframework.context.annotation.Bean;

import javax.inject.Inject;

public class OrderService {
    private UserService userService;

    public void placeOrder(Integer userId, String item) {
        userService.getUserById(userId);
    }

    public OrderService(UserService userService) {
        this.userService = userService;
    }
}
