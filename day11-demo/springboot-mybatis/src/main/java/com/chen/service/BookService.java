package com.chen.service;

import com.chen.domain.Book;

import java.util.List;

public interface BookService {

    boolean save(Book book);

    boolean update(Book book);

    boolean delete(Integer id);

    Book getById(Integer id);

    List<Book> getAll();
}
