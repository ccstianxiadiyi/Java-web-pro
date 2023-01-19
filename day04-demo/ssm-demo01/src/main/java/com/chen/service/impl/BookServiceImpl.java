package com.chen.service.impl;

import com.chen.dao.BookDao;
import com.chen.dao.impl.BookDaoImpl;
import com.chen.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookServiceImpl implements BookService {
//    @Autowired 默认用类型装配
//    @Qualifier("bookDao") 使用按名称注入
//    自动注入
    private BookDao bookDao;
    public void save() {
        System.out.println("book service save~~~");
        bookDao.save();
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
}
