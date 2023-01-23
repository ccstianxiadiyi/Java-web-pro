package com.chen.service;

import com.chen.config.SpringConfig;
import com.chen.domain.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class BookServiceTest {
    @Autowired
    private BookService bookService;
    @Test
    public void TestGetById(){
        Book byId = bookService.getById(1);
        System.out.println(byId);
    }
    @Test
    public void TestGetAll(){
        List<Book> all = bookService.getAll();
        System.out.println(all);
    }
}
