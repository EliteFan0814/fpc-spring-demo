package com.example.fpcspringdemo.dao;

import com.example.fpcspringdemo.entity.Blog;
import org.springframework.context.annotation.Bean;

import java.util.List;

public class BlogDao {
    public List<Blog> getBlogs(Integer page, Integer pageSize, Integer userId) {
        return null;
    };
    public int count(int userId){
        return 0;
    }
}
