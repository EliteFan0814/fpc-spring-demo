package com.example.fpcspringdemo.service;

import com.example.fpcspringdemo.dao.BlogDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.inject.Inject;

@ExtendWith(MockitoExtension.class)
public class BlogServerTest {
    @Mock
    BlogDao blogDao;
    @InjectMocks
    BlogServer blogServer;

    @BeforeEach
    public void setUp() {
        blogServer = new BlogServer(blogDao);
    }

    @Test
    public void getBlogsFromDb() {
        blogServer.getBlogs(1, 10, null);
        Mockito.verify(blogDao).getBlogs(1, 10, null);
    }

    @Test
    public void returnFailureWhenExceptionThrow() {
        Mockito.when(blogDao.getBlogs(Mockito.anyInt(), Mockito.anyInt(), Mockito.any())).thenThrow(new Exception());
        blogServer.getBlogs(1,10,null);
    }
}
