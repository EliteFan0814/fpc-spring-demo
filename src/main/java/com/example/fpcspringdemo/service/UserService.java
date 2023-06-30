package com.example.fpcspringdemo.service;

import com.example.fpcspringdemo.mapper.UserMapper;

import javax.inject.Inject;

public class UserService {
    private UserMapper userMapper;

    @Inject
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User getUserById(Integer userId) {
        return userMapper.findUserById(userId);
    }
}
