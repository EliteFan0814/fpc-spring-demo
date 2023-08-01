package com.example.fpcspringdemo.controller;

import com.example.fpcspringdemo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AuthControllerTest {
    private MockMvc mvc;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Mock
    private UserService userService;
    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new AuthController(userService, authenticationManager)).build();
    }

    @Test
    void returnNotLoginByDefault() throws Exception {
        mvc.perform(get("/auth")).andExpect(status().isOk()).andExpect(new ResultMatcher() {
            @Override
            public void match(MvcResult result) throws Exception {
                System.out.println(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
                Assertions.assertTrue(result.getResponse().getContentAsString(StandardCharsets.UTF_8).contains(
                        "用户没有登录"));
            }
        });
    }

    @Test
    void loginTest() throws Exception {

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("username", "fpc");
        userInfo.put("password", "123123");
        Mockito.when(userService.loadUserByUsername("fpc"))
                .thenReturn(new User("fpc", bCryptPasswordEncoder.encode("123123"), Collections.emptyList()));
        Mockito.when(userService.getUserByUserName("fpc"))
                .thenReturn(new com.example.fpcspringdemo.entity.User(1, "fpc", bCryptPasswordEncoder.encode("123123"), null));
        MvcResult response =
                mvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userInfo)))
                        .andExpect(status().isOk())
                        .andExpect(result -> Assertions.assertTrue(result.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("登录成功")))
                        .andReturn();
        HttpSession session = response.getRequest().getSession();
        mvc.perform(get("/auth").session((MockHttpSession) session)).andExpect(status().isOk()).andExpect(result -> {
            System.out.println(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
            Assertions.assertTrue(result.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("fpc"));
        });
    }
}