package com.example.fpcspringdemo.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService implements UserDetailsService {
    private Map<String, String> userPasswords = new ConcurrentHashMap<>();
    public UserService(){
    userPasswords.put("gebilaowang","gebilaowang");
    }
    public void save(String username,String password){
        userPasswords.put(username,password);
    }
    public String getPassword(String username){
        return userPasswords.get(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(!userPasswords.containsKey(username)){
            throw new UsernameNotFoundException("用户不存在");
        }
        String password = userPasswords.get(username);
        return new User(username,password, Collections.emptyList());
    }
}
