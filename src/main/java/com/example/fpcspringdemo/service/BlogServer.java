package com.example.fpcspringdemo.service;

import com.example.fpcspringdemo.dao.BlogDao;
import com.example.fpcspringdemo.entity.Blog;
import com.example.fpcspringdemo.entity.BlogResult;
import com.example.fpcspringdemo.entity.LoginResult;
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

    public BlogResult getBlogs(Integer page, Integer pageSize, Integer userId) {
        List<Blog> blogs = blogDao.getBlogs(page, pageSize, userId);
        int count = blogDao.count(userId);
        int pageCount = count % pageSize == 0 ? count / pageSize : (count / pageSize + 1);
        return BlogResult.newBlogs(blogs, count, page, pageCount);
    }
}
