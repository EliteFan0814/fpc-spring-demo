package com.example.fpcspringdemo.configration;

import com.example.fpcspringdemo.mapper.UserMapper;
import com.example.fpcspringdemo.service.OrderService;
import com.example.fpcspringdemo.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfigration {
    @Bean
    public UserService userService(UserMapper userMapper) {
        return new UserService(userMapper);
    }

    @Bean
    public OrderService orderService(UserService userService) {
        return new OrderService(userService);
    }
}
