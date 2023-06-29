package com.example.fpcspringdemo.configration;

import com.example.fpcspringdemo.service.OrderService;
import com.example.fpcspringdemo.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfigration {
    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public OrderService orderService() {
        return new OrderService(new UserService());
    }
}
