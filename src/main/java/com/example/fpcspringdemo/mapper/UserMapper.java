package com.example.fpcspringdemo.mapper;

import com.example.fpcspringdemo.service.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where id=#{id}")
    User findUserById(@Param("id") Integer id);
}
