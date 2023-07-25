package com.example.fpcspringdemo.service;

import com.example.fpcspringdemo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    BCryptPasswordEncoder mockEncoder;
    @Mock
    UserMapper userMapper;
    @InjectMocks
    UserService userService=new UserService(userMapper,mockEncoder);
    @Test
    public void testSave(){
        Mockito.when(mockEncoder.encode("test")).thenReturn("encodedTest");
        userService.save("test","test");
        Mockito.verify(userService).save("test","encodedTest");
    }
}