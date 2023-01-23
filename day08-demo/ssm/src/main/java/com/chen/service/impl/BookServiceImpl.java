package com.chen.service.impl;

import com.chen.dao.BookDao;
import com.chen.domain.Book;
import com.chen.service.BookService;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;
    public boolean save(Book book) {
        bookDao.save(book);
        return true;
    }

    public boolean update(Book book) {
        bookDao.update(book);
        return true;
    }

    public boolean delete(Integer id) {
        bookDao.delete(id);
        return true;
    }

    public Book getById(Integer id) {
        Book book = bookDao.getById(id);
        return book;
    }

    public List<Book> getAll() {
        List<Book> all = bookDao.getAll();
        return all;
    }
}
