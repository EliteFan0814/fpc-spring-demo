package com.example.fpcspringdemo.dao;

import com.example.fpcspringdemo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where id=#{id}")
    User findUserById(@Param("id") Integer id);

    @Select("select * from user where username=#{username}")
    User findUserByUserName(@Param("username") String username);

    @Insert("insert into user (username, encrypted_password, created_at, updated_at)\n" +
            "values (#{username},#{encryptedPassword},now(),now())")
    void saveUser(@Param("username") String username, @Param("encryptedPassword") String encryptedPassword);
}
