package com.example.fpcspringdemo.service;

import com.example.fpcspringdemo.dao.BlogDao;
import com.example.fpcspringdemo.entity.Blog;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class BlogServer {
    private BlogDao blogDao;

    @Inject
    public BlogServer(BlogDao blogDao) {
        this.blogDao = blogDao;
    }

    public List<Blog> getBlogs(Integer page, Integer pageSize, Integer userId) {
        return blogDao.getBlogs(page, pageSize, userId);
    }
}
