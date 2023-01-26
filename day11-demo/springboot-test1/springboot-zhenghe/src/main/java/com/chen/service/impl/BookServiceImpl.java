package com.chen.service.impl;

import com.chen.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    @Override
    public void save() {
        System.out.println("save~~~~");
    }
}
