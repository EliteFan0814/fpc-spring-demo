package com.example.fpcspringdemo.service;

import com.example.fpcspringdemo.entity.User;
import com.example.fpcspringdemo.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    private UserMapper userMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
//    private Map<String, User> users = new ConcurrentHashMap<>();

    @Inject
    public UserService(UserMapper userMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper = userMapper;
//        this.save("fpc", "fpc");
    }

    //    保存用户名和加密后的密码
    public void save(String username, String password) {
        userMapper.saveUser(username, bCryptPasswordEncoder.encode(password));
//        users.put(username, new User(1, username, bCryptPasswordEncoder.encode(password), ""));
    }

    public User getUserByUserName(String username) {
        return userMapper.findUserByUserName(username);
//        return users.get(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return new org.springframework.security.core.userdetails
                .User(username, user.getEncryptedPassword(), Collections.emptyList());
    }
}
