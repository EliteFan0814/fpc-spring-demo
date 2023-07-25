package com.example.fpcspringdemo.service;

import com.example.fpcspringdemo.entity.User;
import com.example.fpcspringdemo.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    BCryptPasswordEncoder mockEncoder;
    @Mock
    UserMapper userMapper;
    @InjectMocks
    UserService userService;

    @Test
    public void testSave() {
        Mockito.when(mockEncoder.encode("test")).thenReturn("encodedTest");
        userService.save("test", "test");
        Mockito.verify(userMapper).saveUser("test", "encodedTest");
    }

    @Test
    public void testGetUserByUserName() {
        userService.getUserByUserName("fpc");
        Mockito.verify(userMapper).findUserByUserName("fpc");
    }

    @Test
    public void testLoadUserByUsernameThrowException() {
        Mockito.when(userMapper.findUserByUserName("fpc")).thenReturn(null);
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("fpc");
        });
    }

    @Test
    public void testLoadUserByUsername() {
        Mockito.when(userMapper.findUserByUserName("fpc")).thenReturn(new User(1, "fpc", "encodedPassword", null));
        UserDetails userDetail = userService.loadUserByUsername("fpc");
        Assertions.assertEquals("fpc", userDetail.getUsername());
        Assertions.assertEquals("encodedPassword", userDetail.getPassword());
    }
}