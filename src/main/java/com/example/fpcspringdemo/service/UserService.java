package com.example.fpcspringdemo.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService implements UserDetailsService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Map<String, String> userPasswords = new ConcurrentHashMap<>();

    @Inject
    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.save("gebilaowang", "gebilaowang");
    }

//    保存用户名和加密后的密码
    public void save(String username, String password) {
        userPasswords.put(username, bCryptPasswordEncoder.encode(password));
    }

    public String getPassword(String username) {
        return userPasswords.get(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!userPasswords.containsKey(username)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        String encodedPassword = userPasswords.get(username);
        return new User(username, encodedPassword, Collections.emptyList());
    }
}
